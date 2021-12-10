package com.app.tts.server.handler.unirest2;

import com.app.tts.util.AppParams;
import com.fasterxml.jackson.core.FormatSchema;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.Map;

import static io.vertx.core.json.Json.mapper;

public class UnirestPostHandler2 implements Handler<RoutingContext> {
        @Override
        public void handle(RoutingContext routingContext) {
            routingContext.vertx().executeBlocking(future -> {
                try{
                    JsonObject jsonRequest = routingContext.getBodyAsJson();
                    System.out.println("---------------------------------------------------------------------------");

                    HttpResponse<JsonNode>  jsonResponse
                            = Unirest.post("https://pro.30usd.com/pspfulfill/api/v1/dropship-api/order/v2")
                            .body(mapper.writeValueAsString(jsonRequest)) //nhan String, JSONObject
                            .asJson();

                    System.out.println("------------------------------------------------------------------");
                    System.out.println("jsonResponse ----------------" + jsonResponse.getBody());
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
