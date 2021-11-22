package com.app.tts.server.handler.leagen;

import com.app.tts.server.handler.User.DeleteUserHandler;
import com.app.tts.services.UserService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class createCampaignHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = rc.getBodyAsJson().getMap();
                String email = ParamUtil.getString(jsonRequest, AppParams.S_ID);
//                String username = jsonRequest.getString(AppParams.USERNAME);
//                String address = jsonRequest.getString(AppParams.ADDRESS);
//                String phone = jsonRequest.getString(AppParams.PHONE);
//                String state = jsonRequest.getString(AppParams.STATE);


                Map data = new HashMap();

                rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
//                data.put("email", email);

                LOGGER.info("---email = " + email);
                List<Map> user = UserService.getUserByEmail(email);


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
    public static final Logger LOGGER = Logger.getLogger(createCampaignHandler.class.getName());
}
