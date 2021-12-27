package com.app.tts.services;

import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.error.exception.OracleException;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class DropShipOrderService extends MasterService {

	public static final String GET_ALL_ORDER = "{call PKG_TTS_TRUONG.getAllOrder(?, ?, ?)}";
	public static final String GET_ORDER_BY_ID = "{call PKG_TTS_TRUONG.getOrderById(?, ?, ?, ?)}";
	public static final String UPDATE_DSORDER = "{call PKG_TTS_TRUONG.updateOrder(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static final String INSERT_ORDER = "{call PKG_TTS_TRUONG.insertOrder(?,?,?,?,?,?,?,?,?,?,?)}";

	public static Map insertOrder(String id, String currency, String shipping_id, String store_id, String user_id,
			String refernce_order, String source, String shipping_method) throws SQLException {
		return excuteQuery1(INSERT_ORDER,
				new Object[] { id, currency, shipping_id, store_id, user_id, refernce_order, source, shipping_method });
	}

	public static Map getAllOrder() throws SQLException {
		return excuteQuery1(GET_ALL_ORDER, new Object[] {});
	}

	public static List<Map> getOrderById(String id) throws SQLException {
		List<Map> result = new ArrayList();
		for (Map b : excuteQuery(GET_ORDER_BY_ID, new Object[] { id })) {
			b = format(b);
			result.add(b);
		}
		return result;
	}

	public static Map updateOrder(String id, String currency, String sub_amount, String shipping_fee, String tax_amount,
			String state, Date update_date, String tracking_code, String note, String channel, String user_id,
			String store_id, String shipping_id, String original_id, String source, String shipping_method,
			String ioss_number, String extra_fee, String amount, int addr_verified, String addr_verified_note)
			throws SQLException, OracleException {

		Map resultMap = new HashMap<>();
		Map resultDataList = searchOne(UPDATE_DSORDER,
				new Object[] { id, currency, sub_amount, shipping_fee, tax_amount, state, update_date, tracking_code,
						note, channel, user_id, store_id, shipping_id, original_id, source, shipping_method,
						ioss_number, extra_fee, amount, addr_verified, addr_verified_note });
		return resultDataList;

	}

	private static Map format(Map queryData) throws SQLException {

		Map resultMap = new LinkedHashMap<>();
		resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
		resultMap.put(AppParams.CURRENCY, ParamUtil.getString(queryData, AppParams.S_CURRENCY));
		resultMap.put(AppParams.SUB_AMOUNT, ParamUtil.getString(queryData, AppParams.S_SUB_AMOUNT));
		resultMap.put(AppParams.SHIPPING_FEE, ParamUtil.getString(queryData, AppParams.S_SHIPPING_FEE));
		resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(queryData, AppParams.S_TAX_AMOUNT));
		resultMap.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
		resultMap.put(AppParams.CREATE, ParamUtil.getString(queryData, AppParams.D_CREATE));
		resultMap.put(AppParams.UPDATE, ParamUtil.getString(queryData, AppParams.D_UPDATE));
		resultMap.put(AppParams.TRACKING_CODE, ParamUtil.getString(queryData, AppParams.S_TRACKING_CODE));
		resultMap.put(AppParams.ORDER, ParamUtil.getString(queryData, AppParams.D_ORDER));
		resultMap.put(AppParams.NOTE, ParamUtil.getString(queryData, AppParams.S_NOTE));
		resultMap.put(AppParams.CHANNEL, ParamUtil.getString(queryData, AppParams.S_CHANNEL));
		resultMap.put(AppParams.USER_ID, ParamUtil.getString(queryData, AppParams.S_USER_ID));
		resultMap.put(AppParams.STORE_ID, ParamUtil.getString(queryData, AppParams.S_STORE_ID));
		resultMap.put(AppParams.SHIPPING_ID, ParamUtil.getString(queryData, AppParams.S_SHIPPING_ID));
		resultMap.put(AppParams.ORIGINAL_ID, ParamUtil.getString(queryData, AppParams.S_ORIGINAL_ID));
		resultMap.put(AppParams.SOURCE, ParamUtil.getString(queryData, AppParams.S_SOURCE));
		resultMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(queryData, AppParams.S_SHIPPING_METHOD));
		resultMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(queryData, AppParams.S_IOSS_NUMBER));
		resultMap.put(AppParams.SHIPPING, ParamUtil.getString(queryData, AppParams.SHIPPING));
		resultMap.put(AppParams.EXTRA_FEE, ParamUtil.getString(queryData, AppParams.S_EXTRA_FEE));
		resultMap.put(AppParams.AMOUNT, ParamUtil.getString(queryData, AppParams.S_AMOUNT));
		resultMap.put(AppParams.ADDR_VERIFIED, ParamUtil.getInt(queryData, AppParams.N_ADDR_VERIFIED));
		resultMap.put(AppParams.ADDR_VERIFIED_NOTE, ParamUtil.getString(queryData, AppParams.S_ADDR_VERIFIED_NOTE));
		resultMap.put(AppParams.ITEMS, ParamUtil.getString(queryData, "item"));

		return resultMap;
	}

	public static Map formatUpdateOrder(Map orderInput, Map shippingInput, List<Map> productList) {
		Map resultMap = new LinkedHashMap();

		resultMap.put(AppParams.ID, ParamUtil.getString(orderInput, AppParams.S_ID));
		resultMap.put(AppParams.CURRENCY, ParamUtil.getString(orderInput, AppParams.S_CURRENCY));
		resultMap.put(AppParams.SUB_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_SUB_AMOUNT));
		resultMap.put(AppParams.SHIPPING_FEE, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_FEE));
		resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_TAX_AMOUNT));
		resultMap.put(AppParams.STATE, ParamUtil.getString(orderInput, AppParams.S_STATE));
		resultMap.put(AppParams.UPDATE, ParamUtil.getString(orderInput, AppParams.D_UPDATE));
		resultMap.put(AppParams.TRACKING_CODE, ParamUtil.getString(orderInput, AppParams.S_TRACKING_CODE));
		resultMap.put(AppParams.ORDER, ParamUtil.getString(orderInput, AppParams.D_ORDER));
		resultMap.put(AppParams.NOTE, ParamUtil.getString(orderInput, AppParams.S_NOTE));
		resultMap.put(AppParams.CHANNEL, ParamUtil.getString(orderInput, AppParams.S_CHANNEL));
		resultMap.put(AppParams.USER_ID, ParamUtil.getString(orderInput, AppParams.S_USER_ID));
		resultMap.put(AppParams.STORE_ID, ParamUtil.getString(orderInput, AppParams.S_STORE_ID));
		resultMap.put(AppParams.SHIPPING_ID, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_ID));
		resultMap.put(AppParams.ORIGINAL_ID, ParamUtil.getString(orderInput, AppParams.S_ORIGINAL_ID));
		resultMap.put(AppParams.SOURCE, ParamUtil.getString(orderInput, AppParams.S_SOURCE));
		resultMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_METHOD));
		resultMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));
		resultMap.put(AppParams.SHIPPING, ParamUtil.getString(orderInput, AppParams.SHIPPING));
		resultMap.put(AppParams.EXTRA_FEE, ParamUtil.getString(orderInput, AppParams.S_EXTRA_FEE));
		resultMap.put(AppParams.AMOUNT, ParamUtil.getString(orderInput, AppParams.S_AMOUNT));
