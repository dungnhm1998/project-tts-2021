package com.app.tts.server.handler.user;

import com.app.tts.util.AppParams;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.json.JSONObject;

import java.util.Map;
import java.util.logging.Logger;

public class UsHandler implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                String id = routingContext.request().getParam("id");

                HttpResponse<JsonNode> Response = Unirest.get("https://pro.30usd.com/pspfulfill/api/v1/dropship-api/order/v2/check-log/" + id)
                        .asJson();

                JSONObject myObj = Response.getBody().getObject();
                String data =  myObj.toString();
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
    private static final Logger LOGGER = Logger.getLogger(UsHandler.class.getName());
}
