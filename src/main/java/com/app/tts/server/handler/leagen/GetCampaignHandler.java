package com.app.tts.server.handler.leagen;

import com.app.tts.services.CampaignService;
import com.app.tts.util.AppParams;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GetCampaignHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                Map data = new HashMap();

                List<Map> List_Campaign = CampaignService.GetCampaign();

                LOGGER.info("campaign result: " + List_Campaign);

                data.put("List_Campaign", List_Campaign);
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
    private static final Logger LOGGER = Logger.getLogger(GetCampaignHandler.class.getName());
}

