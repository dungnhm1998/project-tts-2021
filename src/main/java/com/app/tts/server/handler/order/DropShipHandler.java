package com.app.tts.server.handler.order;

import java.util.logging.Logger;

import org.json.JSONObject;

import com.app.tts.util.AppConstants;
import com.app.tts.util.AppParams;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public class DropShipHandler implements Handler<RoutingContext> {

	private String url = AppConstants.URLAPIDRS;

	@Override
	public void handle(RoutingContext routingContext) {
		routingContext.vertx().executeBlocking(future -> {
			try {

				JsonObject jsonRequest = routingContext.getBodyAsJson();

				JSONObject jsonData = new JSONObject(jsonRequest.toString());

				HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
						.header("Content-Type", "application/json")
						.body(jsonData)
						.asJson();

				routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
				routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
				routingContext.put(AppParams.RESPONSE_DATA, jsonResponse.getBody().toString());
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

	private static final Logger LOGGER = Logger.getLogger(DropShipHandler.class.getName());
}