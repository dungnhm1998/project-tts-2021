package com.app.tts.server.handler.ucant;

import java.util.List;
import java.util.Map;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class CreateOrderHandler implements Handler<RoutingContext>{
	@Override
	public void handle(RoutingContext rc) {
		rc.vertx().executeBlocking(future -> {
			try {
				Map json = rc.getBodyAsJson().getMap();
				String orderId = ParamUtil.getString(json, AppParams.S_ID);
				String currency = ParamUtil.getString(json, AppParams.CURRENCY);
				String subAmount = ParamUtil.getString(json, AppParams.SUB_AMOUNT);
				String shippingFee = ParamUtil.getString(json, "shipping_fee");
				String taxAmount = ParamUtil.getString(json, "tax_amount");
				String state = ParamUtil.getString(json, "state");
				String quantity = ParamUtil.getString(json, "quantity");
				String createDate = ParamUtil.getString(json, "create_date");
				String updateDate = ParamUtil.getString(json, "update_date");
				String trackingCode = ParamUtil.getString(json, "tracking_code");
				String orderDate = ParamUtil.getString(json, "order_date");
				String note = ParamUtil.getString(json, "note");
				String channel = ParamUtil.getString(json, "channel");
				String userId = ParamUtil.getString(json, "user_id");
				String storeId = ParamUtil.getString(json, "store_id");
				String shippingId = ParamUtil.getString(json, "shipping_id");
				String originalId = ParamUtil.getString(json, "original_id");
				String source = ParamUtil.getString(json, "source");
				String shippingMethod = ParamUtil.getString(json, "shipping_method");
				int fulfillState = (ParamUtil.getString(json, "fulfill_state")).equals("fulfilled")? 1 : 0;
				String iossNumber = ParamUtil.getString(json, "ioss_number");
				
				Map shipping = ParamUtil.getMapData(json, "shipping");
				String idShipping = ParamUtil.getString(shipping, AppParams.S_ID);
				String name = ParamUtil.getString(shipping, AppParams.NAME);
				String email = ParamUtil.getString(shipping, AppParams.EMAIL);
				String phone = ParamUtil.getString(shipping, AppParams.PHONE);
				int gift = ParamUtil.getBoolean(shipping, "gift")? 1 : 0;
				
				Map address = ParamUtil.getMapData(shipping, "address");
 				String line1 = ParamUtil.getString(address, AppParams.LINE1);
 				String line2 = ParamUtil.getString(address, AppParams.LINE2);
 				String city = ParamUtil.getString(address, AppParams.CITY);
 				String state2 = ParamUtil.getString(address, AppParams.STATE);
 				String postalCode = ParamUtil.getString(address, AppParams.POSTAL_CODE);
 				String country = ParamUtil.getString(address, AppParams.COUNTRY);
 				String countryName = ParamUtil.getString(address, AppParams.COUNTRY_NAME);
 				int addrVerified = ParamUtil.getBoolean(address, "addr_verified")? 1 : 0;
 				String addrVerifiedNote = ParamUtil.getString(address, AppParams.ADDR_VERIFIED_NOTE);
 				
 				String referenceId = ParamUtil.getString(json, "reference_id");
 				String requireRefund = ParamUtil.getString(json, "require_refund");
 				String extraFee = ParamUtil.getString(json, "extra_fee");
 				String amount = ParamUtil.getString(json, "amount");
 				
 				List<Map> items = ParamUtil.getListData(json, "items");
 				for(Map map : items) {
 					String id = ParamUtil.getString(map, AppParams.S_ID);
 					String campaignTitle = ParamUtil.getString(map, "campaign_title");
 					String campaignUrl = ParamUtil.getString(map, "campaign_url");
 					String idUser = ParamUtil.getString(map, "user_id");
 					Map campagin = ParamUtil.getMapData(map, "campaign");
 					String productId = ParamUtil.getString(map, "product_id");
 					String baseId = ParamUtil.getString(map, "base_id");
 					String variantId = ParamUtil.getString(map, "variant_id");
 					String variantName = ParamUtil.getString(map, "variant_name");
 					String sizeId = ParamUtil.getString(map, "size_id");
 					String sizeName = ParamUtil.getString(map, "size_name");
 					String colorId = ParamUtil.getString(map, "color_id"); 
 					String colorName = ParamUtil.getString(map, "color_name");
 					String color = ParamUtil.getString(map, "color");
 					String variantImage = ParamUtil.getString(map, "variant_image");
 					String price = ParamUtil.getString(map, "price");
 					String currency2 = ParamUtil.getString(map, "currency");
 					String quantity2 = ParamUtil.getString(map, "quantity");
 					String shippingFee2 = ParamUtil.getString(map, "shipping_fee");
 					String amount2 = ParamUtil.getString(map, "amount");
 					String state3 = ParamUtil.getString(map, "state");
 					String baseCost = ParamUtil.getString(map, "base_cost");
 					String line_item_id = ParamUtil.getString(map, "line_item_id");
 					String shipping_method = ParamUtil.getString(map, "shipping_method");
 					String item_type = ParamUtil.getString(map, "item_type");
 					String print_detail = ParamUtil.getString(map, "print_detail");
 					
 					Map designs = ParamUtil.getMapData(map, "designs");
 					String mockupFrontUrl = ParamUtil.getString(map, "mockup_front_url");
 					String mockupBackUrl = ParamUtil.getString(map, "mockup_back_url");
 					String designFrontUrl = ParamUtil.getString(map, "design_front_url");
 					String designBackUrl = ParamUtil.getString(map, "design_back_url");
 					
 					String partnerSku = ParamUtil.getString(map, "partner_sku");
 					String partnerUrl = ParamUtil.getString(map, "partner_url");
 					String baseShortCode = ParamUtil.getString(map, "base_short_code");
 					String unit_amount = ParamUtil.getString(map, "unit_amount");
 					String tax = ParamUtil.getString(map, "tax");
 					String taxAmount2 = ParamUtil.getString(map, "tax_amount");
 					String taxRate = ParamUtil.getString(map, "tax_rate");
 					
 					Map brand = ParamUtil.getMapData(map, "brand");
 					
 					String source2 = ParamUtil.getString(map, "source");
 				}
 				
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
