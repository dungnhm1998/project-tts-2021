package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderService extends MasterService {
    private static final String GET_ORDER_BY_ID = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER_BY_ID(?,?,?,?)}";
    private static final String GET_ORDER_PRODUCT = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER_PRODUCT(?,?,?)}";
    private static final String GET_ORDER = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER(?,?,?)}";

    private static final String UPDATE_ORDER = "{call PKG_QUY.UPDATE_DROPSHIP_ORDER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String UPDATE_SHIPPING = "{call PKG_QUY.UPDATE_SHIPPING_SHIPPING(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String UPDATE_PRODUCT = "{call PKG_QUY.UPDATE_DROPSHIP_ORDER_PRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

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


    public static List<Map> updateProduct(
            String id,
            String order_id,
            String s_base_id,
            String s_color_value,
            String s_color_id,
            String s_color_name,
            String s_size_id,
            String s_size_name,
            String n_quantity,
            String s_design_front_url,
            String s_design_back_url,
            String s_variant_front_url,
            String s_variant_back_url,
            String s_variant_name,
            String s_unit_amount

    ) throws SQLException {
        List<Map> result = excuteQuery(UPDATE_PRODUCT, new Object[]{
                id,
                order_id,
                s_base_id,
                s_color_value,
                s_color_id,
                s_color_name,
                s_size_id,
                s_size_name,
                n_quantity,
                s_design_front_url,
                s_design_back_url,
                s_variant_front_url,
                s_variant_back_url,
                s_variant_name,
                s_unit_amount
        });
        System.out.println("result" + result);
        return result;
    }


    public static Map updateShipping(String shippingId,
                                     String email, String nameShipping, String phone,
                                     String line1, String line2, String city, String state, String postalCode, String country, String countryName) throws SQLException {
        Map result = searchOne(UPDATE_SHIPPING, new Object[]{shippingId,
                email, nameShipping, phone,
                line1, line2, city, state, postalCode, country, countryName});

        return result;
    }

    public static Map updateOrder(String orderId, String currency,  String state, String shippingId, String tracking_code,  String note, String channel, String shippingFree, String source, String storeId, String referenceId, int quantity, int addrVerified,
                                  String addrVerifiedNote, String extraFee, String shippingMethod, String taxAmount, String iossNumber) throws SQLException {
        Map result = searchOne(UPDATE_ORDER, new Object[]{orderId, currency, state, shippingId,
                tracking_code, note, channel, shippingFree,
                source, storeId, referenceId, quantity, addrVerified,
                addrVerifiedNote, extraFee, shippingMethod, taxAmount, iossNumber});

        return result;
    }


    public static Map formatUpdateOrder(Map orderInput, Map shippingInput, List<Map> productList) {
        Map resultMap = new LinkedHashMap();

        resultMap.put(AppParams.ID, ParamUtil.getString(orderInput, AppParams.S_ID));
        resultMap.put(AppParams.CURRENCY, ParamUtil.getString(orderInput, AppParams.S_CURRENCY));
        resultMap.put("sub_amount", ParamUtil.getString(orderInput, AppParams.S_SUB_AMOUNT));
        resultMap.put("shipping_fee", ParamUtil.getString(orderInput, AppParams.S_SHIPPING_FEE));
        resultMap.put("tax_amount", ParamUtil.getString(orderInput, AppParams.S_TAX_AMOUNT));
        resultMap.put(AppParams.STATE, ParamUtil.getString(orderInput, AppParams.S_STATE));
        resultMap.put("quantity", ParamUtil.getString(orderInput, "N_TOTAL_ITEM"));
        resultMap.put("create_date", ParamUtil.getString(orderInput, AppParams.D_CREATE));
        resultMap.put("update_date", ParamUtil.getString(orderInput, AppParams.D_UPDATE));
        resultMap.put("tracking_code", ParamUtil.getString(orderInput, AppParams.S_TRACKING_CODE));
        resultMap.put("order_date", ParamUtil.getString(orderInput, "D_ORDER    "));
        resultMap.put("note", ParamUtil.getString(orderInput, AppParams.S_NOTE));
        resultMap.put("chanel", ParamUtil.getString(orderInput, AppParams.S_CHANNEL));
        resultMap.put(AppParams.USER_ID, ParamUtil.getString(orderInput, AppParams.S_USER_ID));

        resultMap.put(AppParams.STORE_ID, ParamUtil.getString(orderInput, AppParams.S_STORE_ID));
        resultMap.put("store_name", ParamUtil.getString(orderInput, ""));
        resultMap.put(AppParams.SHIPPING_ID, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_ID));
        resultMap.put("original_id", ParamUtil.getString(orderInput, AppParams.S_ORIGINAL_ID));
        resultMap.put(AppParams.SOURCE, ParamUtil.getString(orderInput, AppParams.S_SOURCE));
        resultMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_METHOD));
        boolean Unfulfilled = true, fulfilled = false;

        resultMap.put("fulfill_state", ParamUtil.getBoolean(orderInput, AppParams.N_FULFILLED_ITEM) ? Unfulfilled : fulfilled);

        resultMap.put("ioss_number", ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));

