package com.app.tts.server.handler.Order;

import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UpdateOrderHandler implements Handler<RoutingContext> {


        @Override
        public void handle(RoutingContext routingContext) {
            routingContext.vertx().executeBlocking(future -> {
                try {
                    Map jsonRequest = routingContext.getBodyAsJson().getMap();
                    Map result = inputData(jsonRequest);

                    routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                    routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                    routingContext.put(AppParams.RESPONSE_DATA, result);
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

        public static Map inputData(Map mapRequest) throws SQLException {
            //order
//            Random rand = new Random();
//            String orderId = String.valueOf(rand.nextInt(1000000000));
            String orderId = ParamUtil.getString(mapRequest, "id");
            String source = ParamUtil.getString(mapRequest, AppParams.SOURCE);
            String currency = ParamUtil.getString(mapRequest, AppParams.CURRENCY);
            String note = ParamUtil.getString(mapRequest, AppParams.NOTE);

            String storeId = ParamUtil.getString(mapRequest, AppParams.STORE_ID);
            String referenceId = ParamUtil.getString(mapRequest, AppParams.REFERENCE_ID);
            String state = ParamUtil.getString(mapRequest, AppParams.STATE);
            String shippingMethod = ParamUtil.getString(mapRequest, AppParams.SHIPPING_METHOD);

            Map shippingMap = ParamUtil.getMapData(mapRequest, AppParams.SHIPPING);

            String extraFee = ParamUtil.getString(mapRequest, AppParams.EXTRA_FEE_2);

            List<Map> itemsList = ParamUtil.getListData(mapRequest, AppParams.ITEMS);

            String taxAmount = ParamUtil.getString(mapRequest, AppParams.TAX_AMOUNT);
            String iossNumber = ParamUtil.getString(mapRequest, AppParams.IOSS_NUMBER);

            int addrVerified;
            String addrVerifiedNote;

            //shipping
//            String shippingId = String.valueOf(rand.nextInt(1000000000));

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
            String id = "", baseId = "", color = "", colorId = "", colorName = "", sizeId = "", sizeName = "", quantity = "", price = "",
                    designFrontUrl = "", designFrontUrlMd5 = "", designBackUrl = "", designBackUrlMd5 = "",
                    variantName = "", unitAmount = "";
            for (Map mapProduct : itemsList) {
                id = ParamUtil.getString(mapProduct, AppParams.ID);
                baseId = ParamUtil.getString(mapProduct, AppParams.BASE_ID);
                color = ParamUtil.getString(mapProduct, AppParams.COLOR);
                colorId = ParamUtil.getString(mapProduct, AppParams.COLOR_ID);
                colorName = ParamUtil.getString(mapProduct, AppParams.COLOR_NAME);
                sizeId = ParamUtil.getString(mapProduct, AppParams.SIZE_ID);
                sizeName = ParamUtil.getString(mapProduct, AppParams.SIZE_NAME);
                quantity = ParamUtil.getString(mapProduct, AppParams.QUANTITY);
                price = ParamUtil.getString(mapProduct, AppParams.PRICE);

                Map designsMap = ParamUtil.getMapData(mapProduct, AppParams.DESIGNS);
                designFrontUrl = ParamUtil.getString(designsMap, AppParams.DESIGN_FRONT_URL);
                designFrontUrlMd5 = ParamUtil.getString(designsMap, AppParams.DESIGN_FRONT_URL_MD5);
                designBackUrl = ParamUtil.getString(designsMap, AppParams.DESIGN_BACK_URL);
                designBackUrlMd5 = ParamUtil.getString(designsMap, AppParams.DESIGN_BACK_URL_MD5);

                variantName = ParamUtil.getString(mapProduct, AppParams.VARIANT_NAME);
                unitAmount = ParamUtil.getString(mapProduct, AppParams.UNIT_AMOUNT);
            }

            Map orderResultList = updateOrder(orderId, source, currency, note,
                    storeId, referenceId, state, shippingMethod,
                    shippingId, extraFee, taxAmount, iossNumber,
                    addrVerified, addrVerifiedNote);


            Map shippingResultList = updateShipping(shippingId,
                    email, nameShipping, phone,
                    line1, line2, city, stateShipping, postalCode, country, countryName);


            List<Map> productResultList = updateProduct(
                    id, baseId, color, colorId, colorName, sizeId, sizeName, quantity, price,
                    designFrontUrl, designFrontUrlMd5, designBackUrl, designBackUrlMd5,
                    variantName, unitAmount
            );

//            Map result = OrderService.formatUpdateOrder(orderResultList, shippingResultList, productResultList);
//
//            return result;
            Map data = new LinkedHashMap();

            data.put("orderResultList", orderResultList);
            data.put("shippingResultList", shippingResultList);
            data.put("productResultList", productResultList);

            return data;

        }

        public static List<Map> updateProduct(
                String id, String baseId, String color, String colorId, String colorName, String sizeId, String size_name, String quantity, String price,
                String designFrontUrl, String designFrontUrlMd5, String designBackUrl, String designBackUrlMd5,
                String variantName, String unitAmount
          ) throws SQLException {
            List<Map> resultMap = OrderService.updateProduct(
                    id, baseId, color, colorId, colorName, sizeId, size_name, quantity, price,
                    designFrontUrl, designFrontUrlMd5, designBackUrl, designBackUrlMd5,
                    variantName, unitAmount);

            return resultMap;
        }

        public static Map updateShipping(String shippingId,
                String email, String nameShipping, String phone,
                String line1, String line2, String city, String state, String postalCode, String country, String countryName) throws SQLException {
            Map resultMap = OrderService.updateShipping(shippingId,
                    email, nameShipping, phone,
                    line1, line2, city, state, postalCode, country, countryName);

            return resultMap;
        }

        public static Map updateOrder(
                String orderId, String source, String currency, String note,
                String storeId, String referenceId, String state, String shippingMethod,
                String shipping, String extraFee, String taxAmount, String iossNumber,
        int addrVerified, String addrVerifiedNote) throws SQLException {
            Map result = OrderService.updateOrder(
                    orderId, source, currency, note,
                    storeId, referenceId, state, shippingMethod,
                    shipping, extraFee, taxAmount, iossNumber,
                    addrVerified, addrVerifiedNote
            );
            return result;
        }
    }

