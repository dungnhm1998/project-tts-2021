package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class OrderService extends MasterService{
	public static final String INSERT_DROPSHIP_ORDER = "{call "
			+ "PKG_CREATE_ORDER.insert_dropship_order(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static final String INSERT_DROPSHIP_ORDER_PRODUCT = "{call "
			+ "PKG_CREATE_ORDER.insert_dropship_order_product(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static final String INSERT_TB_SHIPPING = "{call "
			+ "PKG_CREATE_ORDER.insert_tb_shipping(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	public static List<Map> insertDropshipOrder(String source, String currency, String note, String store_id, String reference_id,
			String state, String shipping_method, String addr_verified, String addr_verified_note, String extra_fee) throws SQLException {
		List<Map> resultData = excuteQuery(INSERT_DROPSHIP_ORDER, new Object[] {source, currency, note, store_id, reference_id,
			state, shipping_method, addr_verified, addr_verified_note, extra_fee});
		List<Map> result = new ArrayList<>();
		for (Map a : resultData) {
			a = format(a);
			result.add(a);
		}
		return result;
	}
	public static List<Map> insertDropshipOrderProduct(String id, String base_id, String base_name, String color, String color_id, 
			String color_name, String size_id, String size_name, String quantity, String price, String design_front_url, 
			String design_front_url_md5, String design_back_url, String design_back_url_md5, String variant_name, 
			String unit_amount) throws SQLException {
		List<Map> resultData = excuteQuery(INSERT_DROPSHIP_ORDER_PRODUCT, new Object[] {id, base_id, base_name, color,
				color_id, color_name, size_id, size_name, quantity, price, design_front_url, design_front_url_md5,
				design_back_url, design_back_url_md5, variant_name, unit_amount});
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
		Map shipping = new LinkedHashMap<>();
		resultMap.put(AppParams.SOURCE, ParamUtil.getString(queryData, AppParams.S_SOURCE));
		resultMap.put(AppParams.CURRENCY, ParamUtil.getString(queryData, AppParams.S_CURRENCY));
		resultMap.put(AppParams.NOTE, ParamUtil.getString(queryData, AppParams.S_NOTE));
		resultMap.put(AppParams.STORE_ID, ParamUtil.getString(queryData, AppParams.S_STORE_ID));
		resultMap.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
		resultMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(queryData, AppParams.S_SHIPPING_METHOD));
		resultMap.put(AppParams.EXTRA_FEE, ParamUtil.getString(queryData, AppParams.S_EXTRA_FEE));
		//product
		product.put(AppParams.);
        return resultMap;
	}
}

