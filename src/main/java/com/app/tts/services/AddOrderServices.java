package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddOrderServices extends MasterService {
    private static final String INSERT_ORDER = "{call PKG_QUY.insert_dropship_order(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    private static final String INS_ORDER_PRODUCT = "{call PKG_QUY.insert_dropship_order_product(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    private static final String INS_SHIPPING = "{call PKG_QUY.insert_shipping_shipping(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";


    public static Map insertOrder(
            String p_orderid,
            String p_currency,
            String p_state,
            String p_note,
            String p_source,
            String p_storeid,
            String p_referenceid,
            int p_addrverifiedin,
            String p_addrverifiednote,
            String p_shippingmethod,
            String p_taxamount,
            String p_iossnumber
    ) throws SQLException {
        Map result = new HashMap();
        Map insertOrder = searchOne(INSERT_ORDER, new Object[]{
                p_orderid,
                p_currency,
                p_state,
                p_note,
                p_source,
                p_storeid,
                p_referenceid,
                p_addrverifiedin,
                p_addrverifiednote,
                p_shippingmethod,
                p_taxamount,
                p_iossnumber
        });

        System.out.println("insertOrder" + insertOrder);
        return insertOrder;
    }

    public static Map insertShipping(
            String shipping_id,
            String email,
            String name_shipping,
            String phone,
            String line1,
            String line2,
            String city,
            String state,
            String postal_code,
            String country,
            String country_name


    ) throws SQLException {
        Map result = new HashMap();
        Map insertShipping = searchOne(INS_SHIPPING, new Object[]{
                shipping_id,
                email,
                name_shipping,
                phone,
                line1,
                line2,
                city,
                state,
                postal_code,
                country,
                country_name
        });
        System.out.println("insertShipping" +insertShipping);
        return insertShipping;
    }



    public static List<Map> insertOrderProduct(
            String p_id,
            String p_order_id,
            String p_size_id,
            String price,
            int p_quantity,
            String p_variant_name,
            String p_base_id,
            String p_variant_front_url,
            String p_variant_back_url,
            String p_color_id,
            String p_color_value,
            String p_color_name,
            String p_size_name,
            String p_unit_amount,
            String p_design_back_url,
            String p_design_front_url
    ) throws SQLException {
        Map result = new HashMap();
        List<Map> insertOrProduct = excuteQuery(INS_ORDER_PRODUCT, new Object[]{
                p_id,
                p_order_id,
                p_size_id,
                price,
                p_quantity,
                p_variant_name,
                p_base_id,
                p_variant_front_url,
                p_variant_back_url,
                p_color_id,
                p_color_value,
                p_color_name,
                p_size_name,
                p_unit_amount,
                p_design_back_url,
                p_design_front_url
        });
        System.out.println("insertOrProduct"+ insertOrProduct);

        return insertOrProduct;
    }


    public static Map formatInsertOrder(Map orderInput, Map shippingInput, List<Map> productList) {
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
        resultMap.put("order_date", ParamUtil.getString(orderInput, "D_ORDER"));
        resultMap.put("note", ParamUtil.getString(orderInput, AppParams.S_NOTE));
        resultMap.put("chanel", ParamUtil.getString(orderInput, AppParams.S_CHANNEL));
        resultMap.put(AppParams.USER_ID, ParamUtil.getString(orderInput, AppParams.S_USER_ID));

        resultMap.put(AppParams.STORE_ID, ParamUtil.getString(orderInput, AppParams.S_STORE_ID));
        resultMap.put("store_name", ParamUtil.getString(orderInput, ""));
        resultMap.put(AppParams.SHIPPING_ID, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_ID));
        resultMap.put("original_id", ParamUtil.getString(orderInput, AppParams.S_ORIGINAL_ID));
        resultMap.put(AppParams.SOURCE, ParamUtil.getString(orderInput, AppParams.S_SOURCE));
        resultMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_METHOD));


        resultMap.put("fulfill_state", ParamUtil.getInt(orderInput, AppParams.N_FULFILLED_ITEM) == 0 ? "Unfulfilled" : "fulfilled");

        resultMap.put("ioss_number", ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));

