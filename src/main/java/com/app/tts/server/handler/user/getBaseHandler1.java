package com.app.tts.server.handler.user;

import com.app.tts.data.type.RedisKeyEnum;
import com.app.tts.services.GetBaseService1;
import com.app.tts.services.RedisService;
import com.app.tts.util.AppParams;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class getBaseHandler1 implements Handler<RoutingContext> {

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
        List<Map> listBaseAndGroup = GetBaseService1.getBaseService();
        List<Map> listBaseColor = GetBaseService1.getBaseColor();
        List<Map> listBaseSize = GetBaseService1.getBaseSize();


        Map listBaseDB = GetBaseService1.format(listBaseAndGroup, listBaseColor, listBaseSize);
        //list base group
        return listBaseDB;
    }

    private static final Logger LOGGER = Logger.getLogger(getBaseHandler1.class.getName());
}
