package com.app.tts.server.handler.ucant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.services.SubService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class UpdateOrderHandler implements Handler<RoutingContext>{
	@Override
    public void handle(RoutingContext routingContext) {
		routingContext.vertx().executeBlocking(future -> {
            try {
            	Map json = routingContext.getBodyAsJson().getMap();
            	Map data = new LinkedHashMap();
            	List<Map> product = new ArrayList();
            	
            	String orderId = ParamUtil.getString(json, AppParams.ID);
            	String subAmount = ParamUtil.getString(json, "sub_amount");
            	String shippingFee = ParamUtil.getString(json, "shipping_fee");
            	String taxAmount = ParamUtil.getString(json, "tax_amount");
            	String state = ParamUtil.getString(json, AppParams.STATE);
            	String trackingCode = ParamUtil.getString(json, "tracking_code");
            	String shippingMethod = ParamUtil.getString(json, "shipping_method");
            	String iossNumber = ParamUtil.getString(json, "ioss_number");
            	
            	Map ship = ParamUtil.getMapData(json, "shipping");
            	String shippingId = ParamUtil.getString(ship, AppParams.ID);
            	String name = ParamUtil.getString(ship, AppParams.NAME);
            	String email = ParamUtil.getString(ship, AppParams.EMAIL);
            	String phone = ParamUtil.getString(ship, AppParams.PHONE);
            	int gift = ParamUtil.getBoolean(ship, "gift")? 1 : 0;
            	LOGGER.info("shippingId: " + shippingId);
            	Map address = ParamUtil.getMapData(ship, "address");
            	String line1 = ParamUtil.getString(address, "line1");
    			String line2 = ParamUtil.getString(address, "line2");
    			String city = ParamUtil.getString(address, "city");
    			String state2 = ParamUtil.getString(address, "state");
    			String postalCode = ParamUtil.getString(address, "postal_code");
    			String country = ParamUtil.getString(address, "country");
    			String coutryName = ParamUtil.getString(address, "countryName");
    			int addrVerified = ParamUtil.getBoolean(address, "addr_verified")? 1 : 0;
    			String addrVerifiedNote = ParamUtil.getString(address, "addr_verified_note");
            	
    			String referenceId = ParamUtil.getString(json, "reference_id");
            	String requireRefund = ParamUtil.getString(json, "require_refund");
            	String extraFee = ParamUtil.getString(json, "extra_fee");
            	String amount = ParamUtil.getString(json, "amount");
            	
            	String id = "", baseId = "",  color = "", colorId = "", colorName = "", sizeId = "", sizeName = "",
            			quantity = "", amount2 = "", baseCost = "", mockupFrontUrl = "", mockupBackUrl = "",
            			designFrontUrl = "", designBackUrl = "", variantName = "", unitAmount = "";
            	List<Map> items = ParamUtil.getListData(json, "items");
            	for (Map map : items) {
            		id = ParamUtil.getString(map, "id");
            		baseId = ParamUtil.getString(map, AppParams.BASE_ID);
            		variantName = ParamUtil.getString(map, "variant_name");
            		color = ParamUtil.getString(map, "color");
            		colorId = ParamUtil.getString(map, "color_id");
            		colorName = ParamUtil.getString(map, "color_name");
            		sizeId = ParamUtil.getString(map, AppParams.SIZE_ID);
            		sizeName = ParamUtil.getString(map, AppParams.SIZE_NAME);
            		quantity = ParamUtil.getString(map, "quantity");
            		amount2 = ParamUtil.getString(map, "amount");
            		baseCost = ParamUtil.getString(map, AppParams.BASE_COST);
            		unitAmount = ParamUtil.getString(map, "unit_amount");
            		
            		Map designs = ParamUtil.getMapData(map, "designs");
            		mockupFrontUrl = ParamUtil.getString(designs, "mockup_front_url");
            		mockupBackUrl = ParamUtil.getString(designs, "mockup_back_url");
            		designFrontUrl = ParamUtil.getString(designs, "design_front_url");
            		designBackUrl = ParamUtil.getString(designs, "design_back_url");
            		
            		product = SubService.updateProduct(id, orderId, baseId, variantName, color, colorId, colorName,
            				sizeId, sizeName, quantity, amount2, baseCost, unitAmount, mockupFrontUrl, 
            				mockupBackUrl, designFrontUrl, designBackUrl);
            	}
            	
            	Map order = SubService.updateOrder(orderId, subAmount, shippingFee, taxAmount, state2, trackingCode, 
            			shippingMethod, requireRefund, referenceId, iossNumber, extraFee, unitAmount, addrVerified, 
            			addrVerifiedNote);
            	Map shipping = SubService.updateShipping(shippingId, variantName, email, phone, gift, line1, line2,
            			city, state2, postalCode, country, coutryName);
            	data = SubService.formatUpdateOrder(order, shipping, product);
            	
            	routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, data);
                future.complete();
            	
            } catch (Exception e) {
            	routingContext.fail(e);
		    }
		}, asyncResult -> {
		    if (asyncResult.succeeded()) {
		    	routingContext.next();
		    } else {
		    	routingContext.fail(asyncResult.cause());
		    }
        });
    }
	private static final Logger LOGGER = Logger.getLogger(UpdateOrderHandler.class.getName());    
}
