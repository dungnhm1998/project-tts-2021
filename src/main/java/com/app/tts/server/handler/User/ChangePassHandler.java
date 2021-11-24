package com.app.tts.server.handler.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.tts.encode.Md5Code;
import com.app.tts.services.BaseService;
import com.app.tts.session.redis.SessionStore;
import com.app.tts.util.AppParams;

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
					List<Map> user = BaseService.changePassword(email, Md5Code.md5(confirm_password)); 
					
					data.put("message", "change password successfully");
					routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
					routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
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
	
}
