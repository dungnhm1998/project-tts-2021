package com.app.tts.server.handler.ucant;

import java.util.Map;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class UpdateOrderHandler implements Handler<RoutingContext>{
	@Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
            	Map json = rc.getBodyAsJson().getMap();
            	String idCampaign = ParamUtil.getString(json, AppParams.ID);
            	String currency = ParamUtil.getString(json, AppParams.CURRENCY);
            	String subAmount = ParamUtil.getString(json, AppParams.SUB_AMOUNT);
            	String shippingFee = ParamUtil.getString(json, AppParams.SHIPPING_FEE);
            	String taxAmount = ParamUtil.getString(json, AppParams.TAX_AMOUNT);
            	String state = ParamUtil.getString(json, AppParams.STATE);
            	String quantity = ParamUtil.getString(json, AppParams.QUANTITY);
            	String createDate = ParamUtil.getString(json, AppParams.CREATE_DATE);
            	String updateDate = ParamUtil.getString(json, AppParams.UPDATE_DATE);
            	String trackingCode = ParamUtil.getString(json, AppParams.TRACKING_CODE);
            	String orderDate = ParamUtil.getString(json, AppParams.ORDER_DATE);
            	String note = ParamUtil.getString(json, AppParams.NOTE);
            	String channel = ParamUtil.getString(json, AppParams.CHANNEL);
            	String userId = ParamUtil.getString(json, AppParams.USER_ID);
            	String storeId = ParamUtil.getString(json, AppParams.STORE_ID);
            	String shippingId = ParamUtil.getString(json, AppParams.SHIPPING_ID);
            	String originalId = ParamUtil.getString(json, AppParams.ORIGINAL_ID);
            	String source = ParamUtil.getString(json, AppParams.SOURCE);
            	String shippingMethod = ParamUtil.getString(json, AppParams.SHIPPING_METHOD);
            	String iossNumber = ParamUtil.getString(json, AppParams.IOSS_NUMBER);
            	
            	Map shipping = ParamUtil.getMapData(json, "shipping");
            	String id = ParamUtil.getString(shipping, AppParams.ID);
            	String 
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
