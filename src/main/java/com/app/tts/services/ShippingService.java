package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class ShippingService extends MasterService {

	public static final String GET_ALL_SHIPPING = "{call PKG_TTS_TRUONG.getAllShipping(?,?,?)}";
	public static final String GET_SHIPPING_BY_ID = "{call PKG_TTS_TRUONG.getShippingById(?,?,?,?)}";
	public static final String UPDATE_SHIPPING = "{call PKG_TTS_TRUONG.updateShipping(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	public static Map getAllShipping() throws SQLException{
		return excuteQuery1(GET_ALL_SHIPPING, new Object[] {});
	}
	
	public static Map getShippingById(String id) throws SQLException{
		return excuteQuery1(GET_SHIPPING_BY_ID, new Object[] {id});
	}

	public static Map updateShipping(String id, String name, String email, String phone, int gift,
			String line1, String line2, String city, String state, String postal_code, String country_code,
			String country_name) throws SQLException {

		return searchOne(UPDATE_SHIPPING, new Object[] { id, name, email, phone, gift, line1, line2,
				city, state, postal_code, country_code, country_name });
	}

	private static Map format(Map queryData) {
		Map resultMap = new LinkedHashMap<>();
		Map addressMap = new LinkedHashMap<>();
		resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
		resultMap.put(AppParams.NAME, ParamUtil.getString(queryData, AppParams.S_NAME));
		resultMap.put(AppParams.EMAIL, ParamUtil.getString(queryData, AppParams.S_EMAIL));
		resultMap.put(AppParams.PHONE, ParamUtil.getString(queryData, AppParams.S_PHONE));
		resultMap.put(AppParams.GIFT, ParamUtil.getString(queryData, AppParams.N_GIFT));
		
		addressMap.put(AppParams.LINE1, ParamUtil.getString(queryData, AppParams.S_ADD_LINE1));
		addressMap.put(AppParams.LINE2, ParamUtil.getString(queryData, AppParams.S_ADD_LINE2));
		addressMap.put(AppParams.CITY, ParamUtil.getString(queryData, AppParams.S_ADD_CITY));
		addressMap.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
		addressMap.put(AppParams.POSTAL_CODE, ParamUtil.getString(queryData, AppParams.S_POSTAL_CODE));
		addressMap.put(AppParams.COUNTRY, ParamUtil.getString(queryData, AppParams.S_COUNTRY_CODE));
		addressMap.put(AppParams.COUNTRY_NAME, ParamUtil.getString(queryData, AppParams.S_COUNTRY_NAME));
		resultMap.put("address", addressMap);
		return resultMap;
	}

	private static final Logger LOGGER = Logger.getLogger(ShippingService.class.getName());
}
