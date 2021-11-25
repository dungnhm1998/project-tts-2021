package com.app.tts.server.handler.Ucant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;

import com.app.tts.encode.Md5Code;
import com.app.tts.services.SubService;
import com.app.tts.session.redis.SessionStore;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public class ChangePassHandler implements Handler<RoutingContext>, SessionStore {

	@Override
	public void handle(RoutingContext routingContext) {

		routingContext.vertx().executeBlocking(future -> {
				try {
					JsonObject jsonRequest = routingContext.getBodyAsJson();
					String email = jsonRequest.getString("email");
					String password = jsonRequest.getString("password");
					String new_password = jsonRequest.getString("new_password");
					String confirm_password = jsonRequest.getString("confirm_password");
					
					Map data = new HashMap();
					List<Map> user = SubService.getUserByEmail(email); 
					
					Map a = user.get(0);
					String encodePassword = ParamUtil.getString(a, AppParams.S_PASSWORD);
					
					boolean duplicate = false;
	                if (!user.isEmpty()) {
	                    duplicate = true;
	                }
	                if (!encodePassword.equals(Md5Code.md5(password))) {
	                    data.put("message", "Login failed! , password is incorrect");
	                } else if (!new_password.equals(confirm_password)) {
	                    data.put("message", "New password and confirm password are not matched");
	                } else if (18 < new_password.length() || new_password.length() < 6) {
	                    data.put("message", "password must be between 6 and 18 characters ");
	                } else if (!new_password.matches(".*[A-Z].*+")) {
	    				data.put("message", "password must contain at least one uppercase character");
	    			} else if (!new_password.matches(".*[0-9].*+")) {	
	    				data.put("message", "password must contain at least one numeric character");
	                } else if (!isValid(email)) {
	                    data.put("message", "Email is not valid");
	                } else if (!duplicate) {
	                    data.put("message", "Email hasn't registered yet" + email);
	                } else if (duplicate && isValid(email)) {
	                    SubService.changePassword(email, Md5Code.md5(new_password));
	                    data.put("message", "change password successfully");
	                    routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
	                    routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
	                } else {
	                    data.put("message", "failed to change password");
	                }
	                routingContext.put(AppParams.RESPONSE_DATA, data);
	                future.complete();
				} catch (Exception e) {
					routingContext.fail(e);
				}
		}, asyncResult -> {
			if (asyncResult.succeeded()) {
				routingContext.next();
			} else {
				routingContext.fail(asyncResult.cause());
			}
			});
	}
	
	 public static boolean isValid(String email) {
	        boolean valid = false;
	        valid = EmailValidator.getInstance().isValid(email);
	        return true;
	    }
	
}
