package com.app.tts.server.handler.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.validator.routines.EmailValidator;

import com.app.tts.encode.Md5Code;
import com.app.tts.services.SubService;
import com.app.tts.session.redis.SessionStore;
import com.app.tts.util.AppParams;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public class RegisterHandler implements Handler<RoutingContext>, SessionStore{
	@Override
	public void handle(RoutingContext routingContext) {

		routingContext.vertx().executeBlocking(future -> {
			try {
				JsonObject json = routingContext.getBodyAsJson();
				String email = json.getString(AppParams.EMAIL);
				String password = json.getString(AppParams.PASSWORD);
				String confirmPassword = json.getString("confirmPassword");
				String phone = json.getString(AppParams.PHONE);
				
				String id = UUID.randomUUID().toString().substring(5, 20);
				Map data = new HashMap<>();
	
				routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
				routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
				data.put("email", email);
				
				LOGGER.info("email: " + email);
				List<Map> user = SubService.getUserByEmail(email);
				boolean duplicate = false;
				if (!user.isEmpty()) {
					duplicate = true;
				}
				if (!password.equals(confirmPassword)) {
					data.put("message", "register failed, password and confirm password are not matched");
				} else if (phone.length() != 10) {
					data.put("message", "phone must contain 10 numeric character");
				} else if (password.length() < 6 || password.length() >20) {
					data.put("message", "register failed, password must be between 8-20 characters ");
				} else if (!password.matches(".*[A-Z].*+")) {
					data.put("message", "password must contain at least one uppercase character");
				} else if (!password.matches(".*[0-9].*+")) {	
					data.put("message", "password must contain at least one numeric character");
				} else if (!isValid(email)) {
					data.put("message", "register failed, email is not valid");
				} else if (duplicate) {
					data.put("message", "register failed, email is duplicated");
				} else if (!duplicate && isValid(email)) {
					// Đăng ký thành công
					String encodePassword = Md5Code.md5(password); 
					SubService.insertUser(id, email, encodePassword, phone);
					data.put("message", "register successed");
					data.put("id", id);
					routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
					routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
				} else {
					data.put("message", "register failed");
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
	
	private static final Logger LOGGER = Logger.getLogger(SubService.class.getName());
}

