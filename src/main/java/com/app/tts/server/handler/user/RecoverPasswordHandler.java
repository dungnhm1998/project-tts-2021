package com.app.tts.server.handler.user;

import com.app.tts.encode.Md5Code;
import com.app.tts.services.UserService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class RecoverPasswordHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Map jsonrequest = rc.getBodyAsJson().getMap();
                String email = ParamUtil.getString(jsonrequest, AppParams.EMAIL);
                String password = UUID.randomUUID().toString().substring(0,6);
                Map data = new HashMap();
                UserService.updatePass(email, Md5Code.md5(password));
                data.put("message: ", "recover password successfully");
                data.put("recover_password: ", password);
                LOGGER.info("data: "+ data);
                rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                rc.put(AppParams.RESPONSE_DATA, data);
                future.complete();
            } catch (Exception e) {
                rc.fail(e);
            }
        }, asyncResult -> {
            if (asyncResult.succeeded()) {
                rc.next();
            } else {
                rc.fail(asyncResult.cause());
            }
        });
    }

    private static final Logger LOGGER = Logger.getLogger(RecoverPasswordHandler.class.getName());
}
