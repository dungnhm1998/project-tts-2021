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
                String id = ParamUtil.getString(jsonRequest, AppParams.S_ID);
                String site_name = ParamUtil.getString(jsonRequest, AppParams.S_ID);
                String name = ParamUtil.getString(jsonRequest, AppParams.S_ID);
                String email = ParamUtil.getString(jsonRequest, AppParams.S_ID);

                Map tracking_tags = ParamUtil.getMapData(jsonRequest, AppParams.TRACKING_TAGS);
                String fb_pixel = ParamUtil.getString(tracking_tags, AppParams.S_ID);
                String gg_adword_id = ParamUtil.getString(tracking_tags, AppParams.S_ID);
                String gg_analytics_id = ParamUtil.getString(tracking_tags, AppParams.S_ID);
                String gg_site_verification = ParamUtil.getString(tracking_tags, AppParams.S_ID);
                String gg_tag_manager_id = ParamUtil.getString(tracking_tags, AppParams.S_ID);
                String gg_conversion_tracking_id = ParamUtil.getString(tracking_tags, AppParams.S_ID);
                String tiktok_pixel = ParamUtil.getString(tracking_tags, AppParams.S_ID);

                
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
