package com.app.tts.server.handler.Order;

import com.app.tts.util.AppParams;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

public class updateDemoHanler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {


                Map data = new HashMap();

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, data);

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