//		boolean Unfulfilled = true, fulfilled = false;
//
//		resultMap.put("fulfill_state",
//				ParamUtil.getBoolean(orderInput, AppParams.N_FULFILLED_ITEM) ? Unfulfilled : fulfilled);

//        Map getshipping = shippingInput.get(0);
		Map shippingMap = new LinkedHashMap();
		Map addressMap = new LinkedHashMap<>();
		shippingMap.put(AppParams.ID, ParamUtil.getString(shippingInput, AppParams.S_ID));
		shippingMap.put(AppParams.NAME, ParamUtil.getString(shippingInput, AppParams.S_NAME));
		shippingMap.put(AppParams.EMAIL, ParamUtil.getString(shippingInput, AppParams.S_EMAIL));
		shippingMap.put(AppParams.PHONE, ParamUtil.getString(shippingInput, AppParams.S_PHONE));
		shippingMap.put(AppParams.GIFT, ParamUtil.getBoolean(shippingInput, AppParams.N_GIFT));

		addressMap.put(AppParams.LINE1, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE1));
		addressMap.put(AppParams.LINE2, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE2));
		addressMap.put(AppParams.CITY, ParamUtil.getString(shippingInput, AppParams.S_ADD_CITY));
		addressMap.put(AppParams.STATE, ParamUtil.getString(shippingInput, AppParams.S_STATE));
		addressMap.put(AppParams.POSTAL_CODE, ParamUtil.getString(shippingInput, AppParams.S_POSTAL_CODE));
		addressMap.put(AppParams.COUNTRY, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_CODE));
		addressMap.put(AppParams.COUNTRY_NAME, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_NAME));

		shippingMap.put(AppParams.ADDRESS, addressMap);
		resultMap.put(AppParams.SHIPPING, shippingMap);
		resultMap.put(AppParams.EXTRA_FEE_2, ParamUtil.getString(orderInput, AppParams.S_EXTRA_FEE));
		resultMap.put(AppParams.UNIT_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_AMOUNT));

		List<Map> itemsList = new LinkedList<>();
		for (Map productInput : productList) {
			Map productMap = new LinkedHashMap();
			productMap.put(AppParams.ID, ParamUtil.getString(productInput, AppParams.S_ID));

			Map baseMap = new LinkedHashMap();
			baseMap.put(AppParams.BASE_ID, ParamUtil.getString(productInput, AppParams.S_BASE_ID));
			baseMap.put(AppParams.BASE_NAME, ParamUtil.getString(productInput, AppParams.S_BASE_NAME));
			baseMap.put(AppParams.TYPE_ID, ParamUtil.getString(productInput, AppParams.S_TYPE_ID));
			baseMap.put(AppParams.TYPE_NAME, ParamUtil.getString(productInput, AppParams.S_TYPE_NAME));

			Map groupMap = new LinkedHashMap();
			groupMap.put(AppParams.GROUP_ID, ParamUtil.getString(productInput, AppParams.S_GROUP_ID));
			groupMap.put(AppParams.GROUP_NAME, ParamUtil.getString(productInput, AppParams.S_GROUP_NAME));

			baseMap.put("group", groupMap);
			productMap.put("bases", baseMap);

			Map campaignMap = new LinkedHashMap();
			campaignMap.put(AppParams.ID, ParamUtil.getString(productInput, AppParams.S_CAMPAIGN_ID));
			campaignMap.put(AppParams.TITLE, ParamUtil.getString(productInput, AppParams.S_TITLE));
			campaignMap.put(AppParams.DOMAIN, ParamUtil.getString(productInput, AppParams.S_DOMAIN));
			campaignMap.put(AppParams.DOMAIN_ID, ParamUtil.getString(productInput, AppParams.S_DOMAIN_ID));
			productMap.put("campaigns", campaignMap);

			productMap.put(AppParams.COLOR_VALUE, ParamUtil.getString(productInput, AppParams.S_COLOR_VALUE));
			productMap.put(AppParams.COLOR_ID, ParamUtil.getString(productInput, AppParams.S_COLOR_ID));
			productMap.put(AppParams.COLOR_NAME, ParamUtil.getString(productInput, AppParams.S_COLOR_NAME));
			productMap.put(AppParams.SIZE_ID, ParamUtil.getString(productInput, AppParams.S_SIZE_ID));
			productMap.put(AppParams.SIZE_NAME, ParamUtil.getString(productInput, AppParams.S_SIZE_NAME));
			productMap.put(AppParams.CUSTOM_DATA, ParamUtil.getString(productInput, AppParams.S_CUSTOM_DATA));
			productMap.put(AppParams.QUANTITY, ParamUtil.getString(productInput, AppParams.N_QUANTITY));
//			productMap.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(productInput, AppParams.S_CAMPAIGN_ID));

			Map designsMap = new LinkedHashMap();
			designsMap.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(productInput, AppParams.S_DESIGN_FRONT_URL));
			designsMap.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(productInput, AppParams.S_DESIGN_BACK_URL));
			designsMap.put("mockup_front_url", ParamUtil.getString(productInput, AppParams.S_VARIANT_FRONT_URL));
			designsMap.put("mockup_back_url", ParamUtil.getString(productInput, AppParams.S_VARIANT_BACK_URL));
			productMap.put(AppParams.DESIGNS, designsMap);

			productMap.put(AppParams.VARIANT_ID, ParamUtil.getString(productInput, AppParams.S_VARIANT_ID));
			productMap.put(AppParams.PRODUCT_ID, ParamUtil.getString(productMap, AppParams.S_PRODUCT_ID));

			itemsList.add(productMap);
		}

		resultMap.put(AppParams.ITEMS, itemsList);

		resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_TAX_AMOUNT));
		resultMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));

		return resultMap;
	}

	public static Map formatOrder(Map orderInput, Map shippingInput, List<Map> productList) {
		Map resultMap = new LinkedHashMap();

		resultMap.put(AppParams.ID, ParamUtil.getString(orderInput, AppParams.S_ORDER_ID));
		resultMap.put(AppParams.CURRENCY, ParamUtil.getString(orderInput, AppParams.S_CURRENCY));
		resultMap.put(AppParams.SUB_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_SUB_AMOUNT));
		resultMap.put(AppParams.SHIPPING_FEE, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_FEE));
		resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_TAX_AMOUNT));
		resultMap.put(AppParams.STATE, ParamUtil.getString(orderInput, AppParams.S_STATE));
		resultMap.put(AppParams.UPDATE, ParamUtil.getString(orderInput, AppParams.D_UPDATE));
		resultMap.put(AppParams.TRACKING_CODE, ParamUtil.getString(orderInput, AppParams.S_TRACKING_CODE));
		resultMap.put(AppParams.ORDER, ParamUtil.getString(orderInput, AppParams.D_ORDER));
		resultMap.put(AppParams.NOTE, ParamUtil.getString(orderInput, AppParams.S_NOTE));
		resultMap.put(AppParams.CHANNEL, ParamUtil.getString(orderInput, AppParams.S_CHANNEL));
		resultMap.put(AppParams.USER_ID, ParamUtil.getString(orderInput, AppParams.S_USER_ID));
		resultMap.put(AppParams.STORE_ID, ParamUtil.getString(orderInput, AppParams.S_STORE_ID));
		resultMap.put(AppParams.SHIPPING_ID, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_ID));
		resultMap.put(AppParams.ORIGINAL_ID, ParamUtil.getString(orderInput, AppParams.S_ORIGINAL_ID));
		resultMap.put(AppParams.SOURCE, ParamUtil.getString(orderInput, AppParams.S_SOURCE));
		resultMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(orderInput, AppParams.S_SHIPPING_METHOD));
		resultMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));
		resultMap.put(AppParams.SHIPPING, ParamUtil.getString(orderInput, AppParams.SHIPPING));
		resultMap.put(AppParams.EXTRA_FEE, ParamUtil.getString(orderInput, AppParams.S_EXTRA_FEE));
		resultMap.put(AppParams.AMOUNT, ParamUtil.getString(orderInput, AppParams.S_AMOUNT));
