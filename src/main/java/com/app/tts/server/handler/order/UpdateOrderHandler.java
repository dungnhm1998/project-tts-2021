package com.app.tts.server.handler.order;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.services.DropShipOrderService;
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
				Map jsonRequest = routingContext.getBodyAsJson().getMap();
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

				
				/*LocalDateTime myDateObj = LocalDateTime.now();  
			    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yy");  	    
			    String formattedDate = myDateObj.format(myFormatObj);  
			    long millis = System.currentTimeMillis();
		        java.sql.Date date = new java.sql.Date(millis);
				Date update_date = date.valueOf(formattedDate);*/
				Date update_date = null;
				Date order_date = null;

				
				Map data = new HashMap();

				if(DropShipOrderService.getOrderById(id).isEmpty()) {
					data.put("message", "order not found");
				}else {
					List<Map> drop_ship_order = DropShipOrderService.updateOrder(id, currency, sub_amount, shipping_fee,
							tax_amount, state, update_date, tracking_code, order_date, note, channel, user_id, store_id,
							shipping_id, original_id, source, shipping_method, ioss_number);
					data.put("", drop_ship_order);
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
