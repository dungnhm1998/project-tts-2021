package com.app.tts.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddOrderServices extends MasterService {
    private static final String INSERT_ORDER = "{call PKG_QUY.insert_dropship_order(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    private static final String INS_ORDER_PRODUCT = "{call PKG_QUY.insert_dropship_order_product(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    private static final String INS_SHIPPING = "{call PKG_QUY.insert_shipping_shipping(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";


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

        System.out.println(insertOrder);
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
        System.out.println(insertShipping);
        return insertShipping;
    }

    public static List<Map> insertOrderProduct(
            String p_id,
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
        System.out.println(insertOrProduct);
        return insertOrProduct;
    }


    public static Map formatInsertOr() {
        Map resultMap = new LinkedHashMap();


        return resultMap;
    }
}

































