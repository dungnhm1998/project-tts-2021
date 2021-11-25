package com.app.tts.server.handler.Ucant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.services.SubService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
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
            	String base_id = "", id = "", name = "", value = "", size_id = "", size_name = "", price = "",
            			designs = "";
            	List <Map> products = ParamUtil.getListData(json, "products");
            	for(Map map : products) {
            		base_id = ParamUtil.getString(map, AppParams.BASE_ID);
            		int de = ParamUtil.getBoolean(map, AppParams.DEFAULT)? 1 : 0;
            		designs = ParamUtil.getString(map, AppParams.DESIGNS);
            		List<Map> colors = ParamUtil.getListData(map, "colors");
            			for(Map map1 : colors) {
            				id = ParamUtil.getString(map1, AppParams.ID);
            				name = ParamUtil.getString(map1, AppParams.NAME);
            				value = ParamUtil.getString(map1, AppParams.VALUE);
            			}
            		List<Map> prices = ParamUtil.getListData(map, "prices");
            			for(Map map2 : prices) {
            				size_id = ParamUtil.getString(map2, AppParams.SIZE_ID);
            				size_name = ParamUtil.getString(map2, AppParams.SIZE_NAME);
            				price = ParamUtil.getString(map2, AppParams.PRICE);
            			}
    				List<Map> mockups = ParamUtil.getListData(map, "mockups");
            	}
            	Map data = new HashMap();
            	LOGGER.info("body: " + json);
            	List<Map> camp = SubService.addProduct(campaign_id, user_id);
            	LOGGER.info("camp: " + camp);
            	data.put("", camp);
            	LOGGER.info("data: " + data);
            	System.out.println("data" + data);
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
	private static final Logger LOGGER = Logger.getLogger(AddProductHandler.class.getName());     
}