//        Map getshipping = shippingInput.get(0);
        Map shippingMap = new LinkedHashMap();
        shippingMap.put(AppParams.ID, ParamUtil.getString(shippingInput, AppParams.S_ID));
        shippingMap.put(AppParams.NAME, ParamUtil.getString(shippingInput, AppParams.S_NAME));
        shippingMap.put(AppParams.EMAIL, ParamUtil.getString(shippingInput, AppParams.S_EMAIL));
        shippingMap.put(AppParams.PHONE, ParamUtil.getString(shippingInput, AppParams.S_PHONE));
        String gift = ParamUtil.getString(shippingInput, AppParams.N_GIFT);

        shippingMap.put(AppParams.GIFT, ParamUtil.getInt(shippingInput, AppParams.N_GIFT) == 0 ? false : true);

        Map addressMap = new LinkedHashMap();
        addressMap.put(AppParams.LINE1, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE1));
        addressMap.put(AppParams.LINE2, ParamUtil.getString(shippingInput, "S_ADD_LINE2"));
        addressMap.put(AppParams.CITY, ParamUtil.getString(shippingInput, AppParams.S_ADD_CITY));
        addressMap.put(AppParams.STATE, ParamUtil.getString(shippingInput, AppParams.S_STATE));
        addressMap.put(AppParams.POSTAL_CODE, ParamUtil.getString(shippingInput, AppParams.S_POSTAL_CODE));
        addressMap.put(AppParams.COUNTRY, ParamUtil.getString(shippingInput, "S_COUNTRY_CODE"));
        addressMap.put(AppParams.COUNTRY_NAME, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_NAME));
        addressMap.put(AppParams.ADDR_VERIFIED, ParamUtil.getInt(orderInput, AppParams.N_ADDR_VERIFIED) == 0 ? false : true);
        addressMap.put(AppParams.ADDR_VERIFIED_NOTE, ParamUtil.getString(orderInput, AppParams.S_ADDR_VERIFIED_NOTE));

        shippingMap.put(AppParams.ADDRESS, addressMap);
        resultMap.put(AppParams.SHIPPING, shippingMap);
        resultMap.put(AppParams.EXTRA_FEE_2, ParamUtil.getString(orderInput, AppParams.S_EXTRA_FEE));
        resultMap.put(AppParams.UNIT_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_AMOUNT));


        List<Map> itemsList = new LinkedList<>();
        for (Map productInput : productList) {
            Map productMap = new LinkedHashMap();
            productMap.put(AppParams.ID, ParamUtil.getString(productInput, "PRODUCTID"));
            productMap.put("campaign_title", ParamUtil.getString(productInput, AppParams.S_TITLE));
            productMap.put("campaign_url", ParamUtil.getString(productInput, ""));
            productMap.put("user_id", ParamUtil.getString(productInput, "USER_ID"));

            Map campaign = new LinkedHashMap();
            campaign.put(AppParams.ID, ParamUtil.getString(productInput, "CAMPAIGN_ID"));
            campaign.put("title", ParamUtil.getString(productInput, AppParams.S_TITLE));
            campaign.put("url", ParamUtil.getString(productInput, ""));
            campaign.put("end_time", ParamUtil.getString(productInput, "MODIFIED_AT"));
            campaign.put("domain", ParamUtil.getString(productInput, AppParams.S_DOMAIN));
            campaign.put("domain_id", ParamUtil.getString(productInput, AppParams.S_DOMAIN_ID));
            productMap.put("campaign", campaign);

            productMap.put(AppParams.PRODUCT_ID, ParamUtil.getString(productInput, "PRODUCT_ID"));
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
            designsMap.put("design_front_url", ParamUtil.getString(productInput, "DESIGN_FRONT_URL"));
            designsMap.put("design_back_url", ParamUtil.getString(productInput, "DESIGN_BACK_URL"));
            designsMap.put("mockup_front_url", ParamUtil.getString(productInput, "VARIANT_FRONT_URL"));
            designsMap.put("mockup_back_url", ParamUtil.getString(productInput, "VARIANT_BACK_URL"));


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
}
































