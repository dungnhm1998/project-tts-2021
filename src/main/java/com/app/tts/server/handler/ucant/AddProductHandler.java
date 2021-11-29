package com.app.tts.server.handler.ucant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
            	
            	String campaignId = ParamUtil.getString(json, AppParams.CAMPAIGN_ID);
            	String userId = ParamUtil.getString(json, AppParams.USER_ID);
            	String baseId = "", id = "", sizeId = "", price = "",designs = "", mockups = "";
            	List <Map> getproduct = ParamUtil.getListData(json, "products");

            	Map camp = new LinkedHashMap();
            	List<Map> product = new ArrayList();
            	List<Map> color = new ArrayList();
            	List<Map> size = new ArrayList();            	

            	for(Map products : getproduct) {
            		baseId = ParamUtil.getString(products, AppParams.BASE_ID);
            		int de = ParamUtil.getBoolean(products, AppParams.DEFAULT)? 1 : 0;
            		designs = ParamUtil.getString(products, AppParams.DESIGNS);
            		
            		Map input = new HashMap();
            		input.put("baseId", baseId);
            		StringJoiner coloridsub = new StringJoiner(",");
            		StringJoiner sizeidsub = new StringJoiner(",");
            		StringJoiner pricesub = new StringJoiner(",");
            		List<Map> getcolors = ParamUtil.getListData(products, "colors");
            			for(Map colors : getcolors) {
            				String colorid = ParamUtil.getString(colors, AppParams.ID);
    
            				if(!colorid.isEmpty()) {
            					coloridsub.add(colorid);
            				}
            				id = colorid.toString();  				
            			}
            			
            		List<Map> getprices = ParamUtil.getListData(products, "prices");
            			for(Map prices : getprices) {
            				
            				String sizeid = ParamUtil.getString(prices, AppParams.SIZE_ID);
            				if(!sizeid.isEmpty()) {
            					sizeidsub.add(sizeid);
            				}
            				sizeId = sizeidsub.toString();
            				
            				String saleprice = ParamUtil.getString(prices, AppParams.PRICE);
            				if(!saleprice.isEmpty()) {
            					pricesub.add(saleprice);
            				}
            				price = pricesub.toString();
            			}
            			
    				List<Map> getmockup = ParamUtil.getListData(products, "mockups");
    					for(Map mockup : getmockup) {
    						mockups = ParamUtil.getString(mockup, AppParams.MOCKUP_IMG_URL);
    					}
    					
					camp = SubService.createProduct(campaignId, baseId, id, 
	        				sizeId, designs, mockups, price);
	         
	            	product = SubService.getProduct(campaignId);
	            		for(Map map: product) {
	            			String productId = ParamUtil.getString(map, AppParams.S_ID);
	            			color = SubService.getColor(productId);
	            			size = SubService.getSize(productId);
	            			map.put("colors", color);
	            			map.put("sizes", size);
	            		}
	            		LOGGER.info("color: " + color);
            	}
            	
            	Map data = new HashMap();
            	
            	data.put("", camp);
            	data.put("products", product);
            	LOGGER.info("data: " + data);
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
