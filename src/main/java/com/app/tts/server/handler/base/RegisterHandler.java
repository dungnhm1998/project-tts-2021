package com.app.tts.server.handler.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.validator.routines.EmailValidator;

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
				String userName = json.getString(AppParams.USERNAME);
				String password = json.getString(AppParams.PASSWORD);
				String confirmPassword = json.getString("confirmPassword");
				String firstName = json.getString(AppParams.FIRST_NAME);
				String lastName = json.getString(AppParams.LAST_NAME);
				String phone = json.getString(AppParams.PHONE);
				String email = json.getString(AppParams.EMAIL);
				String address = json.getString(AppParams.ADDRESS);
				String postal = json.getString(AppParams.POSTAL_CODE);
				
				Map data = new HashMap<>();
				
				
				
				
				
				

				routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
				routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
				data.put("email", email);
				
				LOGGER.info("---email = " + email);
				List<Map> user = SubService.getUserByEmail2(email);
			boolean duplicate = false;
			if (!user.isEmpty()) {
				duplicate = true;
			}
			if (!password.equals(confirmPassword)) {
				data.put("message", "register failed, password and confirm password are not matched");
			} else if (phone.length() != 10) {
				data.put("message", "can 10 so");
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
				SubService.insertUser( userName,  password,  firstName,  lastName,  phone, email,  address,  postal);
				data.put("message", "register successed");
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

		@SuppressWarnings("unchecked")
//		public void insert(String userName, String password, String firstName, String lastName, String phone, 
//				String email, String address, String postal) throws SQLException {
//			SubService.insertUser( userName,  password,  firstName,  lastName,  phone, email,  address,  postal);
//		}
		
		public static boolean isValid(String email) {
			boolean valid = false;
			valid = EmailValidator.getInstance().isValid(email);
			return true;
		}
		
		private static final Logger LOGGER = Logger.getLogger(SubService.class.getName());
}
