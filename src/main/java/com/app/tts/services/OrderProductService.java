package com.app.tts.services;

import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class OrderProductService extends MasterService {

	public static final String GET_ALL_PRODUCT = "{call PKG_TTS_TRUONG.getAllProduct(?,?,?)}";
	public static final String GET_ORDER_PRODUCT_BY_ID = "{call PKG_TTS_TRUONG.getOrderProductById(?,?,?,?)}";
	public static final String UPDATE_ORDER_PRODUCT = "{call PKG_TTS_TRUONG.updateOrderProduct(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static final String INSERT_ORDER_PRODUCT = "{call PKG_TTS_TRUONG.insertOrderProduct(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	public static List<Map> getAllOrderProduct() throws SQLException {
		LOGGER.info("0 : " + excuteQuery(GET_ALL_PRODUCT, new Object[] {}));
		LOGGER.info("1 : " + excuteQuery1(GET_ALL_PRODUCT, new Object[] {}));
		return excuteQuery(GET_ALL_PRODUCT, new Object[] {});
	}

	public static List<Map> getOrderProductById(String id) throws SQLException {
		List<Map> result = new ArrayList();
		for (Map b : excuteQuery(GET_ORDER_PRODUCT_BY_ID, new Object[] { id })) {
			b = format(b);
			result.add(b);
		}
		return result;
	}

	public static Map insertOrderProduct(String id, String order_id, String campaign_id, String variant_id,
			String size_id, String price, String shipping_fee, String currency, int quantity, /*String state,*/
			String variant_name, /*String amount, int status, String base_cost,*/ String base_id,
			/*String line_item_id, String refunded_item,*/ String variant_front_url, String variant_back_url,
			String color_id, String color_value, /*String partner_sku,*/ String color_name, String size_name,
			String shipping_method, String unit_amount, /*int refund_item,*/ String design_back_url,
			String design_front_url) throws SQLException {
		Map result = searchOne(INSERT_ORDER_PRODUCT,
					new Object[] { id, order_id, campaign_id, variant_id, size_id, price, shipping_fee, currency,
							quantity/*, state*/, variant_name, /*amount, status, base_cost,*/ base_id, /*line_item_id,
							refunded_item,*/ variant_front_url, variant_back_url, color_id, color_value, /*partner_sku,*/
							color_name, size_name, shipping_method, unit_amount, /*refund_item, */design_back_url,
							design_front_url });
		return result;
	} 

	public static List<Map> updateOrderProduc(String id, String order_id, String base_id, String color_value,
			String color_id, String color_name, String size_id, String size_name, String custom_data, Integer quantity,
			String campaign_id, String design_front_url, String design_back_url, String mockup_front_url,
			String mockup_back_url, String variant_id, String product_id, Date update_date) throws SQLException {

		List<Map> result = new ArrayList();
		List<Map> resultDataList = update(UPDATE_ORDER_PRODUCT,
				new Object[] { id, order_id, base_id, color_value, color_id, color_name, size_id, size_name,
						custom_data, quantity, campaign_id, design_front_url, design_back_url, mockup_front_url,
						mockup_back_url, variant_id, product_id, update_date });
		return resultDataList;
	}

	private static Map format(Map queryData) {
		Map resultMap = new LinkedHashMap<>();
		Map designsMap = new LinkedHashMap<>();
		resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
		resultMap.put(AppParams.BASE_ID, ParamUtil.getString(queryData, AppParams.S_BASE_ID));
		resultMap.put(AppParams.COLOR_VALUE, ParamUtil.getString(queryData, AppParams.S_COLOR_VALUE));
		resultMap.put(AppParams.COLOR_ID, ParamUtil.getString(queryData, AppParams.S_COLOR_ID));
		resultMap.put(AppParams.COLOR_NAME, ParamUtil.getString(queryData, AppParams.S_COLOR_NAME));
		resultMap.put(AppParams.SIZE_ID, ParamUtil.getString(queryData, AppParams.S_SIZE_ID));
		resultMap.put(AppParams.SIZE_NAME, ParamUtil.getString(queryData, AppParams.S_SIZE_NAME));
		resultMap.put(AppParams.CUSTOM_DATA, ParamUtil.getString(queryData, AppParams.S_CUSTOM_DATA));
		resultMap.put(AppParams.QUANTITY, ParamUtil.getString(queryData, AppParams.N_QUANTITY));
		resultMap.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(queryData, AppParams.S_CAMPAIGN_ID));

		designsMap.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(queryData, AppParams.S_DESIGN_FRONT_URL));
		designsMap.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(queryData, AppParams.S_DESIGN_BACK_URL));
		designsMap.put(AppParams.VARIANT_FRONT_URL, ParamUtil.getString(queryData, AppParams.S_VARIANT_FRONT_URL));
		designsMap.put(AppParams.VARIANT_BACK_URL, ParamUtil.getString(queryData, AppParams.S_VARIANT_BACK_URL));
		resultMap.put("designs", designsMap);

		resultMap.put(AppParams.VARIANT_ID, ParamUtil.getString(queryData, AppParams.S_VARIANT_ID));
		resultMap.put(AppParams.PRODUCT_ID, ParamUtil.getString(queryData, AppParams.S_PRODUCT_ID));

		return resultMap;
	}

	private static final Logger LOGGER = Logger.getLogger(OrderProductService.class.getName());
}