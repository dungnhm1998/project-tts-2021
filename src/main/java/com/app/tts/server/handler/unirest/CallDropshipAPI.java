package com.app.tts.server.handler.unirest;

import java.util.logging.Logger;

import com.app.tts.util.AppConstants;
import com.app.tts.util.AppParams;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class CallDropshipAPI implements Handler<RoutingContext> {

	private String Path = "/check-log/";
	private String url = AppConstants.URLAPIDRS + Path;

	@Override
	public void handle(RoutingContext routingContext) {
		routingContext.vertx().executeBlocking(future -> {
			try {
				String id = routingContext.request().getParam("id");
				HttpResponse<JsonNode> response = Unirest.get(url + id).asJson();

				routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
				routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
				routingContext.put(AppParams.RESPONSE_DATA, response.getBody().toString());
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

	private static final Logger LOGGER = Logger.getLogger(CallDropshipAPI.class.getName());
}