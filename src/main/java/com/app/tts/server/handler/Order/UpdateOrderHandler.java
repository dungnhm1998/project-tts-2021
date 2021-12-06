package com.app.tts.server.handler.Order;

import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateOrderHandler implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                //order

                List<Map> productResultList = new ArrayList<>();
                String orderId = ParamUtil.getString(jsonRequest, "id");
                String source = ParamUtil.getString(jsonRequest, AppParams.SOURCE);
                String currency = ParamUtil.getString(jsonRequest, AppParams.CURRENCY);
                String note = ParamUtil.getString(jsonRequest, AppParams.NOTE);
                String channel = ParamUtil.getString(jsonRequest, "channel");
                String userId = ParamUtil.getString(jsonRequest, "user_id");
                String shippingFree = ParamUtil.getString(jsonRequest, "shipping_fee");
                String storeId = ParamUtil.getString(jsonRequest, AppParams.STORE_ID);
                String referenceId = ParamUtil.getString(jsonRequest, AppParams.REFERENCE_ID);
                String state = ParamUtil.getString(jsonRequest, AppParams.STATE);
                int quantityOrder = ParamUtil.getInt(jsonRequest, AppParams.QUANTITY);
                String dUpdate = ParamUtil.getString(jsonRequest, "update_date");
                String tracking_code = ParamUtil.getString(jsonRequest, AppParams.TRACKING_CODE);
                String dOrder = ParamUtil.getString(jsonRequest, "order_date");
                String taxAmount = ParamUtil.getString(jsonRequest, AppParams.TAX_AMOUNT);
                String iossNumber = ParamUtil.getString(jsonRequest, AppParams.IOSS_NUMBER);
                String shippingMethod = ParamUtil.getString(jsonRequest, AppParams.SHIPPING_METHOD);

                Map shippingMap = ParamUtil.getMapData(jsonRequest, AppParams.SHIPPING);

                String extraFee = ParamUtil.getString(jsonRequest, AppParams.EXTRA_FEE_2);

                List<Map> itemsList = ParamUtil.getListData(jsonRequest, AppParams.ITEMS);


                int addrVerified = 0;
                String addrVerifiedNote;

                //shipping
//            String shippingId = String.valueOf(rand.nextInt(1000000000));

                String shippingId = ParamUtil.getString(shippingMap, "id");
                String originalId = ParamUtil.getString(jsonRequest, "original_id");
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

                for (Map mapProduct : itemsList) {
                    String orProductId = ParamUtil.getString(mapProduct, AppParams.ID);
                    String baseId = ParamUtil.getString(mapProduct, AppParams.BASE_ID);
                    String clValue = ParamUtil.getString(mapProduct, AppParams.COLOR);
                    String colorId = ParamUtil.getString(mapProduct, AppParams.COLOR_ID);
                    String colorName = ParamUtil.getString(mapProduct, AppParams.COLOR_NAME);
                    String sizeId = ParamUtil.getString(mapProduct, "size_id");
                    String sizeName = ParamUtil.getString(mapProduct, AppParams.SIZE_NAME);
                    String customData = ParamUtil.getString(mapProduct, "custom_data");
                    int quantityProduct = ParamUtil.getInt(mapProduct, AppParams.QUANTITY);
                    String campaignId = ParamUtil.getString(mapProduct, "campaign_id");

                    Map designsMap = ParamUtil.getMapData(mapProduct, AppParams.DESIGNS);
                    String s_design_front_url = ParamUtil.getString(designsMap, "design_front_url");
                    String s_variant_back_url = ParamUtil.getString(designsMap, "mockup_back_url");
                    String s_variant_front_url = ParamUtil.getString(designsMap, "mockup_front_url");
                    String s_design_back_url = ParamUtil.getString(designsMap, "design_back_url");
                    String productId = ParamUtil.getString(designsMap, "product_id");

                    String variantName = ParamUtil.getString(mapProduct, "variant_id");
                    String unitAmount = ParamUtil.getString(mapProduct, AppParams.UNIT_AMOUNT);

                    productResultList = OrderService.updateProduct(
                            orProductId, orderId, campaignId, productId, variantName,
                            sizeId, quantityProduct, baseId, s_variant_front_url, s_variant_back_url,
                            colorId, clValue, colorName, sizeName, unitAmount,
                            s_design_back_url, s_design_front_url, customData);

                }


                Map orderResultList = OrderService.updateOrder(orderId, currency, state, shippingId, tracking_code,
                        note, channel, shippingFree, source, originalId,
                        storeId, userId, referenceId, quantityOrder, addrVerified,
                        addrVerifiedNote, extraFee, shippingMethod, taxAmount, iossNumber
                );

                Map shippingResultList = OrderService.updateShipping(shippingId, email, nameShipping, phone, line1,
                        line2, city, stateShipping, postalCode, country, countryName);

                Map data = OrderService.formatUpdateOrder(orderResultList, shippingResultList, productResultList);


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

