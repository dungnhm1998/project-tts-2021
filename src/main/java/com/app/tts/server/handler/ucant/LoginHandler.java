package com.app.tts.server.handler.ucant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.validator.routines.EmailValidator;

import com.app.tts.encode.Md5Code;
import com.app.tts.services.SubService;
import com.app.tts.session.redis.SessionStore;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import com.google.gson.Gson;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.Cookie;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.Session;
import redis.clients.jedis.params.SetParams;

public class LoginHandler implements Handler<RoutingContext>, SessionStore {

	@Override
	public void handle(RoutingContext routingContext) {

		routingContext.vertx().executeBlocking(future -> {
				try {
				JsonObject jsonRequest = routingContext.getBodyAsJson();
				Session session = routingContext.session();
				
				String email = jsonRequest.getString("email");
				String password = jsonRequest.getString("password");
				String encodePassword = Md5Code.md5(password);
				Gson gson = new Gson();
				Map data = new HashMap<>();
				List<Map> user = SubService.getUserByEmail(email);
			
					if (!user.isEmpty()) {
						for(Map map : user) {
							String user_id = ParamUtil.getString(map, AppParams.S_ID);
							String user_password = ParamUtil.getString(map, AppParams.S_PASSWORD);
							if(user_password.equals(encodePassword)) {
								if (session != null) {
									LOGGER.info("Connection to server sucessfully");
									// Check server redis có chạy không
									LOGGER.info("Server is running: " + jedis.ping());
									// Set timout cho session
									SetParams ttl = new SetParams();
									ttl.ex(30 * 60);
									// Lưu data của user vào session
									jedis.set(session.id(), gson.toJson(user), ttl);
									// Lưu sessionId vào cookie
									Cookie cookie = Cookie.cookie("sessionId", session.id());
									routingContext.addCookie(cookie);
									routingContext.put(AppParams.USER_ID, user_id);
								} else {
									LOGGER.info("session is null");
								}
								data.put("id", user_id);
								data.put("message", "login successfully");
								data.put("email", email);
								routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
								routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
								routingContext.put(AppParams.RESPONSE_DATA, data);
							}else {
								data.put("message", "password is incorrect");
								routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.UNAUTHORIZED.code());
								routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.UNAUTHORIZED.reasonPhrase());
								routingContext.put(AppParams.RESPONSE_DATA, data);
								}
						}
					
					}else {
						data.put("message", "email is invalid");
						routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.UNAUTHORIZED.code());
						routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.UNAUTHORIZED.reasonPhrase());
						routingContext.put(AppParams.RESPONSE_DATA, data);
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
	
	private static final Logger LOGGER = Logger.getLogger(LoginHandler.class.getName());
}
