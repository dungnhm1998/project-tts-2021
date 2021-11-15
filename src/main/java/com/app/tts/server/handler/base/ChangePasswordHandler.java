package com.app.tts.server.handler.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.EmailValidator;

import com.app.tts.services.SubService;
import com.app.tts.session.redis.SessionStore;
import com.app.tts.util.AppParams;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public class ChangePasswordHandler implements Handler<RoutingContext>, SessionStore{
	@Override
	public void handle(RoutingContext routingContext) {

		routingContext.vertx().executeBlocking(future -> {
			try {
				JsonObject json = routingContext.getBodyAsJson();
				String userName = json.getString(AppParams.USERNAME);
				String password = json.getString(AppParams.PASSWORD);
				String newPassword = json.getString("newPassword");
				String confirmPassword = json.getString("confirmPassword");
				
				Map data = new HashMap<>();

				routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
				routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
				data.put("Username", userName);
				
				LOGGER.info("Username = " + userName);
				List<Map> pass = SubService.getPasswordByUserName(userName);

				pass = new ArrayList<Map>();
				List <String> result = new ArrayList<String>();
				for(Map map: pass) {
					result.addAll(map.values());
				}
				
				LOGGER.info("result" + result);
				boolean check = false;
				if (!pass.isEmpty()) {
					check = true;
				} 
				if (!isValid(userName)) {
					data.put("message", "Username khong ton tai");
				} else if (!result.contains(password)) {
					data.put("message", "Mat khau khong chinh xac");
				} else if (!newPassword.equals(confirmPassword)) {
					data.put("message", "Mat khau khong trung khop");
				} else if (!check && isValid(userName)) {
					SubService.updatePassword(userName, newPassword);
					data.put("message", "Doi mat khau thanh cong");
					routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
					routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
				} else {
					data.put("message", "Doi mat khau that bai");
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
	
	public static boolean isValid(String userName) {
		boolean valid = false;
		valid = EmailValidator.getInstance().isValid(userName);
		return true;
	}
	
	private static final Logger LOGGER = Logger.getLogger(SubService.class.getName());
}	