//        Map getshipping = shippingInput.get(0);
        Map shippingMap = new LinkedHashMap();
        shippingMap.put(AppParams.ID, ParamUtil.getString(shippingInput, AppParams.S_ID));
        shippingMap.put(AppParams.NAME, ParamUtil.getString(shippingInput, AppParams.S_NAME));
        shippingMap.put(AppParams.EMAIL, ParamUtil.getString(shippingInput, AppParams.S_EMAIL));
        shippingMap.put(AppParams.PHONE, ParamUtil.getString(shippingInput, AppParams.S_PHONE));
        shippingMap.put(AppParams.GIFT, ParamUtil.getString(shippingInput, AppParams.N_GIFT));

        Map addressMap = new LinkedHashMap();
        addressMap.put(AppParams.LINE1, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE1));
        addressMap.put(AppParams.LINE2, ParamUtil.getString(shippingInput, "S_ADD_LINE2"));
        addressMap.put(AppParams.CITY, ParamUtil.getString(shippingInput, AppParams.S_ADD_CITY));
        addressMap.put(AppParams.STATE, ParamUtil.getString(shippingInput, AppParams.S_STATE));
        addressMap.put(AppParams.POSTAL_CODE, ParamUtil.getString(shippingInput, AppParams.S_POSTAL_CODE));
        addressMap.put(AppParams.COUNTRY, ParamUtil.getString(shippingInput, "S_COUNTRY_CODE"));
        addressMap.put(AppParams.COUNTRY_NAME, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_NAME));
        addressMap.put(AppParams.ADDR_VERIFIED, ParamUtil.getString(orderInput, AppParams.N_ADDR_VERIFIED));
        addressMap.put(AppParams.ADDR_VERIFIED_NOTE, ParamUtil.getString(orderInput, AppParams.S_ADDR_VERIFIED_NOTE));

        shippingMap.put(AppParams.ADDRESS, addressMap);
        resultMap.put(AppParams.SHIPPING, shippingMap);
        resultMap.put(AppParams.EXTRA_FEE_2, ParamUtil.getString(orderInput, AppParams.S_EXTRA_FEE));
        resultMap.put(AppParams.UNIT_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_AMOUNT));


        List<Map> itemsList = new LinkedList<>();
        for (Map productInput : productList) {
            Map productMap = new LinkedHashMap();
            productMap.put(AppParams.ID, ParamUtil.getString(productInput, AppParams.S_ID));
            productMap.put("campaign_title", ParamUtil.getString(productInput, AppParams.S_TITLE));
            productMap.put("campaign_url", ParamUtil.getString(productInput, ""));
            productMap.put("user_id", ParamUtil.getString(productInput, AppParams.S_USER_ID));

            Map campaign = new LinkedHashMap();
            campaign.put(AppParams.ID, ParamUtil.getString(productInput, AppParams.S_CAMPAIGN_ID));
            campaign.put("title", ParamUtil.getString(productInput, AppParams.S_TITLE));
            campaign.put("url", ParamUtil.getString(productInput, ""));
            campaign.put("end_time", ParamUtil.getString(productInput, "MODIFIED_AT"));
            campaign.put("domain", ParamUtil.getString(productInput, AppParams.S_DOMAIN));
            campaign.put("domain_id", ParamUtil.getString(productInput, AppParams.S_DOMAIN_ID));
            productMap.put("campaign", campaign);

            productMap.put(AppParams.PRODUCT_ID, ParamUtil.getString(productInput, AppParams.S_PRODUCT_ID));
            productMap.put(AppParams.BASE_ID, ParamUtil.getString(productInput, AppParams.S_BASE_ID));
            productMap.put("variant_id", ParamUtil.getString(productInput, AppParams.S_VARIANT_ID));
            productMap.put("variant_name", ParamUtil.getString(productInput, AppParams.S_VARIANT_NAME));
            productMap.put(AppParams.SIZES, ParamUtil.getString(productInput, "S_SIZE_ID"));
            productMap.put(AppParams.SIZE_NAME, ParamUtil.getString(productInput, "S_SIZE_NAME"));
            productMap.put(AppParams.COLOR, ParamUtil.getString(productInput, AppParams.S_COLOR_VALUE));
            productMap.put(AppParams.COLOR_ID, ParamUtil.getString(productInput, AppParams.S_COLOR_ID));
            productMap.put(AppParams.COLOR_NAME, ParamUtil.getString(productInput, AppParams.S_COLOR_NAME));
            productMap.put(AppParams.VARIANT_NAME, ParamUtil.getString(productInput, AppParams.S_VARIANT_NAME));
            productMap.put("currency", ParamUtil.getString(productInput, AppParams.S_CURRENCY));
            productMap.put(AppParams.QUANTITY, ParamUtil.getString(productInput, AppParams.N_QUANTITY));
            productMap.put("shipping_fee", ParamUtil.getString(productInput, AppParams.S_SHIPPING_FEE));
            productMap.put("amount", ParamUtil.getString(productInput, AppParams.S_AMOUNT));
            productMap.put("state", ParamUtil.getString(productInput, AppParams.S_STATE));
            productMap.put("base_cost", ParamUtil.getString(productInput, AppParams.S_BASE_COST));
            productMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(productInput, AppParams.S_SHIPPING_METHOD));
            productMap.put(AppParams.ITEM_TYPE, ParamUtil.getString(productInput, AppParams.S_ITEM_TYPE));
            productMap.put(AppParams.ITEM_TYPE, ParamUtil.getString(productInput, AppParams.S_ITEM_TYPE));
            productMap.put(AppParams.PRINT_DETAIL, ParamUtil.getString(productInput, AppParams.S_PRINT_DETAIL));
            productMap.put(AppParams.LINE_ITEM_ID, ParamUtil.getString(productInput, AppParams.S_LINE_ITEM_ID));

            Map designsMap = new LinkedHashMap();
            designsMap.put("design_front_url", ParamUtil.getString(productInput, AppParams.S_DESIGN_FRONT_URL));
            designsMap.put("design_back_url", ParamUtil.getString(productInput, "S_DESIGN_BACK_URL"));
            designsMap.put("mockup_front_url", ParamUtil.getString(productInput, AppParams.S_VARIANT_FRONT_URL));
            designsMap.put("mockup_back_url", ParamUtil.getString(productInput, AppParams.S_VARIANT_BACK_URL));


            productMap.put(AppParams.DESIGNS, designsMap);

            productMap.put(AppParams.PARTNER_SKU, ParamUtil.getString(productInput, AppParams.S_PARTNER_SKU));
            productMap.put("partner_url", ParamUtil.getString(productInput, ""));
            productMap.put("base_short_code", ParamUtil.getString(productInput, AppParams.S_BASE_COST));
            productMap.put("unit_amount", ParamUtil.getString(productInput, AppParams.S_UNIT_AMOUNT));
            productMap.put("tax", ParamUtil.getString(productInput, ""));
            productMap.put("tax_amount", ParamUtil.getString(productInput, AppParams.S_TAX_AMOUNT));
            productMap.put("tax_rate", ParamUtil.getString(productInput, ""));


            Map brand = new LinkedHashMap();
            productMap.put("brand", brand);

            productMap.put("source", ParamUtil.getString(productInput, ""));


            itemsList.add(productMap);
        }

        resultMap.put(AppParams.ITEMS, itemsList);

        resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_TAX_AMOUNT));
        resultMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));

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
