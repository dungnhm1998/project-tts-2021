package com.app.tts.server.handler.Order;

import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UpdateOrderShippingProductHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                JsonObject jsonRequest = routingContext.getBodyAsJson();
                Map mapRequest = jsonRequest.getMap();
                Map result = inputData(mapRequest);

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.BAD_REQUEST.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.BAD_REQUEST.reasonPhrase());
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

    public static Map inputData(Map mapRequest) throws SQLException, ParseException {
        Random rand = new Random();
        //order
        String orderId = ParamUtil.getString(mapRequest, AppParams.ID);
        String currency = ParamUtil.getString(mapRequest, AppParams.CURRENCY);
        String subAmount = ParamUtil.getString(mapRequest, AppParams.SUB_AMOUNT);
        String shippingFee = ParamUtil.getString(mapRequest, AppParams.SHIPPING_FEE);
        String taxAmount = ParamUtil.getString(mapRequest, AppParams.TAX_AMOUNT);
        String state = ParamUtil.getString(mapRequest, AppParams.STATE);
        String createDate = ParamUtil.getString(mapRequest, AppParams.CREATE_DATE);

        DateFormat df1 = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        Date dateCreate = new Date();
        if(!createDate.isEmpty()) {
            dateCreate = df1.parse(createDate);
        }
        String updateDate = ParamUtil.getString(mapRequest, AppParams.UPDATE_DATE);
        Date dateUpdate = new Date();
        if(!updateDate.isEmpty()) {
            dateUpdate = df1.parse(updateDate);
        }
        String trackingCode = ParamUtil.getString(mapRequest, AppParams.TRACKING_CODE);
        String orderDate = ParamUtil.getString(mapRequest, AppParams.ORDER_DATE);
        Date dateOrder = new Date();
        if(!orderDate.isEmpty()){
            dateOrder = df1.parse(orderDate);
        }
        String note = ParamUtil.getString(mapRequest, AppParams.NOTE);
        String channel = ParamUtil.getString(mapRequest, AppParams.CHANNEL);
        String userId = ParamUtil.getString(mapRequest, AppParams.USER_ID);
        String storeId = ParamUtil.getString(mapRequest, AppParams.STORE_ID);
        String shippingId = ParamUtil.getString(mapRequest, AppParams.SHIPPING_ID);
        String originalId = ParamUtil.getString(mapRequest, AppParams.ORIGINAL_ID);
        String source = ParamUtil.getString(mapRequest, AppParams.SOURCE);
        String shippingMethod = ParamUtil.getString(mapRequest, AppParams.SHIPPING_METHOD);
        int fulfillState = ParamUtil.getString(mapRequest, "fulfill_state").equals("Unfulfilled") ? 0 : 1;
        String iossNumber = ParamUtil.getString(mapRequest, AppParams.IOSS_NUMBER);

        Map shippingMap = ParamUtil.getMapData(mapRequest, AppParams.SHIPPING);
        String nameShipping = ParamUtil.getString(shippingMap, AppParams.NAME);
        String email = ParamUtil.getString(shippingMap, AppParams.EMAIL);
        String phone = ParamUtil.getString(shippingMap, AppParams.PHONE);
        int gift = ParamUtil.getBoolean(shippingMap, "gift") ? 1 : 0;

        Map addressMap = ParamUtil.getMapData(shippingMap, AppParams.ADDRESS);
        String line1 = ParamUtil.getString(addressMap, AppParams.LINE1);
        String line2 = ParamUtil.getString(addressMap, AppParams.LINE2);
        String city = ParamUtil.getString(addressMap, AppParams.CITY);
        String stateAddress = ParamUtil.getString(addressMap, AppParams.STATE);
        String postalCode = ParamUtil.getString(addressMap, AppParams.POSTAL_CODE);
        String country = ParamUtil.getString(addressMap, AppParams.COUNTRY);
        String countryName = ParamUtil.getString(addressMap, AppParams.COUNTRY_NAME);
        int addrVerified = ParamUtil.getBoolean(addressMap, AppParams.ADDR_VERIFIED) ? 1 : 0;
        String addrVerifiedNote = ParamUtil.getString(addressMap, AppParams.ADDR_VERIFIED_NOTE);

        String referenceId = ParamUtil.getString(mapRequest, AppParams.REFERENCE_ID);
        int requireRefund = ParamUtil.getInt(mapRequest, AppParams.REQUIRE_REFUND);
        String extraFee = ParamUtil.getString(mapRequest, "extra_fee");
        String amount = ParamUtil.getString(mapRequest, AppParams.AMOUNT);

        List<Map> listItems = ParamUtil.getListData(mapRequest, AppParams.ITEMS);
        List<Map> productListInput = new LinkedList<>();
        List<String> listIdProduct = new LinkedList<>();
        List<String> listIdProductDB = getProductByOrderId(orderId);
        for (Map productMap : listItems) {
            String id = ParamUtil.getString(productMap, AppParams.ID);
            String baseId = ParamUtil.getString(productMap, AppParams.BASE_ID);
            String color = ParamUtil.getString(productMap, AppParams.COLOR);
            String colorId = ParamUtil.getString(productMap, AppParams.COLOR_ID);
            String colorName = ParamUtil.getString(productMap, AppParams.COLOR_NAME);
            String sizeId = ParamUtil.getString(productMap, AppParams.SIZE_ID);
            String sizeName = ParamUtil.getString(productMap, AppParams.SIZE_NAME);
            String customData = ParamUtil.getString(productMap, AppParams.CUSTOM_DATA);
            int quantity = ParamUtil.getInt(productMap, AppParams.QUANTITY);
            String campaignId = ParamUtil.getString(productMap, AppParams.CAMPAIGN_ID);
            String unitAmount = ParamUtil.getString(productMap, AppParams.UNIT_AMOUNT);

            Map designsMap = ParamUtil.getMapData(productMap, AppParams.DESIGNS);
            String designFrontUrl = ParamUtil.getString(designsMap, AppParams.DESIGN_FRONT_URL);
            String designBackUrl = ParamUtil.getString(designsMap, AppParams.DESIGN_BACK_URL);

            String variantId = ParamUtil.getString(productMap, AppParams.VARIANT_ID);
            String productId = ParamUtil.getString(productMap, AppParams.PRODUCT_ID);

            Map productMapInput = new LinkedHashMap();
            if (!id.isEmpty()) {
                productMapInput = updateProduct(orderId,
                        id, baseId,
                        color, colorId, colorName, sizeId, sizeName,
                        customData, quantity,
                        campaignId, unitAmount,
                        designFrontUrl, designBackUrl,
                        variantId, productId);

                listIdProduct.add(id);
            } else {
                id = String.valueOf(rand.nextInt(1000000000));
                productMapInput = OrderService.insertProduct(orderId,
                        id, baseId, color, colorId, colorName, sizeId, sizeName, quantity, "",
                        designFrontUrl, "", designBackUrl, "",
                        "", unitAmount);
            }

            productListInput.add(productMapInput);
        }

        //xoa product
        for (String idProduct : listIdProductDB) {
            if (!listIdProduct.contains(idProduct)) {
                deleteProductInOrder(orderId, idProduct);
            }
        }

        Map shippingInput = updateShipping(shippingId,
                nameShipping, email, phone, gift,
                line1, line2, city, stateAddress, postalCode, country, countryName);

        Map orderInput = updateOrder(orderId,
                currency, subAmount, shippingFee, taxAmount, state,
                dateCreate, dateUpdate, trackingCode, dateOrder, note,
                channel, userId, storeId,
                shippingId, originalId, source, shippingMethod, fulfillState,
                iossNumber,
                referenceId, requireRefund, extraFee, amount,
                addrVerified, addrVerifiedNote);

        Map result = OrderService.formatUpdateOrder(orderInput, shippingInput, productListInput);

        return result;
    }

    public static Map deleteProductInOrder(String orderId, String productId) throws SQLException {
        Map result = OrderService.deleteProductInOrder(orderId, productId);
        return result;
    }

    public static List<String> getProductByOrderId(String orderId) throws SQLException {
        List<String> result = OrderService.getProductByOrderId(orderId);
        return result;
    }

    public static Map updateProduct(String orderId,
                                    String id, String baseId,
                                    String color, String colorId, String colorName, String sizeId, String sizeName,
                                    String customData, int quantity,
                                    String campaignId, String unitAmount,
                                    String designFrontUrl, String designBackUrl,
                                    String variantId, String productId) throws SQLException {
        Map resultProduct = OrderService.updateProduct(orderId,
                id, baseId,
                color, colorId, colorName, sizeId, sizeName,
                customData, quantity,
                campaignId, unitAmount,
                designFrontUrl, designBackUrl,
                variantId, productId);
        return resultProduct;
    }

    public static Map updateShipping(String shippingId,
                                     String nameShipping, String email, String phone, int gift,
                                     String line1, String line2, String city, String state, String postalCode, String country, String countryName)
            throws SQLException {
        Map resultShipping = OrderService.updateShipping(shippingId,
                nameShipping, email, phone, gift,
                line1, line2, city, state, postalCode, country, countryName);
        return resultShipping;
    }

    public static Map updateOrder(String orderId,
                                  String currency, String subAmount, String shippingFee, String taxAmount, String state,
                                  Date createDate, Date updateDate, String trackingCode, Date orderDate, String note,
                                  String channel, String userId, String storeId,
                                  String shippingId, String originalId, String source, String shippingMethod, int fulfillState,
                                  String iossNumber,
                                  String referenceId, int requireRefund, String extraFee, String amount,
                                  int addrVerified, String addrVerifiedNote) throws SQLException {
        Map resultOrder = OrderService.updateOrder(orderId,
                currency, subAmount, shippingFee, taxAmount, state,
                createDate, updateDate, trackingCode, orderDate, note,
                channel, userId, storeId,
                shippingId, originalId, source, shippingMethod, fulfillState,
                iossNumber,
                referenceId, requireRefund, extraFee, amount,
                addrVerified, addrVerifiedNote);
        return resultOrder;
    }
}
