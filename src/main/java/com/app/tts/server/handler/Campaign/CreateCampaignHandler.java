package com.app.tts.server.handler.campaign;

import com.app.tts.services.CampaignService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateCampaignHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                String userId = ParamUtil.getString(jsonRequest, AppParams.USER_ID);
                Map result = createCampaign(userId);
                Map data = new LinkedHashMap();
                String message = "created campaign";
                data.put(AppParams.ID, ParamUtil.getString(result, AppParams.S_ID_2));
                data.put(AppParams.MESSAGE, message);

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, data);

                future.complete();
            }catch (Exception e){
                routingContext.fail(e);
            }
        }, asyncResult -> {
            if(asyncResult.succeeded()){
                routingContext.next();
            }else{
                routingContext.fail(asyncResult.cause());
            }
        });
    }

    public static Map createCampaign(String userId) throws SQLException{
        Map  result = CampaignService.createCampaign(userId);
        return result;
    }
}
