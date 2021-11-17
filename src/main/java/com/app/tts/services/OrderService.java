package com.app.tts.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderService extends MasterService{
    private static final String GET_ORDER_BY_ID = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER_BY_ID(?,?,?,?)}";
    private static final String GET_ORDER_PRODUCT = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER_PRODUCT(?,?,?)}";
    private static final String GET_ORDER = "{call PKG_DROPSHIP_ORDER_PHUONG.GET_ORDER(?,?,?)}";

    public static List<Map> getOrderProduct() throws SQLException{
        List<Map> result = excuteQuery(GET_ORDER_PRODUCT, new Object[]{});
        return result;
    }

    public static List<Map> getOrder() throws SQLException{
        List<Map> result = excuteQuery(GET_ORDER, new Object[]{});
        return result;
    }

    public static Map getOrderById(String id) throws SQLException{
        List<Map> result = excuteQuery(GET_ORDER_BY_ID, new Object[]{id});
        Map resultMap = result.get(0);
        return resultMap;
    }
}
