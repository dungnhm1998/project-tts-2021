package com.app.tts.server.handler.user;

import com.app.tts.services.UserService;
import com.app.tts.util.AppParams;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GetAllUserHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(getFutureHandler(rc), asyncResult -> {
            if (asyncResult.succeeded()) {
                rc.next();
            } else {
                rc.fail(asyncResult.cause());
            }
        });
    }

    @NotNull
    private Handler<Future<Object>> getFutureHandler(RoutingContext rc) {

        return future -> {
            try {
                Map data = new HashMap();

                LOGGER.info("Users result: " + UserService.getAllUser("active"));

                data.put("List_Customer", UserService.getAllUser("active"));
                rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                rc.put(AppParams.RESPONSE_DATA, data);
                future.complete();
            } catch (Exception e) {
                rc.fail(e);
            }
        };
    }

    private static final Logger LOGGER = Logger.getLogger(GetAllUserHandler.class.getName());
}
