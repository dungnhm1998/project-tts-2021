package com.app.tts.server.handler.order;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.app.tts.services.DropShipOrderService;
import com.app.tts.services.OrderProductService;
import com.app.tts.services.ShippingService;
import com.app.tts.util.AppParams;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class GetOrderHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map data = new LinkedHashMap();

                Map orderMap = DropShipOrderService.getAllOrder();
                Map shipMap  = ShippingService.getAllShipping();
                List<Map> productList = OrderProductService.getAllOrderProduct();

                data = DropShipOrderService.formatUpdateOrder(orderMap, shipMap, productList);
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