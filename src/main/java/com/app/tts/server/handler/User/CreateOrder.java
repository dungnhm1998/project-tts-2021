package com.app.tts.server.handler.User;

import com.app.tts.services.UserService;
import com.app.tts.util.AppParams;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CreateOrder implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
            	HttpServerResponse response = rc.response();
                JsonObject jsonRequest = rc.getBodyAsJson();
                String source = jsonRequest.getString(AppParams.SOURCE);
                String currency = jsonRequest.getString(AppParams.CURRENCY);
                String note = jsonRequest.getString(AppParams.NOTE);
                String store_id = jsonRequest.getString(AppParams.STORE_ID);
                String reference_id = jsonRequest.getString(AppParams.REFERENCE_ID);
                String shipping_method = jsonRequest.getString(AppParams.SHIPPING_METHOD);
                
                JsonObject shipping = jsonRequest.getJsonObject("shipping");
                String email = shipping.getString(AppParams.EMAIL);
                String name = shipping.getString(AppParams.NAME);
                String phone = shipping.getString(AppParams.PHONE);
                String phone_ext = shipping.getString(AppParams.PHONE_EXT);
                
                JsonObject address = shipping.getJsonObject("address");
                String line1 = address.getString(AppParams.LINE1);
                String line2 = address.getString(AppParams.LINE2);
                String city = address.getString(AppParams.CITY);
                String state = address.getString(AppParams.STATE);
                String postal_code = address.getString(AppParams.POSTAL_CODE);
                String country = address.getString(AppParams.COUNTRY);
                String country_name = address.getString(AppParams.COUNTRY_NAME);
                Boolean addr_verified = address.getBoolean(AppParams.ADDR_VERIFIED);
                String add_verified_note = address.getString(AppParams.ADD_VERIFIED_NOTE);
                
                String extra_fee = jsonRequest.getString(AppParams.EXTRA_FEE);
                
                JsonObject items = jsonRequest.getJsonObject("items");
                String id = items.getString(AppParams.ID);
                Boolean isEdit = items.getBoolean(AppParams.ISEDIT);
                String base_id = items.getString(AppParams.BASE_ID);
                String base_name = items.getString(AppParams.BASE_NAME);
                String color = items.getString(AppParams.COLOR);
                String color_name = items.getString(AppParams.COLOR_NAME);
                String size_id = items.getString(AppParams.SIZE_ID);
                String size_name = items.getString(AppParams.SIZE_NAME);
                String quantity = items.getString(AppParams.QUANTITY);
                String price = items.getString(AppParams.PRICE);	
                
                JsonObject designs = items.getJsonObject("designs");
                String design_front_url = designs.getString(AppParams.DESIGN_FRONT_URL);
                String design_front_url_md5 = designs.getString(AppParams.DESIGN_FRONT_URL_MD5);
        		String design_back_url = designs.getString(AppParams.DESIGN_BACK_URL);
        		String design_back_url_md5 = designs.getString(AppParams.DESIGN_BACK_URL_MD5);
        		String mockup_front_url = designs.getString(AppParams.MOCKUP_FRONT_URL);
        		String mockup_back_url = designs.getString(AppParams.MOCKUP_BACK_URL);
        		
        		String variant_name = items.getString(AppParams.VARIANT_NAME);
        		String unit_amount = items.getString(AppParams.UNIT_AMOUNT);
        		Boolean shippingExpress = items.getBoolean(AppParams.SHIPPINGEXPRESS);
        		
        		String tax_amount = jsonRequest.getString(AppParams.TAX_AMOUNT);
        		String ioss_number = jsonRequest.getString(AppParams.IOSS_NUMBER);

                LOGGER.info("Order" + jsonRequest);
                rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
//                rc.put(AppParams.RESPONSE_DATA, jsonRequest);
                rc.response().end(Json.encodePrettily(jsonRequest));
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
    private static final Logger LOGGER = Logger.getLogger(CreateOrder.class.getName());

}
