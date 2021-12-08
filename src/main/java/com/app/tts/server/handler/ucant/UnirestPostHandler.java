package com.app.tts.server.handler.ucant;

import java.util.List;
import java.util.Map;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class UnirestPostHandler implements Handler<RoutingContext>{
	@Override
	public void handle(RoutingContext rc) {
		rc.vertx().executeBlocking(future -> {
			try {
				Map json = rc.getBodyAsJson().getMap();
				System.out.println("json: " +json);
//				String shipping_name = ParamUtil.getString(json, "shipping_name");
//				String shipping_address1 = ParamUtil.getString(json, "");
//				String shipping_address2 = ParamUtil.getString(json, "shipping_address2");
//				String shipping_city = ParamUtil.getString(json, "shipping_city");
//				String shipping_state = ParamUtil.getString(json, "shipping_state");
//				String shipping_zip = ParamUtil.getString(json, "shipping_zip");
//				String shipping_country = ParamUtil.getString(json, "shipping_country");
//				String shipping_email = ParamUtil.getString(json, "shipping_email");
//				String shipping_phone = ParamUtil.getString(json, "shipping_phone");
//				String ignore_address_check = ParamUtil.getString(json, "ignore_address_check");
//				String reference_order_id = ParamUtil.getString(json, "reference_order_id");
//				String sandbox = ParamUtil.getString(json, "sandbox");
//				String api_key = ParamUtil.getString(json, "api_key");
//				
//				String catalog_sku = "", design_url_front = "", mockup_url_front = "", quantity = "";
//				
//				List<Map> items = ParamUtil.getListData(json, "items");
//				for (Map map : items) {
//					catalog_sku = ParamUtil.getString(map, "catalog_sku");
//					design_url_front = ParamUtil.getString(map, "catalog_sku");
//					mockup_url_front = ParamUtil.getString(map, "mockup_url_front");
//					quantity = ParamUtil.getString(map, "quantity");
//				}
				
				HttpResponse<JsonNode> jsonResponse 
			      = Unirest.post("https://pro.30usd.com/pspfulfill/api/v1/dropship-api/order/v2")
			      .body(json).asJson();
				System.out.println(jsonResponse.getBody());
				rc.put(AppParams.RESPONSE_DATA, jsonResponse.getBody());
				
				future.complete();
			}catch (Exception e) {
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
