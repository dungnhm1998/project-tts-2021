package com.app.tts.server.handler.Ucant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
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
            	String base_id = "", id = "", name = "", size_name = "", price = "",designs = "", mockups = "",
            			size_id = "";
            	List<Map> productBaseid = new ArrayList<Map>();
            	Map input = new HashMap();
            	List <Map> getproduct = ParamUtil.getListData(json, "products");
            	for(Map product : getproduct) {
            		base_id = ParamUtil.getString(product, AppParams.BASE_ID);
            		int de = ParamUtil.getBoolean(product, AppParams.DEFAULT)? 1 : 0;
            		designs = ParamUtil.getString(product, AppParams.DESIGNS);
            		StringJoiner coloridsub = new StringJoiner(",");
            		StringJoiner sizeidsub = new StringJoiner(",");
            		List<Map> getcolors = ParamUtil.getListData(product, "colors");
            			for(Map colors : getcolors) {
            				String colorid = ParamUtil.getString(colors, AppParams.ID);
            				if(!colorid.isEmpty()) {
            					coloridsub.add(colorid);
            				}
            				id = colorid.toString();
            				input.put("id", id);
            			}
            			LOGGER.info("id: " + id);
            		List<Map> getprices = ParamUtil.getListData(product, "prices");
            			for(Map prices : getprices) {
            				String sizeid = ParamUtil.getString(prices, AppParams.SIZE_ID);
            				if(!sizeid.isEmpty()) {
            					sizeidsub.add(sizeid);
            					size_id = sizeidsub.toString();
            				}
            				input.put("size_id", size_id);
            				size_name = ParamUtil.getString(prices, AppParams.SIZE_NAME);
            				price = ParamUtil.getString(prices, AppParams.PRICE);
            			}
            			LOGGER.info("sizeidsub " + sizeidsub);
        				LOGGER.info("size_id " + size_id);
    				List<Map> getmockup = ParamUtil.getListData(product, "mockups");
    					for(Map mockup : getmockup) {
    						mockups = ParamUtil.getString(mockup, AppParams.MOCKUP_IMG_URL);
    					}
            		input.put("base_id", base_id);
            		productBaseid.add(input);
            	}
            	LOGGER.info("input " + input);
            	LOGGER.info("productBaseid " + productBaseid);
            	
            	Map data = new HashMap();
            	
            	List<Map> camp = new ArrayList();
            	List<Map> product = new ArrayList();
            	List<Map> color = new ArrayList();
            	List<Map> size = new ArrayList();
            	for(Map input1 : productBaseid) {
	            	 camp = SubService.createProduct(campaign_id, base_id, id, 
	        				size_id, designs, mockups);
	         
	            	product = SubService.getProduct(campaign_id);
	            		for(Map map: product) {
	            			String productId = ParamUtil.getString(map, AppParams.S_ID);
	            			color = SubService.getColor(productId);
	            			size = SubService.getSize(productId);
	            			map.put("colors", color);
	            			map.put("sizes", size);
	            		}
            	}
            	
            	data.put("", camp);
            	data.put("products", product);
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
