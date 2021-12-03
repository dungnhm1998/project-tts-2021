package com.app.tts.server.handler.Order;

import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UpdateOrderHandler implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                Map data = new LinkedHashMap();

                List<Map> productResultList = new ArrayList<>();
                String orderId = ParamUtil.getString(jsonRequest, "id");
                String source = ParamUtil.getString(jsonRequest, AppParams.SOURCE);
                String currency = ParamUtil.getString(jsonRequest, AppParams.CURRENCY);
                String note = ParamUtil.getString(jsonRequest, AppParams.NOTE);

                String storeId = ParamUtil.getString(jsonRequest, AppParams.STORE_ID);
                String referenceId = ParamUtil.getString(jsonRequest, AppParams.REFERENCE_ID);
                String state = ParamUtil.getString(jsonRequest, AppParams.STATE);

                String taxAmount = ParamUtil.getString(jsonRequest, AppParams.TAX_AMOUNT);
                String iossNumber = ParamUtil.getString(jsonRequest, AppParams.IOSS_NUMBER);
                String shippingMethod = ParamUtil.getString(jsonRequest, AppParams.SHIPPING_METHOD);

                Map shippingMap = ParamUtil.getMapData(jsonRequest, AppParams.SHIPPING);

                String extraFee = ParamUtil.getString(jsonRequest, AppParams.EXTRA_FEE_2);

                List<Map> itemsList = ParamUtil.getListData(jsonRequest, AppParams.ITEMS);


                int addrVerified;
                String addrVerifiedNote;

                String shippingId = ParamUtil.getString(shippingMap, "id");
                String email = ParamUtil.getString(shippingMap, AppParams.EMAIL);
                String nameShipping = ParamUtil.getString(shippingMap, AppParams.NAME);
                String phone = ParamUtil.getString(shippingMap, AppParams.PHONE);

                Map addressMap = ParamUtil.getMapData(shippingMap, AppParams.ADDRESS);
                String line1 = ParamUtil.getString(addressMap, AppParams.LINE1);
                String line2 = ParamUtil.getString(addressMap, AppParams.LINE2);
                String city = ParamUtil.getString(addressMap, AppParams.CITY);
                String stateShipping = ParamUtil.getString(addressMap, AppParams.STATE);
                String postalCode = ParamUtil.getString(addressMap, AppParams.POSTAL_CODE);
                String country = ParamUtil.getString(addressMap, AppParams.COUNTRY);
                String countryName = ParamUtil.getString(addressMap, AppParams.COUNTRY_NAME);
                addrVerified = ParamUtil.getBoolean(addressMap, AppParams.ADDR_VERIFIED) ? 1 : 0;
                addrVerifiedNote = ParamUtil.getString(addressMap, AppParams.ADDR_VERIFIED_NOTE);

                // product
                String orProductId = "", baseId = "", clValue = "", colorId = "", colorName = "", sizeId = "", sizeName = "", quantity = "",
                        s_design_front_url = "", s_design_back_url = "", s_variant_front_url = "",
                        s_variant_back_url = "", variantName = "", unitAmount = "";
                for (Map mapProduct : itemsList) {
                    orProductId = ParamUtil.getString(mapProduct, AppParams.ID);
                    baseId = ParamUtil.getString(mapProduct, AppParams.BASE_ID);
                    clValue = ParamUtil.getString(mapProduct, AppParams.COLOR);
                    colorId = ParamUtil.getString(mapProduct, AppParams.COLOR_ID);
                    colorName = ParamUtil.getString(mapProduct, AppParams.COLOR_NAME);
                    sizeId = ParamUtil.getString(mapProduct, "size_id");
                    sizeName = ParamUtil.getString(mapProduct, AppParams.SIZE_NAME);
                    quantity = ParamUtil.getString(mapProduct, AppParams.QUANTITY);


                    Map designsMap = ParamUtil.getMapData(mapProduct, AppParams.DESIGNS);
                    s_design_front_url = ParamUtil.getString(designsMap, "design_front_url");
                    s_variant_back_url = ParamUtil.getString(designsMap, "mockup_back_url");
                    s_variant_front_url = ParamUtil.getString(designsMap, "mockup_front_url");
                    s_design_back_url = ParamUtil.getString(designsMap, "design_back_url");


                    variantName = ParamUtil.getString(mapProduct, "variant_id");
                    unitAmount = ParamUtil.getString(mapProduct, AppParams.UNIT_AMOUNT);

                    productResultList = OrderService.updateProduct(orProductId, orderId, baseId, clValue, colorId, colorName, sizeId, sizeName, quantity,
                            s_design_front_url, s_variant_front_url,s_design_back_url, s_variant_back_url, variantName, unitAmount);

                }


                Map orderResultList = OrderService.updateOrder(orderId, source, currency, note, storeId, referenceId, state, shippingMethod, shippingId, extraFee, taxAmount, iossNumber, addrVerified, addrVerifiedNote);

                Map shippingResultList = OrderService.updateShipping(shippingId, email, nameShipping, phone, line1, line2, city, stateShipping, postalCode, country, countryName);

                data = OrderService.formatUpdateOrder1(orderResultList, shippingResultList, productResultList);


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

}

