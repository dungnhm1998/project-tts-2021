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
            	String base_id = "", id = "", name = "", value = "";
            	
            	List <Map> products = ParamUtil.getListData(json, "products");
            	for(Map map1 : products) {
            		base_id = ParamUtil.getString(map1, AppParams.BASE_ID);
            		int de = ParamUtil.getBoolean(map1, AppParams.DEFAULT)? 1 : 0;
            		List<Map> colors = ParamUtil.getListData(map1, "colors");
            			for(Map map2 : colors) {
            				id = ParamUtil.getString(map2, AppParams.ID);
            				name = ParamUtil.getString(map2, AppParams.NAME);
            				value = ParamUtil.getString(map2, AppParams.VALUE);
            			}
            		
            	}
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
