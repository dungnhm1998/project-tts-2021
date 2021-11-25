package com.app.tts.server.handler.Order;

import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetListOrderProductHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map data = new LinkedHashMap();

                data.put(AppParams.RESPONSE_DATA, getListOrderProduct());

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

    public static List<Map> getProduct() throws SQLException {
        List<Map> listProduct = OrderService.getOrderProduct();
        return listProduct;
    }

    public static List<Map> getListOrderProduct() throws SQLException {
        List<Map> listOrder = OrderService.getOrder();
        List<Map> listProduct = OrderService.getOrderProduct();

        List<Map> result = new ArrayList<>();

        Map<String, List<Map>> orderProductMap = new LinkedHashMap<>();
        List<Map> orderProductList = new ArrayList<>();
        for (int number = 0; number < listProduct.size(); number++) {
            Map productMap = listProduct.get(number);
            String sOrderId = ParamUtil.getString(productMap, AppParams.S_ORDER_ID);
            if (orderProductMap.containsKey(sOrderId)) {
                orderProductList.add(productMap);
            } else {
                orderProductList = new ArrayList<>();
                orderProductList.add(productMap);
                orderProductMap.put(sOrderId, orderProductList);
            }
        }

        for (int number = 0; number < listOrder.size(); number++) {
            Map orderMap = listOrder.get(number);
            String orderId = ParamUtil.getString(orderMap, AppParams.S_ID_2);
            if (orderProductMap.containsKey(orderId)) {
                orderMap.put(AppParams.ORDER_PRODUCT, orderProductMap.get(orderId));
            }
        }
        return listOrder;
    }
}
