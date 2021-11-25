package com.app.tts.server.handler.Order;

import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InsertOrderShippingProductHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try{
                JsonObject jsonRequest = routingContext.getBodyAsJson();
                Map mapRequest = jsonRequest.getMap();
                Map result = inputData(mapRequest);

                routingContext.response().end(Json.encodePrettily(result));

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
            }catch (Exception e){
                routingContext.fail(e);
            }
        }, asyncResult -> {
            if(asyncResult.succeeded()){
                routingContext.next();
            }else {
                routingContext.fail(asyncResult.cause());
            }
        });
    }

    public static Map inputData(Map mapRequest) throws SQLException {
        //order
        Random rand = new Random();
        String order_id = String.valueOf(rand.nextInt(1000000000));
        String source = ParamUtil.getString(mapRequest, AppParams.SOURCE);
        String currency = ParamUtil.getString(mapRequest, AppParams.CURRENCY);
        String note = ParamUtil.getString(mapRequest, AppParams.NOTE);

        String store_id = ParamUtil.getString(mapRequest, AppParams.STORE_ID);
        String reference_id = ParamUtil.getString(mapRequest, AppParams.REFERENCE_ID);
        String state = ParamUtil.getString(mapRequest, AppParams.STATE);
        String shipping_method = ParamUtil.getString(mapRequest, AppParams.SHIPPING_METHOD);

        Map shippingMap = ParamUtil.getMapData(mapRequest, AppParams.SHIPPING);

        String extra_fee = ParamUtil.getString(mapRequest, AppParams.EXTRA_FEE_2);

        List<Map> itemsList = ParamUtil.getListData(mapRequest, AppParams.ITEMS);

        String tax_amount = ParamUtil.getString(mapRequest, AppParams.TAX_AMOUNT);
        String ioss_number = ParamUtil.getString(mapRequest, AppParams.IOSS_NUMBER);

        int addr_verified ;
        String addr_verified_note ;

        //shipping
        String shipping_id = String.valueOf(rand.nextInt(1000000000));

        String email = ParamUtil.getString(shippingMap, AppParams.EMAIL);
        String name_shipping = ParamUtil.getString(shippingMap, AppParams.NAME);
        String phone = ParamUtil.getString(shippingMap, AppParams.PHONE);

        Map addressMap = ParamUtil.getMapData(shippingMap, AppParams.ADDRESS);
        String line1 = ParamUtil.getString(addressMap, AppParams.LINE1);
        String line2 = ParamUtil.getString(addressMap, AppParams.LINE2);
        String city = ParamUtil.getString(addressMap, AppParams.CITY);
        String stateShipping = ParamUtil.getString(addressMap, AppParams.STATE);
        String postal_code = ParamUtil.getString(addressMap, AppParams.POSTAL_CODE);
        String country = ParamUtil.getString(addressMap, AppParams.COUNTRY);
        String country_name = ParamUtil.getString(addressMap, AppParams.COUNTRY_NAME);
         addr_verified = ParamUtil.getBoolean(addressMap, AppParams.ADDR_VERIFIED)?1:0;
         addr_verified_note = ParamUtil.getString(addressMap, AppParams.ADDR_VERIFIED_NOTE);

        // product
        String id = "", base_id = "", color = "", color_id = "", color_name = "", size_id = "", size_name = "", quantity = "", price = "",
                design_front_url = "", design_front_url_md5 = "", design_back_url = "", design_back_url_md5 = "",
                variant_name = "", unit_amount = "";
        for(Map mapProduct : itemsList) {
             id = ParamUtil.getString(mapProduct, AppParams.ID);
             base_id = ParamUtil.getString(mapProduct, AppParams.BASE_ID);
             color = ParamUtil.getString(mapProduct, AppParams.COLOR);
             color_id = ParamUtil.getString(mapProduct, AppParams.COLOR_ID);
             color_name = ParamUtil.getString(mapProduct, AppParams.COLOR_NAME);
             size_id = ParamUtil.getString(mapProduct, AppParams.SIZE_ID);
             size_name = ParamUtil.getString(mapProduct, AppParams.SIZE_NAME);
             quantity = ParamUtil.getString(mapProduct, AppParams.QUANTITY);
             price = ParamUtil.getString(mapProduct, AppParams.PRICE);

            Map designsMap = ParamUtil.getMapData(mapProduct, AppParams.DESIGNS);
             design_front_url = ParamUtil.getString(designsMap, AppParams.DESIGN_FRONT_URL);
             design_front_url_md5 = ParamUtil.getString(designsMap, AppParams.DESIGN_FRONT_URL_MD5);
             design_back_url = ParamUtil.getString(designsMap, AppParams.DESIGN_BACK_URL);
             design_back_url_md5 = ParamUtil.getString(designsMap, AppParams.DESIGN_BACK_URL_MD5);

             variant_name = ParamUtil.getString(mapProduct, AppParams.VARIANT_NAME);
             unit_amount = ParamUtil.getString(mapProduct, AppParams.UNIT_AMOUNT);
        }

        List<Map> orderResultList = insertOrder(order_id, source, currency, note,
                store_id, reference_id, state, shipping_method,
                shipping_id, extra_fee, tax_amount, ioss_number,
                addr_verified, addr_verified_note);
        Map orderResultMap = orderResultList.get(0);

        List<Map> shippingResultList = insertShipping(shipping_id,
                email, name_shipping, phone,
                line1, line2, city, stateShipping, postal_code, country, country_name);
        Map shippingResultMap = shippingResultList.get(0);

        List<Map> productResultList = insertProduct(
                id, base_id, color, color_id, color_name, size_id, size_name, quantity, price,
                design_front_url, design_front_url_md5, design_back_url, design_back_url_md5,
                variant_name, unit_amount
        );

        Map result = OrderService.formatInsertOrder(orderResultMap, shippingResultMap, productResultList);

        return result;
        }

    public static List<Map> insertProduct(
            String id, String base_id, String color, String color_id, String color_name, String size_id, String size_name, String quantity, String price,
            String design_front_url, String design_front_url_md5, String design_back_url, String design_back_url_md5,
            String variant_name, String unit_amount
    ) throws SQLException{
        List<Map> resultMap = OrderService.indertProduct(
                id, base_id, color, color_id, color_name, size_id, size_name, quantity, price,
                design_front_url, design_front_url_md5, design_back_url, design_back_url_md5,
                variant_name, unit_amount);

        return resultMap;
    }

    public static List<Map> insertShipping(String shipping_id,
                                           String email, String name_shipping, String phone,
                                           String line1, String line2, String city, String state, String postal_code, String country, String country_name) throws SQLException{
        List<Map> resultMap = OrderService.indertShipping(shipping_id,
                email, name_shipping, phone,
                line1, line2, city, state, postal_code, country, country_name);

        return resultMap;
    }

    public static List<Map> insertOrder(
            String order_id, String source, String currency, String note,
            String store_id, String reference_id, String state, String shipping_method,
            String shipping, String extra_fee, String tax_amount, String ioss_number,
            int addr_verified, String addr_verified_note) throws SQLException{
        List<Map> result = OrderService.insertOrder(
                order_id, source, currency, note,
                store_id, reference_id, state, shipping_method,
                shipping, extra_fee, tax_amount, ioss_number,
                addr_verified, addr_verified_note
        );
        return result;
    }
}
