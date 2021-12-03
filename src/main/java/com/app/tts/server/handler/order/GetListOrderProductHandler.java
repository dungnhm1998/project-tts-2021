package com.app.tts.server.handler.order;

import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
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
                List<Map> listOrder = getListOrderProduct();

                data.put("total", listOrder.size());
                data.put("orders", listOrder);

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

        Map<String, List<Map>> orderProductMap = new LinkedHashMap<>();

        for (int number = 0; number < listProduct.size(); number++) {
            Map productMap = listProduct.get(number);
            String sOrderId = ParamUtil.getString(productMap, AppParams.ORDER_ID);
            if (orderProductMap.containsKey(sOrderId)) {
                orderProductMap.get(sOrderId).add(productMap);
            } else {
                List<Map> orderProductList = new ArrayList<>();
                orderProductList.add(productMap);
                orderProductMap.put(sOrderId, orderProductList);
            }
        }

        for (int number = 0; number < listOrder.size(); number++) {
            Map orderMap = listOrder.get(number);
            String orderId = ParamUtil.getString(orderMap, AppParams.ID);
            if (orderProductMap.containsKey(orderId)) {
                orderMap.put(AppParams.ORDER_PRODUCT, orderProductMap.get(orderId));
            }else{
                orderMap.put(AppParams.ORDER_PRODUCT, new LinkedHashMap<>());
            }
        }
        return listOrder;
    }
}
