package com.app.tts.server.handler.User;

import com.app.tts.data.type.RedisKeyEnum;
import com.app.tts.services.GetBaseService;
import com.app.tts.services.GetOrderService;
import com.app.tts.services.RedisService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.*;

public class Get_OrderHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {

                Map listBaseRedis = RedisService.getMap(RedisKeyEnum.BASES_MAP);
                if (listBaseRedis == null || listBaseRedis.isEmpty()) {
                    listBaseRedis = getListBaseFromDB();
                }
                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, listBaseRedis);
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

    public static Map getListBaseFromDB() throws SQLException {
        Map listBaseDB = new HashMap();
//        List<Map> listorderandetail = GetOrderService.getOrderDetail();
        List<Map> listshipandorder = GetOrderService.getShippingService();

        Set<String> listOrderId = new HashSet();
        for (Map baseAndGroup : listshipandorder) {
            //get base id
            String shippingOrderId = ParamUtil.getString(baseAndGroup, AppParams.ORDER_ID);
            listOrderId.add(shippingOrderId);
        }
        //list base group
        for (int i = 0 ; i < listshipandorder.size(); i++) {
            List<Map> Listorder = new ArrayList();
            for (Map orderAndShipping : listshipandorder) {
                Listorder.add(orderAndShipping);
            }
            listBaseDB.put("orderName", Listorder);
        }

        return listBaseDB;
    }
}
