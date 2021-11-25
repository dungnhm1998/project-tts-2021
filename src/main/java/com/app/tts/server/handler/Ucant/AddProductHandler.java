package com.app.tts.server.handler.Ucant;

import java.util.List;
import java.util.Map;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public class AddProductHandler implements Handler<RoutingContext>{
	@Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
            	Map json = rc.getBodyAsJson().getMap();
            	String campaign_id = ParamUtil.getString(json, AppParams.CAMPAIGN_ID);
            	String user_id = ParamUtil.getString(json, AppParams.USER_ID);
            	List <Map> products = ParamUtil.getListData(json, "products");
            	
            	
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
