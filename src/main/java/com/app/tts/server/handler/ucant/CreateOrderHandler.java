package com.app.tts.server.handler.ucant;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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
				String source = ParamUtil.getString(json, "source");
				String currency = ParamUtil.getString(json, AppParams.CURRENCY);
				String note = ParamUtil.getString(json, "note");
				String storeId = ParamUtil.getString(json, "store_id");
				String referenceId = ParamUtil.getString(json, "reference_id");
				String state = ParamUtil.getString(json, "state");
				String shippingMethod = ParamUtil.getString(json, "shipping_method");
				
				Map shipping = ParamUtil.getMapData(json, "shipping");
				String name = ParamUtil.getString(shipping, AppParams.NAME);
				String email = ParamUtil.getString(shipping, AppParams.EMAIL);
				String phone = ParamUtil.getString(shipping, AppParams.PHONE);
					
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
 				
 				String extraFee = ParamUtil.getString(json, "extra_fee");
 				
 				String id = "", baseId = "", color = "", colorId = "", colorName = "", sizeId = "", sizeName = "",
 						customData = "", source2 = "", campaignId = "", unitAmount = "", variantId = "", productId = "";
				int quantity = 1;
 				
 				List<Map> items = ParamUtil.getListData(json, "items");
 				for(Map map : items) {
 					productId = ParamUtil.getString(map, AppParams.S_ID);
 					baseId = ParamUtil.getString(map, "base_id");
 					color = ParamUtil.getString(map, "color");
 					colorId = ParamUtil.getString(map, "color_id"); 
 					colorName = ParamUtil.getString(map, "color_name");
 					sizeId = ParamUtil.getString(map, "size_id");
 					sizeName = ParamUtil.getString(map, "size_name");
 					customData = ParamUtil.getString(map, "custom_data");
 					quantity = ParamUtil.getInt(map, "quantity");
 					source2 = ParamUtil.getString(map, "source2");
 					campaignId = ParamUtil.getString(map, "campaign_id");
 					unitAmount = ParamUtil.getString(map, "unit_amount");
 					variantId = ParamUtil.getString(map, "variant_id");
 					productId = ParamUtil.getString(map, "product_id");
 					
 					Map designs = ParamUtil.getMapData(map, "designs");
 					String mockupFrontUrl = ParamUtil.getString(map, "mockup_front_url");
 					String mockupBackUrl = ParamUtil.getString(map, "mockup_back_url");
 					String designFrontUrl = ParamUtil.getString(map, "design_front_url");
 					String designBackUrl = ParamUtil.getString(map, "design_back_url");
 					
 					String dropShipProId = UUID.randomUUID().toString().replace("-", "").substring(0,16);
 				}
 				
 				String taxAmount = ParamUtil.getString(json, "tax_amount");
				String iossNumber = ParamUtil.getString(json, "ioss_number");
				
				String userId = "A2955";
				Random random = new Random();
				String orderId = userId + "-CT-" + random.nextInt();
 				String shippingId = UUID.randomUUID().toString().replace("-", "").substring(0,16);
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
