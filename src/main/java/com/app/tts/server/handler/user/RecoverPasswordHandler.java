package com.app.tts.server.handler.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import com.app.tts.encode.Md5Code;
import com.app.tts.services.UserService;
import com.app.tts.util.AppParams;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.RoutingContext;

public class RecoverPasswordHandler implements Handler<RoutingContext> {
	
	 private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
	 private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
	 private static final String digits = "0123456789"; // 0-9
	 //private static final String specials = "~=+%^*/()[]{}/!@#$?|";
	 private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
	 //private static final String ALL = alpha + alphaUpperCase + digits + specials;
	
	private static Random generator = new Random();

	@Override
	public void handle(RoutingContext routingContext) {

		routingContext.vertx().executeBlocking(future -> {
			try {
				HttpServerResponse response = (HttpServerResponse) routingContext.response();
				JsonObject jsonRequest = routingContext.getBodyAsJson();
				String email = jsonRequest.getString(AppParams.EMAIL);
				String password = randomAlphaNumeric(6);
				
				Map data = new HashMap();
				List<Map> users = UserService.getPassByEmail(email);
				if(!users.isEmpty()) {
					UserService.updatePass(email, password);
					routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
					routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
					data.put("message ", "recover password successfully");
					data.put("recover_password ", password);
					routingContext.put(AppParams.RESPONSE_DATA, data);
				}else {
					response.end(Json.encode("email already exist!"));
				}
				
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
	
	public String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }
	
	public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    private static final Logger LOGGER = Logger.getLogger(RecoverPasswordHandler.class.getName());
}
