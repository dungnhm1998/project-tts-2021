package com.app.tts.server.handler.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.tts.services.SubService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public class CreateCamHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
            	JsonObject json = rc.getBodyAsJson();
            	String user_id = json.getString("user_id");
            	
            	List<Map> user = SubService.createCam(user_id);
            	Map data = new HashMap();
            	Map a = user.get(0);
            	String id = ParamUtil.getString(a, AppParams.S_ID);
            	data.put("id", id);
            	data.put("message", "create campaign");
            	rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
                rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
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
	
}
