package com.app.tts.services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.error.exception.OracleException;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class DropShipOrderService extends MasterService {

	public static final String GET_DSORDER_BY_ID = "{call PKG_TTS_TRUONG.findDSOrderById(?, ?, ?, ?)}";
	public static final String GET_ALL_ORDER = "{call PKG_TTS_TRUONG.getAllOrder(?, ?, ?, ?)}";
	public static final String UPDATE_DSORDER = "{call PKG_TTS_TRUONG.updateOrder(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	public static List<Map> getAllOrder(String id) throws SQLException{
		return searchAll(GET_ALL_ORDER, new Object[] {id});
	}
	public static Map getOrderById(String s_id) throws SQLException {
		return searchOne(GET_DSORDER_BY_ID, new Object[] { s_id });
	}

	public static List<Map> updateOrder(String id, String currency, String sub_amount, String shipping_fee,
			String tax_amount, String state, String tracking_code, String note, String channel, String user_id,
			String store_id, String shipping_id, String original_id, String source, String shipping_method,
			String ioss_number, String extra_fee, String amount, int addr_verified, String addr_verified_note)
			throws SQLException, OracleException {

		Map resultMap = new HashMap<>();
		List<Map> resultDataList = excuteQuery(UPDATE_DSORDER,
				new Object[] { id, currency, sub_amount, shipping_fee, tax_amount, state, tracking_code, note, channel,
						user_id, store_id, shipping_id, original_id, source, shipping_method, ioss_number, extra_fee,
						amount, addr_verified, addr_verified_note });
		System.out.println(" id    "+id);
		LOGGER.info("=> UPDATE BY ID result: " + resultDataList);
		List<Map> result = new ArrayList<>();
        for (Map map : resultDataList) {
            map = format(map);
            result.add(map);
        }
        return result;

	}

	private static Map format(Map queryData) throws SQLException {

		Map resultMap = new LinkedHashMap<>();
		resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
		resultMap.put(AppParams.CURRENCY, ParamUtil.getString(queryData, AppParams.S_CURRENCY));
		resultMap.put(AppParams.SUB_AMOUNT, ParamUtil.getString(queryData, AppParams.S_SUB_AMOUNT));
		resultMap.put(AppParams.SHIPPING_FEE, ParamUtil.getString(queryData, AppParams.S_SHIPPING_FEE));
		resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(queryData, AppParams.S_TAX_AMOUNT));
		resultMap.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
		// resultMap.put(AppParams.UPDATE, ParamUtil.getString(queryData,
		// AppParams.D_UPDATE));
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

	private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
}
