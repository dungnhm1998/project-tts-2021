package com.app.tts.server.handler.unirest2;

import com.app.tts.util.AppParams;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.Map;

public class UnirestGetHandler2 implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try{
                String id = routingContext.request().getParam("id");
                //viet "/:id" thi dien luon value cua id sau "/"
                HttpResponse<JsonNode> jsonResponse
                        = Unirest.get("https://pro.30usd.com/pspfulfill/api/v1/dropship-api/order/v2/check-log/{id}")
                        .routeParam("id", id).asJson();
                Map result = jsonResponse.getBody().getObject().toMap();
                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, result);


                future.complete();
            }catch (Exception e){
                routingContext.fail(e);
            }
        }, asyncResult -> {
            if(asyncResult.succeeded()){
                routingContext.next();
            }else{
                routingContext.fail(asyncResult.cause());
            }
        });
    }
}
