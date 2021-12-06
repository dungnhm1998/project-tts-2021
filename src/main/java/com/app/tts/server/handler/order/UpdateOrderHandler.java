package com.app.tts.server.handler.order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.app.tts.services.DropShipOrderService;
import com.app.tts.services.OrderProductService;
import com.app.tts.services.ShippingService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class UpdateOrderHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext routingContext) {
		routingContext.vertx().executeBlocking(future -> {
			try {
				Map data = new HashMap();
				String MESSAGE = "message";
				Date date_update = new Date(System.currentTimeMillis());
				Map jsonRequest = routingContext.getBodyAsJson().getMap();
				Map rOrdertList = new HashMap();
				Map rShippingList = new HashMap();
				List<Map> rProductList = new ArrayList<Map>();
				List<Map> dataProductList = new ArrayList<Map>();
				Set<String> listItem = new HashSet();

				String id = ParamUtil.getString(jsonRequest, AppParams.ID);
				String currency = ParamUtil.getString(jsonRequest, AppParams.CURRENCY);
				String sub_amount = ParamUtil.getString(jsonRequest, AppParams.SUB_AMOUNT);
				String shipping_fee = ParamUtil.getString(jsonRequest, AppParams.SHIPPING_FEE);
				String tax_amount = ParamUtil.getString(jsonRequest, AppParams.TAX_AMOUNT);
				String state = ParamUtil.getString(jsonRequest, AppParams.STATE);
				String tracking_code = ParamUtil.getString(jsonRequest, AppParams.TRACKING_CODE);
				String note = ParamUtil.getString(jsonRequest, AppParams.NOTE);
				String channel = ParamUtil.getString(jsonRequest, AppParams.CHANNEL);
				String user_id = ParamUtil.getString(jsonRequest, AppParams.USER_ID);
				String store_id = ParamUtil.getString(jsonRequest, AppParams.STORE_ID);
				String shipping_id = ParamUtil.getString(jsonRequest, AppParams.SHIPPING_ID);
				String original_id = ParamUtil.getString(jsonRequest, AppParams.ORIGINAL_ID);
				String source = ParamUtil.getString(jsonRequest, AppParams.SOURCE);
				String shipping_method = ParamUtil.getString(jsonRequest, AppParams.SHIPPING_METHOD);
				String ioss_number = ParamUtil.getString(jsonRequest, AppParams.IOSS_NUMBER);
				String extra_fee = ParamUtil.getString(jsonRequest, AppParams.EXTRA_FEE);
				String amount = ParamUtil.getString(jsonRequest, AppParams.AMOUNT);

				Map shippingMap = ParamUtil.getMapData(jsonRequest, AppParams.SHIPPING);
				String shippingId = ParamUtil.getString(shippingMap, AppParams.ID);
				String shippingName = ParamUtil.getString(shippingMap, AppParams.NAME);
				String shippingEmail = ParamUtil.getString(shippingMap, AppParams.EMAIL);
				String shippingPhone = ParamUtil.getString(shippingMap, AppParams.PHONE);
				int shippingGift = ParamUtil.getBoolean(shippingMap, AppParams.GIFT) ? 1 : 0;

				Map address = ParamUtil.getMapData(shippingMap, AppParams.ADDRESS);
				String line1 = ParamUtil.getString(address, AppParams.LINE1);
				String line2 = ParamUtil.getString(address, AppParams.LINE2);
				String city = ParamUtil.getString(address, AppParams.CITY);
				String a_state = ParamUtil.getString(address, AppParams.STATE);
				String postalCode = ParamUtil.getString(address, AppParams.POSTAL_CODE);
				String country = ParamUtil.getString(address, AppParams.COUNTRY);
				String countryName = ParamUtil.getString(address, AppParams.COUNTRY_NAME);
				int a_verified = ParamUtil.getBoolean(address, AppParams.ADDR_VERIFIED) ? 1 : 0;
				String a_verifiedNote = ParamUtil.getString(address, AppParams.ADDR_VERIFIED_NOTE);

				List<Map> items = ParamUtil.getListData(jsonRequest, AppParams.ITEMS);
				String pId = "", baseId = "", colorValue = "", colorName = "", colorId = "", sizeId = "", sizeName = "",
						customData = "", campaignId = "", unitAmount = "", dsFUrl = "", dsBUrl = "", mFUrl = "",
						mBUrl = "", variantId = "", productId = "";
				int quantity = 0;

				for (Map mapProduct : items) {
					pId = ParamUtil.getString(mapProduct, AppParams.ID);
					listItem.add(pId);
					baseId = ParamUtil.getString(mapProduct, AppParams.BASE_ID);
					colorName = ParamUtil.getString(mapProduct, AppParams.COLOR_NAME);
					colorValue = ParamUtil.getString(mapProduct, AppParams.COLOR);
					colorId = ParamUtil.getString(mapProduct, AppParams.COLOR_ID);
					sizeId = ParamUtil.getString(mapProduct, AppParams.SIZE_ID);
					sizeName = ParamUtil.getString(mapProduct, AppParams.SIZE_NAME);
					customData = ParamUtil.getString(mapProduct, AppParams.CUSTOM_DATA);
					campaignId = ParamUtil.getString(mapProduct, AppParams.CAMPAIGN_ID);
					unitAmount = ParamUtil.getString(mapProduct, AppParams.UNIT_AMOUNT);
					Map designsMap = ParamUtil.getMapData(mapProduct, AppParams.DESIGNS);
					dsFUrl = ParamUtil.getString(designsMap, AppParams.DESIGN_FRONT_URL);
					dsBUrl = ParamUtil.getString(designsMap, AppParams.DESIGN_BACK_URL);
					mFUrl = ParamUtil.getString(designsMap, AppParams.VARIANT_FRONT_URL);
					mBUrl = ParamUtil.getString(designsMap, AppParams.VARIANT_BACK_URL);
					variantId = ParamUtil.getString(mapProduct, AppParams.VARIANT_ID);
					productId = ParamUtil.getString(mapProduct, AppParams.PRODUCT_ID);
					quantity = ParamUtil.getInt(mapProduct, AppParams.QUANTITY);

					
					rProductList = OrderProductService.updateOrderProduc(pId, id, baseId, colorValue, colorId,
							colorName, sizeId, sizeName, customData, quantity, campaignId, dsFUrl, dsBUrl, mFUrl, mBUrl,
							variantId, productId);
					
				}
				
				rOrdertList = DropShipOrderService.updateOrder(id, currency, sub_amount, shipping_fee, tax_amount,
						state, date_update, tracking_code, note, channel, user_id, store_id, shipping_id,
						original_id, source, shipping_method, ioss_number, extra_fee, amount, a_verified,
						a_verifiedNote);
				rShippingList = ShippingService.updateShipping(shippingId, shippingName, shippingEmail,
						shippingPhone, shippingGift, line1, line2, city, a_state, postalCode, country, countryName);
				
				
				data = DropShipOrderService.formatUpdateOrder(rOrdertList, rShippingList, rProductList);

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
