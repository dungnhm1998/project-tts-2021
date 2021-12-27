package com.app.tts.server.handler.order;

import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetOrderByIdHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                String id = routingContext.request().getParam("id");
                Map data = new LinkedHashMap();

                Map resultOrder = getOrder(id);
                String message;
                if (resultOrder.isEmpty()) {
                    message = "Can't find order with id = " + id;
                    data.put(AppParams.RESPONSE_MSG, message);
                } else {
                    data.put(AppParams.RESPONSE_DATA, resultOrder);
                }

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
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

    public static Map getOrder(String id) throws SQLException {
        Map result = OrderService.getOrderById(id);
        return result;
    }
}
