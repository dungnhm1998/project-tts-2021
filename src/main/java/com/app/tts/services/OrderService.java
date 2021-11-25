package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.*;

public class OrderService extends MasterService {
    private static final String GET_ORDER_BY_ID = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER_BY_ID(?,?,?,?)}";
    private static final String GET_ORDER_PRODUCT = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER_PRODUCT(?,?,?)}";
    private static final String GET_ORDER = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER(?,?,?)}";

    private static final String INSERT_DROPSHIP_ORDER = "{call PKG_DROPSHIP_ORDER_PHUONG.INSERT_DROPSHIP_ORDER" +
            "(?, ?,?,?,?,?,?,?, ?,?, ?,?, ?,?, ?,?,?)}";
    private static final String INSERT_SHIPPING = "{call PKG_DROPSHIP_ORDER_PHUONG.INSERT_SHIPPING" +
            "(?, ?,?,?, ?,?,?,?,?,?,?, ?,?,?)}";
    private static final String INSERT_DROPSHIP_ORDER_PRODUCT = "{call PKG_DROPSHIP_ORDER_PHUONG.INSERT_DROPSHIP_ORDER_PRODUCT" +
            "(?,?,?,?,?,?,?,?,?, ?,?,?,?, ?,?, ?,?,?)}";

    public static List<Map> indertProduct(
            String id, String baseId, String color, String colorId, String colorName, String sizeId, String sizeName, String quantity, String price,
            String designFrontUrl, String designFrontUrlMd5, String designBackUrl, String designBackUrlMd5,
            String variantName, String unitAmount
    ) throws SQLException {
        List<Map> resultMap = excuteQuery(INSERT_DROPSHIP_ORDER_PRODUCT, new Object[]{
                id, baseId, color, colorId, colorName, sizeId, sizeName, quantity, price,
                designFrontUrl, designFrontUrlMd5, designBackUrl, designBackUrlMd5,
                variantName, unitAmount
        });
        return resultMap;
    }

    public static List<Map> indertShipping(String shippingId,
                                           String email, String nameShipping, String phone,
                                           String line1, String line2, String city, String state, String postalCode, String country, String countryName) throws SQLException {
        List<Map> resultMap = excuteQuery(INSERT_SHIPPING, new Object[]{shippingId,
                email, nameShipping, phone,
                line1, line2, city, state, postalCode, country, countryName});
        return resultMap;
    }


    public static List<Map> insertOrder(String orderId, String source, String currency, String note,
                                        String storeId, String referenceId, String state, String shippingMethod,
                                        String shipping, String extraFee, String taxAmount, String iossNumber,
                                        int addrVerified, String addrVerifiedNote) throws SQLException {
        List<Map> resultMap = excuteQuery(INSERT_DROPSHIP_ORDER, new Object[]{
                orderId, source, currency, note,
                storeId, referenceId, state, shippingMethod,
                shipping, extraFee, taxAmount, iossNumber,
                addrVerified, addrVerifiedNote});
        return resultMap;
    }

    public static Map formatInsertOrder(Map orderInput, Map shippingInput, List<Map> productList) {
        Map orderMap = new LinkedHashMap();
        orderMap.put(AppParams.SOURCE, ParamUtil.getString(orderInput, AppParams.S_SOURCE));
        orderMap.put(AppParams.CURRENCY, ParamUtil.getString(orderInput, AppParams.S_CURRENCY));
        orderMap.put(AppParams.NOTE, ParamUtil.getString(orderInput, AppParams.S_NOTE));

        orderMap.put(AppParams.STORE_ID, ParamUtil.getString(orderInput, AppParams.S_STORE_ID));
        orderMap.put(AppParams.REFERENCE_ID, ParamUtil.getString(orderInput, AppParams.S_REFERENCE_ORDER));
        orderMap.put(AppParams.STATE, ParamUtil.getString(orderInput, AppParams.S_STATE));
        orderMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_METHOD));

        Map shippingMap = new LinkedHashMap();
        shippingMap.put(AppParams.EMAIL, ParamUtil.getString(shippingInput, AppParams.S_EMAIL_2));
        shippingMap.put(AppParams.NAME, ParamUtil.getString(shippingInput, AppParams.S_NAME));
        shippingMap.put(AppParams.PHONE, ParamUtil.getString(shippingInput, AppParams.S_PHONE));

        Map addressMap = new LinkedHashMap();
        addressMap.put(AppParams.LINE1, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE1));
        addressMap.put(AppParams.LINE2, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE2));
        addressMap.put(AppParams.CITY, ParamUtil.getString(shippingInput, AppParams.S_ADD_CITY));
        addressMap.put(AppParams.STATE, ParamUtil.getString(shippingInput, AppParams.S_STATE_2));
        addressMap.put(AppParams.POSTAL_CODE, ParamUtil.getString(shippingInput, AppParams.S_POSTAL_CODE));
        addressMap.put(AppParams.COUNTRY, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_CODE));
        addressMap.put(AppParams.COUNTRY_NAME, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_NAME));
        Boolean addrVerified;
        if (ParamUtil.getInt(orderInput, AppParams.N_ADDR_VERIFIED) == 1) {
            addrVerified = true;
        } else {
            addrVerified = false;
        }
        addressMap.put(AppParams.ADDR_VERIFIED, addrVerified);
        addressMap.put(AppParams.ADDR_VERIFIED_NOTE, ParamUtil.getString(orderInput, AppParams.S_ADDR_VERIFIED_NOTE));
        shippingMap.put(AppParams.ADDRESS, addressMap);

        orderMap.put(AppParams.SHIPPING, shippingMap);

        orderMap.put(AppParams.EXTRA_FEE_2, ParamUtil.getString(orderInput, AppParams.S_EXTRA_FEE));

        List<Map> itemsList = new LinkedList<>();
        for (Map productInput : productList) {
            Map productMap = new LinkedHashMap();
            productMap.put(AppParams.ID, ParamUtil.getString(productInput, AppParams.S_ID));
            productMap.put(AppParams.BASE_ID, ParamUtil.getString(productInput, AppParams.S_BASE_ID));
            productMap.put(AppParams.COLOR, ParamUtil.getString(productInput, AppParams.S_COLOR_VALUE));
            productMap.put(AppParams.COLOR_ID, ParamUtil.getString(productInput, AppParams.S_COLOR_ID));
            productMap.put(AppParams.COLOR_NAME, ParamUtil.getString(productInput, AppParams.S_COLOR_NAME));
            productMap.put(AppParams.SIZE_ID, ParamUtil.getString(productInput, AppParams.S_SIZE_ID));
            productMap.put(AppParams.SIZE_NAME, ParamUtil.getString(productInput, AppParams.S_SIZE_NAME));
            productMap.put(AppParams.QUANTITY, ParamUtil.getString(productInput, AppParams.N_QUANTITY));
            productMap.put(AppParams.PRICE, ParamUtil.getString(productInput, AppParams.S_PRICE));

            Map designsMap = new LinkedHashMap();
            designsMap.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(productInput, AppParams.S_DESIGN_FRONT_URL));
            designsMap.put(AppParams.DESIGN_FRONT_URL_MD5, ParamUtil.getString(productInput, AppParams.S_VARIANT_FRONT_URL));
            designsMap.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(productInput, AppParams.S_DESIGN_BACK_URL));
            designsMap.put(AppParams.DESIGN_BACK_URL_MD5, ParamUtil.getString(productInput, AppParams.S_VARIANT_BACK_URL));
            productMap.put(AppParams.DESIGNS, designsMap);

            productMap.put(AppParams.VARIANT_NAME, ParamUtil.getString(productInput, AppParams.S_VARIANT_NAME));
            productMap.put(AppParams.UNIT_AMOUNT, ParamUtil.getString(productInput, AppParams.S_UNIT_AMOUNT));

            itemsList.add(productMap);
        }

        orderMap.put(AppParams.ITEMS, itemsList);

        orderMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_TAX_AMOUNT));
        orderMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));

        return orderMap;
    }


    public static List<Map> getOrderProduct() throws SQLException {
        List<Map> resultMap = excuteQuery(GET_ORDER_PRODUCT, new Object[]{});
        List<Map> result = new ArrayList<>();
        for (Map map : resultMap) {
            map = formatOrder(map);
            result.add(map);
        }
        return result;
    }

    public static List<Map> getOrder() throws SQLException {
        List<Map> resultMap = excuteQuery(GET_ORDER, new Object[]{});
        List<Map> result = new ArrayList<>();
        for (Map map : resultMap) {
            map = formatOrder(map);
            result.add(map);
        }
        return result;
    }

    public static Map getOrderById(String id) throws SQLException {
        List<Map> result = excuteQuery(GET_ORDER_BY_ID, new Object[]{id});
        Map resultMap = result.get(0);
        return resultMap;
    }


    public static Map formatOrder(Map inputMap) {
        Map resultMap = new LinkedHashMap();
        Map orderProduct = new LinkedHashMap();

        resultMap.put(AppParams.ID, ParamUtil.getString(inputMap, AppParams.S_ID_2));
        resultMap.put(AppParams.AMOUNT, ParamUtil.getString(inputMap, AppParams.S_AMOUNT));
        resultMap.put(AppParams.CURRENCY, ParamUtil.getString(inputMap, AppParams.S_CURRENCY));
        resultMap.put(AppParams.STATE, ParamUtil.getString(inputMap, AppParams.S_STATE));
        resultMap.put(AppParams.CREATE, ParamUtil.getString(inputMap, AppParams.D_CREATE));
        resultMap.put(AppParams.UPDATE, ParamUtil.getString(inputMap, AppParams.D_UPDATE));
        resultMap.put(AppParams.SHIPPING_ID, ParamUtil.getString(inputMap, AppParams.S_SHIPPING_ID));
        resultMap.put(AppParams.TRACKING_CODE, ParamUtil.getString(inputMap, AppParams.S_TRACKING_CODE));
        resultMap.put(AppParams.ORDER, ParamUtil.getString(inputMap, AppParams.D_ORDER));
        resultMap.put(AppParams.NOTE, ParamUtil.getString(inputMap, AppParams.S_NOTE));
        resultMap.put(AppParams.CHANNEL, ParamUtil.getString(inputMap, AppParams.S_CHANNEL));
        resultMap.put(AppParams.SUB_AMOUNT, ParamUtil.getString(inputMap, AppParams.S_SUB_AMOUNT));
        resultMap.put(AppParams.SHIPPING_FEE, ParamUtil.getString(inputMap, AppParams.S_SHIPPING_FEE));
        resultMap.put(AppParams.STORE_ID, ParamUtil.getString(inputMap, AppParams.S_STORE_ID));
        resultMap.put(AppParams.USER_ID, ParamUtil.getString(inputMap, AppParams.S_USER_ID));
        resultMap.put(AppParams.REFERENCE_ORDER, ParamUtil.getString(inputMap, AppParams.S_REFERENCE_ORDER));
        resultMap.put(AppParams.TOTAL_ITEM, ParamUtil.getString(inputMap, AppParams.N_TOTAL_ITEM));
        resultMap.put(AppParams.FULFILLED_ITEM, ParamUtil.getString(inputMap, AppParams.N_FULFILLED_ITEM));
        resultMap.put(AppParams.REFUNDED_ITEM, ParamUtil.getString(inputMap, AppParams.N_REFUNDED_ITEM));
        resultMap.put(AppParams.ACCOUNTING_PROCESSED, ParamUtil.getString(inputMap, AppParams.N_ACCOUNTING_PROCESSED));
        resultMap.put(AppParams.PAYMENT_FEE, ParamUtil.getString(inputMap, AppParams.S_PAYMENT_FEE));
        resultMap.put(AppParams.REQUIRE_REFUND, ParamUtil.getString(inputMap, AppParams.N_REQUIRE_REFUND));
        resultMap.put(AppParams.REFUND_NOTE, ParamUtil.getString(inputMap, AppParams.S_REFUND_NOTE));
        resultMap.put(AppParams.REQUIRE_REFUND, ParamUtil.getString(inputMap, AppParams.D_REQUIRE_REFUND));
        resultMap.put(AppParams.HAS_ADJUST, ParamUtil.getString(inputMap, AppParams.N_HAS_ADJUST));
        resultMap.put(AppParams.ADJUST_NOTE, ParamUtil.getString(inputMap, AppParams.S_ADJUST_NOTE));
        resultMap.put(AppParams.REFUNDED_AMOUNT, ParamUtil.getString(inputMap, AppParams.S_REFUNDED_AMOUNT));
        resultMap.put(AppParams.REF_FEE, ParamUtil.getString(inputMap, AppParams.S_REF_FEE));
        resultMap.put(AppParams.ADDR_VERIFIED, ParamUtil.getString(inputMap, AppParams.N_ADDR_VERIFIED));
        resultMap.put(AppParams.ADDR_VERIFIED_NOTE, ParamUtil.getString(inputMap, AppParams.S_ADDR_VERIFIED_NOTE));
        resultMap.put(AppParams.MINIFIED_JSON, ParamUtil.getString(inputMap, AppParams.S_MINIFIED_JSON));
        resultMap.put(AppParams.ACCOUNTING_PROCESSED, ParamUtil.getString(inputMap, AppParams.D_ACCOUNTING_PROCESSED));
        resultMap.put(AppParams.SELLER_COST, ParamUtil.getString(inputMap, AppParams.S_SELLER_COST));
        resultMap.put(AppParams.PRODUCE_COST, ParamUtil.getString(inputMap, AppParams.S_PRODUCE_COST));
        resultMap.put(AppParams.SHIPPING_COST, ParamUtil.getString(inputMap, AppParams.S_SHIPPING_COST));
        resultMap.put(AppParams.SOURCE, ParamUtil.getString(inputMap, AppParams.S_SOURCE));
        resultMap.put(AppParams.SUB_STATE, ParamUtil.getString(inputMap, AppParams.S_SUB_STATE));
        resultMap.put(AppParams.ORIGINAL_ID, ParamUtil.getString(inputMap, AppParams.S_ORIGINAL_ID));
        resultMap.put(AppParams.EXTRA_FEE, ParamUtil.getString(inputMap, AppParams.S_EXTRA_FEE));
        resultMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(inputMap, AppParams.S_SHIPPING_METHOD));
        resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(inputMap, AppParams.S_TAX_AMOUNT));
        resultMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(inputMap, AppParams.S_IOSS_NUMBER));
        //product
        orderProduct.put(AppParams.ID, ParamUtil.getString(inputMap, AppParams.S_ID_2));
        orderProduct.put(AppParams.ORDER_ID, ParamUtil.getString(inputMap, AppParams.S_ORDER_ID));
        orderProduct.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(inputMap, AppParams.S_CAMPAIGN_ID));
        orderProduct.put(AppParams.PRODUCT_ID, ParamUtil.getString(inputMap, AppParams.S_PRODUCT_ID));
        orderProduct.put(AppParams.VARIANT_ID, ParamUtil.getString(inputMap, AppParams.S_VARIANT_ID));
        orderProduct.put(AppParams.SIZE_ID, ParamUtil.getString(inputMap, AppParams.S_SIZE_ID));
        orderProduct.put(AppParams.CURRENCY, ParamUtil.getString(inputMap, AppParams.S_CURRENCY));
        orderProduct.put(AppParams.QUANTITY, ParamUtil.getString(inputMap, AppParams.N_QUANTITY));
        orderProduct.put(AppParams.STATE, ParamUtil.getString(inputMap, AppParams.S_STATE));
        orderProduct.put(AppParams.CREATE, ParamUtil.getString(inputMap, AppParams.D_CREATE));
        orderProduct.put(AppParams.UPDATE, ParamUtil.getString(inputMap, AppParams.D_UPDATE));
        orderProduct.put(AppParams.VARIANT_NAME, ParamUtil.getString(inputMap, AppParams.S_VARIANT_NAME));
        orderProduct.put(AppParams.AMOUNT, ParamUtil.getString(inputMap, AppParams.S_AMOUNT));
        orderProduct.put(AppParams.STATUS, ParamUtil.getString(inputMap, AppParams.N_STATUS));
        orderProduct.put(AppParams.BASE_COST, ParamUtil.getString(inputMap, AppParams.S_BASE_COST));
        orderProduct.put(AppParams.BASE_ID, ParamUtil.getString(inputMap, AppParams.S_BASE_ID));
        orderProduct.put(AppParams.LINE_ITEM_ID, ParamUtil.getString(inputMap, AppParams.S_LINE_ITEM_ID));
        orderProduct.put(AppParams.REFUNDED_ITEM, ParamUtil.getString(inputMap, AppParams.N_REFUNDED_ITEM));
        orderProduct.put(AppParams.VARIANT_FRONT_URL, ParamUtil.getString(inputMap, AppParams.S_VARIANT_FRONT_URL));
        orderProduct.put(AppParams.VARIANT_BACK_URL, ParamUtil.getString(inputMap, AppParams.S_VARIANT_BACK_URL));
        orderProduct.put(AppParams.COLOR_ID, ParamUtil.getString(inputMap, AppParams.S_COLOR_ID));
        orderProduct.put(AppParams.COLOR_VALUE, ParamUtil.getString(inputMap, AppParams.S_COLOR_VALUE));
        orderProduct.put(AppParams.PARTNER_SKU, ParamUtil.getString(inputMap, AppParams.S_PARTNER_SKU));
        orderProduct.put(AppParams.COLOR_NAME, ParamUtil.getString(inputMap, AppParams.S_COLOR_NAME));
        orderProduct.put(AppParams.SIZE_NAME, ParamUtil.getString(inputMap, AppParams.S_SIZE_NAME));
        orderProduct.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(inputMap, AppParams.S_SHIPPING_METHOD));
        orderProduct.put(AppParams.UNIT_AMOUNT, ParamUtil.getString(inputMap, AppParams.S_UNIT_AMOUNT));
        orderProduct.put(AppParams.REFUND_ITEM, ParamUtil.getString(inputMap, AppParams.N_REFUND_ITEM));
        orderProduct.put(AppParams.PRINT_DETAIL, ParamUtil.getString(inputMap, AppParams.S_PRINT_DETAIL));
        orderProduct.put(AppParams.ITEM_TYPE, ParamUtil.getString(inputMap, AppParams.S_ITEM_TYPE));
        orderProduct.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(inputMap, AppParams.S_DESIGN_BACK_URL));
        orderProduct.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(inputMap, AppParams.S_DESIGN_FRONT_URL));
        orderProduct.put(AppParams.PARTNER_PROPERTIES, ParamUtil.getString(inputMap, AppParams.S_PARTNER_PROPERTIES));
        orderProduct.put(AppParams.PARTNER_OPTION, ParamUtil.getString(inputMap, AppParams.S_PARTNER_OPTION));
        orderProduct.put(AppParams.TAX_AMOUNT, ParamUtil.getString(inputMap, AppParams.S_TAX_AMOUNT));
        orderProduct.put(AppParams.CUSTOM_DATA, ParamUtil.getString(inputMap, AppParams.S_CUSTOM_DATA));

        resultMap.put(AppParams.ORDER_PRODUCT, orderProduct);

        return resultMap;
    }
}
