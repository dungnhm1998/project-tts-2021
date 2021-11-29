package com.app.tts.server.handler.ucant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.app.tts.encode.Md5Code;
import com.app.tts.services.SubService;
import com.app.tts.session.redis.SessionStore;
import com.app.tts.util.AppParams;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public class RecoveryPassHandler implements Handler<RoutingContext>, SessionStore{
	@Override
	public void handle(RoutingContext routingContext) {

		routingContext.vertx().executeBlocking(future -> {
			try {
				JsonObject jsonRequest = routingContext.getBodyAsJson();
				String email = jsonRequest.getString("email");
				String password = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
				String encodePassword = Md5Code.md5(password);
				System.out.println(password);
				Map data = new HashMap();
				List<Map> user = SubService.recoverPassword(email, encodePassword);
				
				data.put("message", "recover passoword successfully");
				data.put("recover_password", password);
				
				
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