//		boolean Unfulfilled = true, fulfilled = false;
//
//		resultMap.put("fulfill_state",
//				ParamUtil.getBoolean(orderInput, AppParams.N_FULFILLED_ITEM) ? Unfulfilled : fulfilled);

//        Map getshipping = shippingInput.get(0);
		Map shippingMap = new LinkedHashMap();
		Map addressMap = new LinkedHashMap<>();
		shippingMap.put(AppParams.ID, ParamUtil.getString(shippingInput, AppParams.S_ID));
		shippingMap.put(AppParams.NAME, ParamUtil.getString(shippingInput, AppParams.S_NAME));
		shippingMap.put(AppParams.EMAIL, ParamUtil.getString(shippingInput, AppParams.S_EMAIL));
		shippingMap.put(AppParams.PHONE, ParamUtil.getString(shippingInput, AppParams.S_PHONE));
		shippingMap.put(AppParams.GIFT, ParamUtil.getBoolean(shippingInput, AppParams.N_GIFT));

		addressMap.put(AppParams.LINE1, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE1));
		addressMap.put(AppParams.LINE2, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE2));
		addressMap.put(AppParams.CITY, ParamUtil.getString(shippingInput, AppParams.S_ADD_CITY));
		addressMap.put(AppParams.STATE, ParamUtil.getString(shippingInput, AppParams.S_STATE));
		addressMap.put(AppParams.POSTAL_CODE, ParamUtil.getString(shippingInput, AppParams.S_POSTAL_CODE));
		addressMap.put(AppParams.COUNTRY, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_CODE));
		addressMap.put(AppParams.COUNTRY_NAME, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_NAME));

		shippingMap.put(AppParams.ADDRESS, addressMap);
		resultMap.put(AppParams.SHIPPING, shippingMap);
		resultMap.put(AppParams.EXTRA_FEE_2, ParamUtil.getString(orderInput, AppParams.S_EXTRA_FEE));
		resultMap.put(AppParams.UNIT_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_AMOUNT));

		List<Map> itemsList = new LinkedList<>();
		for (Map productInput : productList) {
			Map productMap = new LinkedHashMap();
			productMap.put(AppParams.ID, ParamUtil.getString(productInput, AppParams.S_ID));

			Map baseMap = new LinkedHashMap();
			baseMap.put(AppParams.BASE_ID, ParamUtil.getString(productInput, AppParams.S_BASE_ID));
			baseMap.put(AppParams.BASE_NAME, ParamUtil.getString(productInput, AppParams.S_BASE_NAME));
			baseMap.put(AppParams.TYPE_ID, ParamUtil.getString(productInput, AppParams.S_TYPE_ID));
			baseMap.put(AppParams.TYPE_NAME, ParamUtil.getString(productInput, AppParams.S_TYPE_NAME));

			Map groupMap = new LinkedHashMap();
			groupMap.put(AppParams.GROUP_ID, ParamUtil.getString(productInput, AppParams.S_GROUP_ID));
			groupMap.put(AppParams.GROUP_NAME, ParamUtil.getString(productInput, AppParams.S_GROUP_NAME));

			baseMap.put("group", groupMap);
			productMap.put("bases", baseMap);

			Map campaignMap = new LinkedHashMap();
			campaignMap.put(AppParams.ID, ParamUtil.getString(productInput, AppParams.S_CAMPAIGN_ID));
			campaignMap.put(AppParams.TITLE, ParamUtil.getString(productInput, AppParams.S_TITLE));
			campaignMap.put(AppParams.DOMAIN, ParamUtil.getString(productInput, AppParams.S_DOMAIN));
			campaignMap.put(AppParams.DOMAIN_ID, ParamUtil.getString(productInput, AppParams.S_DOMAIN_ID));
			productMap.put("campaigns", campaignMap);

			productMap.put(AppParams.COLOR_VALUE, ParamUtil.getString(productInput, AppParams.S_COLOR_VALUE));
			productMap.put(AppParams.COLOR_ID, ParamUtil.getString(productInput, AppParams.S_COLOR_ID));
			productMap.put(AppParams.COLOR_NAME, ParamUtil.getString(productInput, AppParams.S_COLOR_NAME));
			productMap.put(AppParams.SIZE_ID, ParamUtil.getString(productInput, AppParams.S_SIZE_ID));
			productMap.put(AppParams.SIZE_NAME, ParamUtil.getString(productInput, AppParams.S_SIZE_NAME));
			productMap.put(AppParams.CUSTOM_DATA, ParamUtil.getString(productInput, AppParams.S_CUSTOM_DATA));
			productMap.put(AppParams.QUANTITY, ParamUtil.getString(productInput, AppParams.N_QUANTITY));
//			productMap.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(productInput, AppParams.S_CAMPAIGN_ID));

			Map designsMap = new LinkedHashMap();
			designsMap.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(productInput, AppParams.S_DESIGN_FRONT_URL));
			designsMap.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(productInput, AppParams.S_DESIGN_BACK_URL));
			designsMap.put("mockup_front_url", ParamUtil.getString(productInput, AppParams.S_VARIANT_FRONT_URL));
			designsMap.put("mockup_back_url", ParamUtil.getString(productInput, AppParams.S_VARIANT_BACK_URL));
			productMap.put(AppParams.DESIGNS, designsMap);

			productMap.put(AppParams.VARIANT_ID, ParamUtil.getString(productInput, AppParams.S_VARIANT_ID));
			productMap.put(AppParams.PRODUCT_ID, ParamUtil.getString(productMap, AppParams.S_PRODUCT_ID));

			itemsList.add(productMap);
		}

		resultMap.put(AppParams.ITEMS, itemsList);

		resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_TAX_AMOUNT));
		resultMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));

		return resultMap;
	}

	private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
}
