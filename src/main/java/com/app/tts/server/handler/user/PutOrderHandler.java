package com.app.tts.server.handler.user;

import com.app.tts.util.AppParams;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.json.JSONObject;

import java.util.logging.Logger;

public class PutOrderHandler implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                JsonObject jsonObject = routingContext.getBodyAsJson();
                JSONObject json = new JSONObject(jsonObject.toString());
                String api = "https://pro.30usd.com/pspfulfill/api/v1/dropship-api/order/v2";

                HttpResponse<JsonNode> Response = Unirest
                        .put(api)
                        .header("Content-Type", "application/json")
                        .body(json)
                        .asJson();

                LOGGER.info("json" + json);

                JSONObject myObj = Response.getBody().getObject();
                LOGGER.info("add order " + myObj);
                String data = myObj.toString();

                LOGGER.info("response" + data);
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

    private static final Logger LOGGER = Logger.getLogger(PostOrderHandler.class.getName());


}

