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
            			designs = "", mockups = "";
            	List <Map> getproduct = ParamUtil.getListData(json, "products");
            	for(Map product : getproduct) {
            		base_id = ParamUtil.getString(product, AppParams.BASE_ID);
            		int de = ParamUtil.getBoolean(product, AppParams.DEFAULT)? 1 : 0;
            		designs = ParamUtil.getString(product, AppParams.DESIGNS);
            		List<Map> getcolors = ParamUtil.getListData(product, "colors");
            			for(Map colors : getcolors) {
            				id = ParamUtil.getString(colors, AppParams.ID);
            				name = ParamUtil.getString(colors, AppParams.NAME);
            				value = ParamUtil.getString(colors, AppParams.VALUE);
            			}
            		List<Map> getprices = ParamUtil.getListData(product, "prices");
            			for(Map prices : getprices) {
            				size_id = ParamUtil.getString(prices, AppParams.SIZE_ID);
            				size_name = ParamUtil.getString(prices, AppParams.SIZE_NAME);
            				price = ParamUtil.getString(prices, AppParams.PRICE);
            			}
    				List<Map> getmockup = ParamUtil.getListData(product, "mockups");
    					for(Map mockup : getmockup) {
    						mockups = ParamUtil.getString(mockup, AppParams.MOCKUP_IMG_URL);
    					}
    					
            	}
            	Map data = new HashMap();
            	LOGGER.info("body: " + json);
            	
            	List<Map> camp = SubService.createProduct(campaign_id, base_id, id, 
        				size_id, designs, mockups);
            	LOGGER.info("camp:" + camp);
            	List<Map> product = SubService.getProduct(campaign_id);
            		for(Map map: product) {
            			String productId = ParamUtil.getString(map, AppParams.S_ID);
            			List<Map> color = SubService.getColor(productId);
            			LOGGER.info("color:" + color);
            			List<Map> size = SubService.getSize(productId);
            			LOGGER.info("size:" + size);
            			map.put("colors", color);
            			map.put("sizes", size);
            		}
            	
            	data.put("", camp);
            	LOGGER.info("camp: " + camp);
            	data.put("products", product);
            	LOGGER.info("products: " + product);
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
