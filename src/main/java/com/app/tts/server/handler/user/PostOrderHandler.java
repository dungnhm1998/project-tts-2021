package com.app.tts.server.handler.user;

import com.app.tts.util.AppParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.json.JSONObject;

import java.util.Map;
import java.util.logging.Logger;

public class PostOrderHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map req = routingContext.getBodyAsJson().getMap();
                JSONObject reqBody = new JSONObject(req);
                String url = "https://pro.30usd.com/pspfulfill/api/v1/dropship-api/order/v2";

                HttpResponse<JsonNode> Response = Unirest
                        .post(url)
                        .header("Content-Type", "application/json")
                        .body(reqBody)
                        .asJson();

                LOGGER.info("json"+ reqBody);

                JSONObject myObj = Response.getBody().getObject();
                LOGGER.info("add order " + myObj);

                String data =  myObj.toString();

                LOGGER.info("response" + data);
                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
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
