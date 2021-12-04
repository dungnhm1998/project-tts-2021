package com.app.tts.server.handler.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
				Map jsonRequest = routingContext.getBodyAsJson().getMap();
				String id = ParamUtil.getString(jsonRequest, AppParams.ID);
				if(DropShipOrderService.getAllOrder(id).isEmpty()) {
					data.put(MESSAGE, "order id not found!");
				}else {
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
					if(ShippingService.getAllShipping(shipping_id).isEmpty()) {
						data.put(MESSAGE, "shipping not found");
					}else {
						String original_id = ParamUtil.getString(jsonRequest, AppParams.ORIGINAL_ID);
						String source = ParamUtil.getString(jsonRequest, AppParams.SOURCE);
						String shipping_method = ParamUtil.getString(jsonRequest, AppParams.SHIPPING_METHOD);
						String ioss_number = ParamUtil.getString(jsonRequest, AppParams.IOSS_NUMBER);
						String extra_fee = ParamUtil.getString(jsonRequest, AppParams.EXTRA_FEE);
						String amount = ParamUtil.getString(jsonRequest, AppParams.AMOUNT);

						/*
						 * LocalDateTime myDateObj = LocalDateTime.now(); DateTimeFormatter myFormatObj
						 * = DateTimeFormatter.ofPattern("dd-MMM-yy"); String formattedDate =
						 * myDateObj.format(myFormatObj); long millis = System.currentTimeMillis();
						 * java.sql.Date date = new java.sql.Date(millis); Date update_date =
						 * date.valueOf(formattedDate);
						 */
						

						Map shippingMap = ParamUtil.getMapData(jsonRequest, AppParams.SHIPPING);
						String shippingId = ParamUtil.getString(shippingMap, AppParams.ID);
						if(!shipping_id.equals(shippingId)) {
							data.put(MESSAGE, "check shipping id");
						}else {
							String shippingName = ParamUtil.getString(shippingMap, AppParams.NAME);
							String shippingEmail = ParamUtil.getString(shippingMap, AppParams.EMAIL);
							String shippingPhone = ParamUtil.getString(shippingMap, AppParams.PHONE);
							int shippingGift = ParamUtil.getBoolean(shippingMap, AppParams.GIFT)? 1 : 0;

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

							List<Map> rOrdertList = new ArrayList<Map>();
							rOrdertList = DropShipOrderService.updateOrder(id, currency, sub_amount, shipping_fee,
									tax_amount, state, tracking_code, note, channel, user_id, store_id,
									shipping_id, original_id, source, shipping_method, ioss_number, extra_fee, amount, a_verified,
									a_verifiedNote);
							List<Map> rShippingList = new ArrayList<Map>();
							rShippingList = ShippingService.updateShipping(shippingId, shippingName, shippingEmail, shippingPhone, shippingGift, line1, line2, city, a_state, postalCode, country, countryName);
							List<Map> rProductList = new ArrayList<Map>();
							for (Map mapProduct : items) {
								pId = ParamUtil.getString(mapProduct, AppParams.ID);
								if(OrderProductService.getAllOrderProduct(pId).isEmpty()){
									data.put(MESSAGE, "order product not found");
								}else {
									baseId = ParamUtil.getString(mapProduct, AppParams.BASE_ID);
									colorName = ParamUtil.getString(mapProduct, AppParams.COLOR_NAME);
									colorValue = ParamUtil.getString(mapProduct, AppParams.COLOR_VALUE);
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
									rProductList = OrderProductService.updateOrderProduc(pId, baseId, colorValue, colorId, colorName,
											sizeId, sizeName, customData, quantity, campaignId, dsFUrl, dsBUrl, mFUrl, mBUrl, variantId,
											productId);
								}							
							}
							data.put("order", rOrdertList);
							data.put("ship", rShippingList);
							data.put("orderP", rProductList);
						}
					}
				}
				
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
