package com.app.tts.server.handler.campaign;

import com.app.tts.services.CampaignService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CreateCampaignHandler2 implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try{
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                String userId = ParamUtil.getString(jsonRequest, AppParams.USER_ID);

                List<Map> listIdUser = CampaignService.getIdUser(userId);
                Map data = new LinkedHashMap();
                if(!listIdUser.isEmpty()) {
                    Map result = createCampaign(userId);
                    data.put(AppParams.ID, ParamUtil.getString(result, AppParams.S_ID));
                    data.put(AppParams.MESSAGE, "created campaign");

                    routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                    routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                }else {
                    routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                    routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
                }
                routingContext.put(AppParams.RESPONSE_DATA, data);

                future.complete();
            }catch (Exception e){
                routingContext.fail(e);
            }
        }, asyncResult -> {
            if(asyncResult.succeeded()){
                routingContext.next();
            }else {
                routingContext.fail(asyncResult.cause());
            }
        });
    }

    public static Map createCampaign(String userId) throws SQLException {
        Map result = CampaignService.createCampaign(userId);
        return result;
    }
}
