package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderService extends MasterService {
    private static final String GET_ORDER_BY_ID = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER_BY_ID(?,?,?,?)}";
    private static final String GET_ORDER_PRODUCT = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER_PRODUCT(?,?,?)}";
    private static final String GET_ORDER = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER(?,?,?)}";
    private static final String INSERT_DROPSHIP_ORDER = "{call PKG_DROPSHIP_ORDER_PHUONG.INSERT_DROPSHIP_ORDER" +
            "(?, ?,?,?,?,?,?,?, ?,?, ?,?, ?,?, ?,?,?)}";
    private static final String INSERT_SHIPPING = "{call PKG_DROPSHIP_ORDER_PHUONG.INSERT_SHIPPING" +
            "(?, ?,?,?, ?,?,?,?,?,?,?, ?,?,?)}";
    private static final String INSERT_DROPSHIP_ORDER_PRODUCT = "{call PKG_DROPSHIP_ORDER_PHUONG.INSERT_DROPSHIP_ORDER_PRODUCT" +
            "(?, ?,?,?,?,?,?,?,?,?, ?,?,?,?, ?,?, ?,?,?)}";

    private static final String UPDATE_DROPSHIP_ORDER = "{call PKG_DROPSHIP_ORDER_PHUONG.UPDATE_DROPSHIP_ORDER" +
            "(?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?, ?,?,?,?,?, ?, ?,?,?,?, ?,?, ?,?,?)}";
    private static final String UPDATE_SHIPPING = "{call PKG_DROPSHIP_ORDER_PHUONG.UPDATE_SHIPPING" +
            "(?, ?,?,?,?, ?,?,?,?,?,?,?, ?,?,?)}";
    private static final String UPDATE_DROPSHIP_ORDER_PRODUCT = "{call PKG_DROPSHIP_ORDER_PHUONG.UPDATE_DROPSHIP_ORDER_PRODUCT" +
            "(?, ?,?, ?,?,?,?,?, ?,?, ?,?, ?,?, ?,?, ?,?,?)}";

    private static final String GET_PRODUCT_BY_ORDER_ID = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_PRODUCT_BY_ORDER_ID" +
            "(?, ?,?,?)}";
    private static final String DELETE_PRODUCT_IN_ORDER = "{call PKG_DROPSHIP_ORDER_PHUONG.DELETE_PRODUCT_IN_ORDER" +
            "(?,?, ?,?,?)}";

    public static Map deleteProductInOrder(String orderId, String productId) throws SQLException {
        List<Map> resultMap = excuteQuery(DELETE_PRODUCT_IN_ORDER, new Object[]{orderId, productId});
        Map result = new LinkedHashMap();
        if (!resultMap.isEmpty()) {
            result = resultMap.get(0);
        }
        return result;
    }

    public static List<String> getProductByOrderId(String orderId) throws SQLException {
        List<Map> resultList = excuteQuery(GET_PRODUCT_BY_ORDER_ID, new Object[]{orderId});
        List<String> result = new LinkedList<>();
        for (Map resultMap : resultList) {
            result.add(ParamUtil.getString(resultMap, AppParams.S_ID));
        }
        return result;
    }

    public static Map updateProduct(String orderId,
                                    String id, String baseId,
                                    String color, String colorId, String colorName, String sizeId, String sizeName,
                                    String customData, int quantity,
                                    String campaignId, String unitAmount,
                                    String designFrontUrl, String designBackUrl,
                                    String variantId, String productId) throws SQLException {
        List<Map> resultMap = excuteQuery(UPDATE_DROPSHIP_ORDER_PRODUCT, new Object[]{orderId,
                id, baseId,
                color, colorId, colorName, sizeId, sizeName,
                customData, quantity,
                campaignId, unitAmount,
                designFrontUrl, designBackUrl,
                variantId, productId});

        Map result = new LinkedHashMap();
        if (!resultMap.isEmpty()) {
            result = resultMap.get(0);
        }
        return result;
    }

    public static Map updateShipping(String shippingId,
                                     String nameShipping, String email, String phone, int gift,
                                     String line1, String line2, String city, String state, String postalCode, String country, String countryName)
            throws SQLException {
        List<Map> resultMap = excuteQuery(UPDATE_SHIPPING, new Object[]{shippingId,
                nameShipping, email, phone, gift,
                line1, line2, city, state, postalCode, country, countryName});

        Map result = new LinkedHashMap();
        if (!resultMap.isEmpty()) {
            result = resultMap.get(0);
        }
        return result;
    }

    public static Map updateOrder(String orderId,
                                  String currency, String subAmount, String shippingFee, String taxAmount, String state,
                                  Date createDate, Date updateDate, String trackingCode, Date orderDate, String note,
                                  String channel, String userId, String storeId,
                                  String shippingId, String originalId, String source, String shippingMethod, int fulfillState,
                                  String iossNumber,
                                  String referenceId, int requireRefund, String extraFee, String amount,
                                  int addrVerified, String addrVerifiedNote) throws SQLException {
        List<Map> resultMap = excuteQuery(UPDATE_DROPSHIP_ORDER, new Object[]{orderId,
                currency, subAmount, shippingFee, taxAmount, state,
                createDate, updateDate, trackingCode, orderDate, note,
                channel, userId, storeId,
                shippingId, originalId, source, shippingMethod, fulfillState,
                iossNumber,
                referenceId, requireRefund, extraFee, amount,
                addrVerified, addrVerifiedNote});

        Map result = new LinkedHashMap();
        if (!resultMap.isEmpty()) {
            result = resultMap.get(0);
        }
        return result;
    }

    public static Map formatUpdateOrder(Map orderInput, Map shippingInput, List<Map> productListInput) {
        Map orderMap = new LinkedHashMap();

        orderMap.put(AppParams.ID, ParamUtil.getString(orderInput, AppParams.S_ID));
        orderMap.put(AppParams.CURRENCY, ParamUtil.getString(orderInput, AppParams.S_CURRENCY));
        orderMap.put(AppParams.SUB_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_SUB_AMOUNT));
        orderMap.put(AppParams.SHIPPING_FEE, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_FEE));
        orderMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_TAX_AMOUNT));
        orderMap.put(AppParams.STATE, ParamUtil.getString(orderInput, AppParams.S_STATE));
        orderMap.put(AppParams.QUANTITY, "");
        orderMap.put(AppParams.CREATE_DATE, ParamUtil.getString(orderInput, AppParams.D_CREATE));
        orderMap.put(AppParams.UPDATE_DATE, ParamUtil.getString(orderInput, AppParams.D_UPDATE));
        orderMap.put(AppParams.TRACKING_CODE, ParamUtil.getString(orderInput, AppParams.S_TRACKING_CODE));
        orderMap.put(AppParams.ORDER_DATE, ParamUtil.getString(orderInput, AppParams.D_ORDER));
        orderMap.put(AppParams.NOTE, ParamUtil.getString(orderInput, AppParams.S_NOTE));
        orderMap.put(AppParams.CHANNEL, ParamUtil.getString(orderInput, AppParams.S_CHANNEL));
        orderMap.put(AppParams.USER_ID, ParamUtil.getString(orderInput, AppParams.S_USER_ID));
        orderMap.put(AppParams.STORE_ID, ParamUtil.getString(orderInput, AppParams.S_STORE_ID));
        orderMap.put("store_name", "");
        orderMap.put(AppParams.SHIPPING_ID, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_ID));
        orderMap.put(AppParams.ORIGINAL_ID, ParamUtil.getString(orderInput, AppParams.S_ORIGINAL_ID));
        orderMap.put(AppParams.SOURCE, ParamUtil.getString(orderInput, AppParams.S_SOURCE));
        orderMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_METHOD));
        orderMap.put("fulfill_state", ParamUtil.getInt(orderInput, AppParams.N_FULFILLED_ITEM) == 0 ? "Unfulfilled" : "Fulfilled");
        orderMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));

        //SHIPPING
        Map shippingMap = new LinkedHashMap();
        shippingMap.put(AppParams.ID, ParamUtil.getString(shippingInput, AppParams.S_ID));
        shippingMap.put(AppParams.NAME, ParamUtil.getString(shippingInput, AppParams.S_NAME));
        shippingMap.put(AppParams.EMAIL, ParamUtil.getString(shippingInput, AppParams.S_EMAIL));
        shippingMap.put(AppParams.PHONE, ParamUtil.getString(shippingInput, "S_PHONE"));
        shippingMap.put("gift", ParamUtil.getInt(shippingInput, "N_GIFT") == 1);
        //address
        Map addressMap = new LinkedHashMap();
        addressMap.put(AppParams.LINE1, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE1));
        addressMap.put(AppParams.LINE2, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE2));
        addressMap.put(AppParams.CITY, ParamUtil.getString(shippingInput, AppParams.S_ADD_CITY));
        addressMap.put(AppParams.STATE, ParamUtil.getString(shippingInput, AppParams.S_STATE));
        addressMap.put(AppParams.POSTAL_CODE, ParamUtil.getString(shippingInput, AppParams.S_POSTAL_CODE));
        addressMap.put(AppParams.COUNTRY, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_CODE));
        addressMap.put(AppParams.COUNTRY_NAME, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_NAME));

        addressMap.put(AppParams.ADDR_VERIFIED, ParamUtil.getInt(orderInput, AppParams.N_ADDR_VERIFIED) == 1);
        addressMap.put(AppParams.ADDR_VERIFIED_NOTE, ParamUtil.getString(orderInput, AppParams.S_ADDR_VERIFIED_NOTE));

        shippingMap.put(AppParams.ADDRESS, addressMap);

        orderMap.put(AppParams.SHIPPING, shippingMap);

        orderMap.put("store_domain", "");
        orderMap.put(AppParams.REFERENCE_ID, ParamUtil.getString(orderInput, AppParams.S_REFERENCE_ORDER));
        orderMap.put(AppParams.REQUIRE_REFUND, ParamUtil.getInt(orderInput, AppParams.N_REQUIRE_REFUND));
        orderMap.put("extra_fee", ParamUtil.getString(orderInput, AppParams.S_EXTRA_FEE));
        orderMap.put(AppParams.AMOUNT, ParamUtil.getString(orderInput, AppParams.S_AMOUNT));

        List<Map> itemsList = new LinkedList<>();
        for (Map productMap : productListInput) {
            Map itemsMap = new LinkedHashMap();
            itemsMap.put(AppParams.ID, ParamUtil.getString(productMap, AppParams.S_ID));
            itemsMap.put("campaign_title", "");
            itemsMap.put("campaign_url", "");
            itemsMap.put(AppParams.USER_ID, ParamUtil.getString(orderInput, AppParams.S_USER_ID));

            Map campaignMap = new LinkedHashMap();
            campaignMap.put(AppParams.ID, ParamUtil.getString(productMap, AppParams.S_CAMPAIGN_ID));
            campaignMap.put(AppParams.TITLE, "");
            campaignMap.put(AppParams.URL, "");
            campaignMap.put("end_time", "");
            campaignMap.put(AppParams.DOMAIN, "");
            campaignMap.put(AppParams.DOMAIN_ID, "");

            itemsMap.put("campaign", campaignMap);

            itemsMap.put(AppParams.PRODUCT_ID, ParamUtil.getString(productMap, AppParams.S_PRODUCT_ID));
            itemsMap.put(AppParams.BASE_ID, ParamUtil.getString(productMap, AppParams.S_BASE_ID));
            itemsMap.put(AppParams.VARIANT_ID, ParamUtil.getString(productMap, AppParams.S_VARIANT_ID));
            itemsMap.put(AppParams.VARIANT_NAME, ParamUtil.getString(productMap, AppParams.S_VARIANT_NAME));
            itemsMap.put(AppParams.SIZE_ID, ParamUtil.getString(productMap, AppParams.S_SIZE_ID));
            itemsMap.put(AppParams.SIZE_NAME, ParamUtil.getString(productMap, AppParams.S_SIZE_NAME));
            itemsMap.put(AppParams.COLOR_ID, ParamUtil.getString(productMap, AppParams.S_COLOR_ID));
            itemsMap.put(AppParams.COLOR_NAME, ParamUtil.getString(productMap, AppParams.S_COLOR_NAME));
            itemsMap.put(AppParams.COLOR, ParamUtil.getString(productMap, AppParams.S_COLOR_VALUE));
            itemsMap.put("variant_image", ParamUtil.getString(productMap, AppParams.S_VARIANT_FRONT_URL));// k tim thay cot tuong ung
            itemsMap.put(AppParams.PRICE, ParamUtil.getString(productMap, AppParams.S_PRICE));
            itemsMap.put(AppParams.CURRENCY, ParamUtil.getString(productMap, AppParams.S_CURRENCY));
            itemsMap.put(AppParams.QUANTITY, ParamUtil.getInt(productMap, AppParams.N_QUANTITY));
            itemsMap.put(AppParams.SHIPPING_FEE, ParamUtil.getString(productMap, AppParams.S_SHIPPING_FEE));
            itemsMap.put(AppParams.AMOUNT, ParamUtil.getString(productMap, AppParams.S_AMOUNT));
            itemsMap.put(AppParams.STATE, ParamUtil.getString(productMap, AppParams.S_STATE));
            itemsMap.put(AppParams.BASE_COST, ParamUtil.getString(productMap, AppParams.S_BASE_COST));
            itemsMap.put(AppParams.LINE_ITEM_ID, ParamUtil.getString(productMap, AppParams.S_LINE_ITEM_ID));
            itemsMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(productMap, AppParams.S_SHIPPING_METHOD));
            itemsMap.put(AppParams.ITEM_TYPE, ParamUtil.getString(productMap, AppParams.S_ITEM_TYPE));
            itemsMap.put(AppParams.PRINT_DETAIL, ParamUtil.getString(productMap, AppParams.S_PRINT_DETAIL));

            Map designsMap = new LinkedHashMap();
            designsMap.put("mockup_front_url", "");
            designsMap.put("mockup_back_url", "");
            designsMap.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(productMap, AppParams.S_DESIGN_FRONT_URL));
            designsMap.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(productMap, AppParams.S_DESIGN_BACK_URL));

            itemsMap.put(AppParams.DESIGNS, designsMap);

            itemsMap.put(AppParams.PARTNER_SKU, ParamUtil.getString(productMap, AppParams.S_PARTNER_SKU));
            itemsMap.put("partner_url", "");
            itemsMap.put("base_short_code", "");
            itemsMap.put(AppParams.UNIT_AMOUNT, ParamUtil.getString(productMap, AppParams.S_UNIT_AMOUNT));
            itemsMap.put("tax", "");// k tim thay cot nay
            itemsMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(productMap, AppParams.S_TAX_AMOUNT));
            itemsMap.put("tax_rate", "0.0"); // k tim thay cot nay

            Map brandMap = new LinkedHashMap();
            itemsMap.put("brand", brandMap);

            String source = ParamUtil.getString(orderInput, AppParams.S_SOURCE);
            List<String> listSource = Arrays.asList(source.split("\\."));
            if (!listSource.isEmpty()) {
                itemsMap.put(AppParams.SOURCE, listSource.get(0));
            } else {
                itemsMap.put(AppParams.SOURCE, "");
            }

            itemsList.add(itemsMap);
        }

        orderMap.put(AppParams.ITEMS, itemsList);

        return orderMap;
    }

    public static Map insertProduct(String orderId,
                                    String id, String baseId, String color, String colorId, String colorName, String sizeId, String sizeName, int quantity, String price,
                                    String designFrontUrl, String designFrontUrlMd5, String designBackUrl, String designBackUrlMd5,
                                    String variantName, String unitAmount
    ) throws SQLException {
        List<Map> resultMap = excuteQuery(INSERT_DROPSHIP_ORDER_PRODUCT, new Object[]{orderId,
                id, baseId, color, colorId, colorName, sizeId, sizeName, quantity, price,
                designFrontUrl, designFrontUrlMd5, designBackUrl, designBackUrlMd5,
                variantName, unitAmount
        });
        Map result = new LinkedHashMap();
        if (!resultMap.isEmpty()) {
            result = resultMap.get(0);
        }
        return result;
    }

    public static List<Map> insertShipping(String shippingId,
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
        shippingMap.put(AppParams.EMAIL, ParamUtil.getString(shippingInput, AppParams.S_EMAIL));
        shippingMap.put(AppParams.NAME, ParamUtil.getString(shippingInput, AppParams.S_NAME));
        shippingMap.put(AppParams.PHONE, ParamUtil.getString(shippingInput, AppParams.S_PHONE));

        Map addressMap = new LinkedHashMap();
        addressMap.put(AppParams.LINE1, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE1));
        addressMap.put(AppParams.LINE2, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE2));
        addressMap.put(AppParams.CITY, ParamUtil.getString(shippingInput, AppParams.S_ADD_CITY));
        addressMap.put(AppParams.STATE, ParamUtil.getString(shippingInput, AppParams.S_STATE));
        addressMap.put(AppParams.POSTAL_CODE, ParamUtil.getString(shippingInput, AppParams.S_POSTAL_CODE));
        addressMap.put(AppParams.COUNTRY, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_CODE));
        addressMap.put(AppParams.COUNTRY_NAME, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_NAME));

        addressMap.put(AppParams.ADDR_VERIFIED, ParamUtil.getInt(orderInput, AppParams.N_ADDR_VERIFIED) == 1);
        addressMap.put(AppParams.ADDR_VERIFIED_NOTE, ParamUtil.getString(orderInput, AppParams.S_ADDR_VERIFIED_NOTE));
        shippingMap.put(AppParams.ADDRESS, addressMap);

        orderMap.put(AppParams.SHIPPING, shippingMap);

        orderMap.put(AppParams.EXTRA_FEE, ParamUtil.getString(orderInput, AppParams.S_EXTRA_FEE));

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
            productMap.put(AppParams.QUANTITY, ParamUtil.getInt(productInput, AppParams.N_QUANTITY));
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

    private static final String UPDATE_ORDER = "{call PKG_QUY.UPDATE_DROPSHIP_ORDER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String UPDATE_SHIPPINGG = "{call PKG_QUY.UPDATE_SHIPPING_SHIPPING(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String UPDATE_PRODUCT = "{call PKG_QUY.UPDATE_DROPSHIP_ORDER_PRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    public static List<Map> getOrderProduct() throws SQLException {
        List<Map> resultMap = excuteQuery(GET_ORDER_PRODUCT, new Object[]{});
        List<Map> result = new ArrayList<>();
        for (Map map : resultMap) {
            map = formatOrderProduct(map);
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
        Map resultMap = new LinkedHashMap();
        if (!result.isEmpty()) {
            resultMap = result.get(0);
        }
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
        System.out.println("result" +  result);
        return result;
    }


    public static Map updateShipping(String shippingId,
                                     String email, String nameShipping, String phone,
                                     String line1, String line2, String city, String state, String postalCode, String country, String countryName) throws SQLException {
        Map result = searchOne(UPDATE_SHIPPINGG, new Object[]{shippingId,
                email, nameShipping, phone,
                line1, line2, city, state, postalCode, country, countryName});

        return result;
    }

    public static Map updateOrder(String orderId, String source, String currency, String note,
                                  String storeId, String referenceId, String state, String shippingMethod,
                                  String shipping, String extraFee, String taxAmount, String iossNumber,
                                  int addrVerified, String addrVerifiedNote) throws SQLException {
        Map result = searchOne(UPDATE_ORDER, new Object[]{orderId, source, currency, note,
                storeId, referenceId, state, shippingMethod,
                shipping, extraFee, taxAmount, iossNumber,
                addrVerified, addrVerifiedNote});

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
        resultMap.put("quantity", ParamUtil.getString(orderInput, ""));
        resultMap.put("create_date", ParamUtil.getString(orderInput, AppParams.D_CREATE));
        resultMap.put("update_date", ParamUtil.getString(orderInput, AppParams.D_UPDATE));
        resultMap.put("tracking_code", ParamUtil.getString(orderInput, AppParams.S_TRACKING_CODE));
        resultMap.put("order_date", ParamUtil.getString(orderInput, ""));
        resultMap.put("note", ParamUtil.getString(orderInput, AppParams.S_NOTE));
        resultMap.put("chanel", ParamUtil.getString(orderInput, AppParams.S_CHANNEL));
        resultMap.put(AppParams.USER_ID, ParamUtil.getString(orderInput, AppParams.S_USER_ID));

        resultMap.put(AppParams.STORE_ID, ParamUtil.getString(orderInput, AppParams.S_STORE_ID));
        resultMap.put("store_name", ParamUtil.getString(orderInput, ""));
        resultMap.put(AppParams.SHIPPING_ID, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_ID));
        resultMap.put("original_id", ParamUtil.getString(orderInput, AppParams.S_ORIGINAL_ID));
        resultMap.put(AppParams.SOURCE, ParamUtil.getString(orderInput, AppParams.S_SOURCE));
        resultMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_METHOD));
        resultMap.put("fulfill_state", ParamUtil.getString(orderInput, AppParams.N_FULFILLED_ITEM));
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
        addressMap.put(AppParams.LINE2, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE2));
        addressMap.put(AppParams.CITY, ParamUtil.getString(shippingInput, AppParams.S_ADD_CITY));
        addressMap.put(AppParams.STATE, ParamUtil.getString(shippingInput, AppParams.S_STATE));
        addressMap.put(AppParams.POSTAL_CODE, ParamUtil.getString(shippingInput, AppParams.S_POSTAL_CODE));
        addressMap.put(AppParams.COUNTRY, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_CODE));
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

        resultMap.put(AppParams.ID, ParamUtil.getString(inputMap, AppParams.S_ID));
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

        return resultMap;
    }
        public static Map formatOrderProduct(Map inputMap) {
        //product
        Map orderProduct = new LinkedHashMap();
        orderProduct.put(AppParams.ID, ParamUtil.getString(inputMap, AppParams.S_ID));
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

        return orderProduct;
    }


}
