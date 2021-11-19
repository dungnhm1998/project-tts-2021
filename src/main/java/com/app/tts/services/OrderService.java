package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderService extends MasterService {
    public static final String INSERT_SHIPPING = "{call "
            + "PKG_CREATE_ORDER.insert_tb_shipping(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    public static final String INSERT_DROPSHIP_ORDER = "{call "
            + "PKG_CREATE_ORDER.insert_dropship_order(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    public static final String INSERT_DROPSHIP_ORDER_PRODUCT = "{call "
            + "PKG_CREATE_ORDER.insert_dropship_order_product(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    public static List<Map> insertShipping(String ID, String email, String name, String phone, String line1, String line2,
                                           String city, String state, String postal_code, String country, String country_name) throws SQLException {
        List<Map> resultData = excuteQuery(INSERT_SHIPPING, new Object[]{ID, email, name, phone, line1, line2, city,
                state, postal_code, country, country_name});
        List<Map> result = new ArrayList<>();
        for (Map a : resultData) {
            a = format(a);
            result.add(a);
        }
        return result;
    }

    public static List<Map> insertDropshipOrder(String ID, String shipping_id, String source, String currency, String note, String store_id,
                                                String reference_id, String state, String shipping_method, int addr_verified, String addr_verified_note,
                                                String extra_fee, String tax_amount, String ioss_number) throws SQLException {
        List<Map> resultData = excuteQuery(INSERT_DROPSHIP_ORDER, new Object[]{ID, shipping_id, source, currency, note, store_id,
                reference_id, state, shipping_method, addr_verified, addr_verified_note,
                extra_fee, tax_amount, ioss_number});
        List<Map> result = new ArrayList<>();
        for (Map a : resultData) {
            a = format(a);
            result.add(a);
        }
        return result;
    }

    public static List<Map> insertDropshipOrderProduct(String order_id,
                                                       String id,
                                                       String base_id,
                                                       String color_value,
                                                       String color_id,
                                                       String color_name,
                                                       String size_id,
                                                       String size_name,
                                                       int quantity,
                                                       String price,
                                                       String design_front_url,
                                                       String variant_front_url,
                                                       String design_back_url,
                                                       String variant_back_url,
                                                       String variant_name,
                                                       String unit_amount) throws SQLException {
        List<Map> resultData = excuteQuery(INSERT_DROPSHIP_ORDER_PRODUCT, new Object[]{
               order_id,
               id,
               base_id,
               color_value,
               color_id,
               color_name,
               size_id,
               size_name,
               quantity,
               price,
               design_front_url,
               variant_front_url,
               design_back_url,
               variant_back_url,
               variant_name,
               unit_amount
				});
        List<Map> result = new ArrayList<>();
        for (Map a : resultData) {
            a = format(a);
            result.add(a);
        }
        return result;
    }



    public static Map format(Map queryData) {
        Map resultMap = new LinkedHashMap<>();
        Map product = new LinkedHashMap<>();
        Map design = new LinkedHashMap<>();
        Map shipping = new LinkedHashMap<>();
        Map address = new LinkedHashMap<>();
        resultMap.put(AppParams.SOURCE, ParamUtil.getString(queryData, AppParams.S_SOURCE));
        resultMap.put(AppParams.CURRENCY, ParamUtil.getString(queryData, AppParams.S_CURRENCY));
        resultMap.put(AppParams.NOTE, ParamUtil.getString(queryData, AppParams.S_NOTE));
        resultMap.put(AppParams.STORE_ID, ParamUtil.getString(queryData, AppParams.S_STORE_ID));
        resultMap.put(AppParams.REFERENCE_ID, ParamUtil.getString(queryData, AppParams.S_REFERENCE_ID));
        resultMap.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
        resultMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(queryData, AppParams.S_SHIPPING_METHOD));
        resultMap.put(AppParams.EXTRA_FEE, ParamUtil.getString(queryData, AppParams.S_EXTRA_FEE));
        resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(queryData, AppParams.S_TAX_AMOUNT));
        resultMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(queryData, AppParams.S_IOSS_NUMBER));
        //product
        product.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
        product.put(AppParams.BASE_ID, ParamUtil.getString(queryData, AppParams.S_BASE_ID));
        product.put(AppParams.BASE_NAME, ParamUtil.getString(queryData, AppParams.S_BASE_NAME));
        product.put(AppParams.COLOR, ParamUtil.getString(queryData, AppParams.S_COLOR));
        product.put(AppParams.COLOR_ID, ParamUtil.getString(queryData, AppParams.S_COLOR_ID));
        product.put(AppParams.COLOR_NAME, ParamUtil.getString(queryData, AppParams.S_COLOR_NAME));
        product.put(AppParams.SIZE_ID, ParamUtil.getString(queryData, AppParams.S_SIZE_ID));
        product.put(AppParams.SIZE_NAME, ParamUtil.getString(queryData, AppParams.S_SIZE_NAME));
        product.put(AppParams.QUANTITY, ParamUtil.getString(queryData, AppParams.S_QUANTITY));
        product.put(AppParams.PRICE, ParamUtil.getString(queryData, AppParams.S_PRICE));
        product.put(AppParams.VARIANT_NAME, ParamUtil.getString(queryData, AppParams.S_VARIANT_NAME));
        product.put(AppParams.UNIT_AMOUNT, ParamUtil.getString(queryData, AppParams.S_UNIT_AMOUNT));
        //design
        design.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(queryData, AppParams.S_DESIGN_FRONT_URL));
        design.put(AppParams.DESIGN_FRONT_URL_MD5, ParamUtil.getString(queryData, AppParams.S_VARIANT_FRONT_URL));
        design.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(queryData, AppParams.S_DESIGN_BACK_URL));
        design.put(AppParams.DESIGN_BACK_URL_MD5, ParamUtil.getString(queryData, AppParams.S_VARIANT_BACK_URL));
        //shipping
        shipping.put(AppParams.EMAIL, ParamUtil.getString(queryData, AppParams.S_EMAIL));
        shipping.put(AppParams.NAME, ParamUtil.getString(queryData, AppParams.S_NAME));
        shipping.put(AppParams.PHONE, ParamUtil.getString(queryData, AppParams.S_PHONE));
        //address
        address.put(AppParams.LINE1, ParamUtil.getString(queryData, AppParams.S_LINE1));
        address.put(AppParams.LINE2, ParamUtil.getString(queryData, AppParams.S_LINE2));
        address.put(AppParams.CITY, ParamUtil.getString(queryData, AppParams.S_CITY));
        address.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
        address.put(AppParams.POSTAL_CODE, ParamUtil.getString(queryData, AppParams.S_POSTAL_CODE));
        address.put(AppParams.COUNTRY, ParamUtil.getString(queryData, AppParams.S_COUNTRY));
        address.put(AppParams.COUNTRY_NAME, ParamUtil.getString(queryData, AppParams.S_COUNTRY_NAME));
        address.put(AppParams.ADDR_VERIFIED, ParamUtil.getString(queryData, AppParams.N_ADDR_VERIFIED));
        address.put(AppParams.ADDR_VERIFIED_NOTE, ParamUtil.getString(queryData, AppParams.S_ADDR_VERIFIED_NOTE));

        resultMap.put(AppParams.PRODUCT, product);
        product.put(AppParams.DESIGN, design);
        shipping.put(AppParams.ADDRESS, address);
        resultMap.put(AppParams.SHIPPING, shipping);
        return resultMap;
    }
}

