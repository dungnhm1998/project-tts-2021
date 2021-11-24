package com.app.tts.server.handler.campaign;

import com.app.tts.services.CreateCampaignService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CreateCampaignHandler implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Map jsonrequest = rc.getBodyAsJson().getMap();
                String user_id = ParamUtil.getString(jsonrequest, "id");


                Map data = new HashMap();
                Map json = CreateCampaignService.createCampaign(user_id);
                data.put("id", ParamUtil.getString(json, "S_ID"));
                data.put("message","created campaign");
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

    private static final Logger LOGGER = Logger.getLogger(CreateProductHandler.class.getName());
}
