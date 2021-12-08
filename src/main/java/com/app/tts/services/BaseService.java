package com.app.tts.services;

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
import com.app.tts.util.DBProcedureUtil;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import oracle.jdbc.OracleTypes;

public class BaseService extends MasterService {

	public static final String GET_ALL_BASE = "{call PKG_TTS_TRUONG.getBase(?, ?, ?)}";
	public static final String GET_BASE = "{call PKG_TTS_TRUONG.getBase1(?,?,?)}";

	public static List<Map> getBase() throws SQLException {
		List<Map> resultDataList = excuteQuery(GET_BASE, new Object[] {});
		List<Map> result = new ArrayList();
		for(Map m : resultDataList) {
			m = formatBase(m);
			result.add(m);
		}
		LOGGER.info("result "+result);
		return result;
	}

	public static List<Map> getListBases() throws SQLException {

		List<Map> result = new ArrayList();
		List<Map> resultDataList = excuteQuery(GET_ALL_BASE, new Object[] {});
		for (Map b : resultDataList) {
			b = format(b);
			result.add(b);
		}
		return result;
	}

	public static Map format(Map queryData) {
		Map resultMap = new LinkedHashMap<>();

		Map printTable = new LinkedHashMap<>();
		Map image = new LinkedHashMap<>();

		resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_BASE_ID));
		resultMap.put(AppParams.TYPE_ID, ParamUtil.getString(queryData, AppParams.S_TYPE_ID));
		resultMap.put(AppParams.NAME, ParamUtil.getString(queryData, "S_BASE_NAME"));
		resultMap.put(AppParams.GROUP_ID, ParamUtil.getString(queryData, AppParams.S_GROUP_ID));
		resultMap.put(AppParams.GROUP_NAME, ParamUtil.getString(queryData, AppParams.S_GROUP_NAME));

//        resultMap.put(AppParams.RESOLUTION_REQUIRE, ParamUtil.getString(queryData, AppParams.S_RESOLUTION_REQUIRE));

		// printable
		printTable.put("front_top", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_TOP));
		printTable.put("front_left", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_LEFT));
		printTable.put("front_width", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_WIDTH));
		printTable.put("front_height", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_HEIGHT));
		printTable.put("back_top", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_TOP));
		printTable.put("back_left", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_LEFT));
		printTable.put("back_width", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_WIDTH));
		printTable.put("back_height", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_HEIGHT));
		// image
		image.put("icon_url", ParamUtil.getString(queryData, AppParams.S_ICON_IMG_URL));
		image.put("front_url", ParamUtil.getString(queryData, AppParams.S_FRONT_IMG_URL));
		image.put("front_width", ParamUtil.getString(queryData, AppParams.S_FRONT_IMG_WIDTH));
		image.put("front_height", ParamUtil.getString(queryData, AppParams.S_FRONT_IMG_HEIGHT));
		image.put("back_url", ParamUtil.getString(queryData, AppParams.S_BACK_IMG_URL));
		image.put("back_width", ParamUtil.getString(queryData, AppParams.S_BACK_IMG_WIDTH));
		image.put("back_height", ParamUtil.getString(queryData, AppParams.S_BACK_IMG_HEIGHT));

		resultMap.put(AppParams.SIZES, ParamUtil.getString(queryData, AppParams.S_SIZES));
		resultMap.put(AppParams.COLORS, ParamUtil.getString(queryData, AppParams.S_COLORS));

		resultMap.put(AppParams.IMAGE, image);
		resultMap.put(AppParams.PRINTABLE, printTable);

		return resultMap;
	}

	public static Map formatBase(Map queryData) {
		Map resultMap = new LinkedHashMap();

		resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_BASE_ID));
		resultMap.put(AppParams.NAME, ParamUtil.getString(queryData, AppParams.S_BASE_NAME));
		
		Map typeMap = new LinkedHashMap();
		typeMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_TYPE_ID));
		typeMap.put(AppParams.NAME, ParamUtil.getString(queryData, AppParams.S_TYPE_NAME));
		
		resultMap.put(AppParams.TYPE, typeMap);
		
//		Map groupMap = new LinkedHashMap();
//		groupMap.put(AppParams.GROUP_ID, ParamUtil.getString(queryData, AppParams.S_GROUP_ID));
//		groupMap.put(AppParams.GROUP_NAME, ParamUtil.getString(queryData, AppParams.S_GROUP_NAME));
//		
//		resultMap.put(AppParams.TYPE, groupMap);
		resultMap.put(AppParams.DISPLAY_NAME, ParamUtil.getString(queryData, AppParams.S_DISPLAY_NAME));
		resultMap.put(AppParams.DESC, ParamUtil.getString(queryData, AppParams.S_DESC));
		resultMap.put(AppParams.CURRENCY, ParamUtil.getString(queryData, AppParams.S_CURRENCY));
		resultMap.put(AppParams.DESIGN_GROUP, ParamUtil.getString(queryData, AppParams.S_DESIGN_GROUP));
		resultMap.put("size_price_editable", ParamUtil.getString(queryData, "N_EDIT_SIZE_PRICE"));

		Map dimensionMap = new LinkedHashMap();
		dimensionMap.put("width", ParamUtil.getString(queryData, "S_DIMENSION_WIDTH"));
		dimensionMap.put("height", ParamUtil.getString(queryData, "S_DIMENSION_HEIGHT"));
		
		resultMap.put("dimension", dimensionMap);
		resultMap.put(AppParams.POSITION, ParamUtil.getString(queryData, AppParams.N_POSITION));
		resultMap.put(AppParams.DESIGN_TYPE, ParamUtil.getString(queryData, AppParams.S_DESIGN_TYPE));
		
		Map printTable = new LinkedHashMap<>();
		Map image = new LinkedHashMap<>();
		printTable.put("front_top", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_TOP));
		printTable.put("front_left", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_LEFT));
		printTable.put("front_width", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_WIDTH));
		printTable.put("front_height", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_HEIGHT));
		printTable.put("back_top", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_TOP));
		printTable.put("back_left", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_LEFT));
		printTable.put("back_width", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_WIDTH));
		printTable.put("back_height", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_HEIGHT));
		// image
		image.put("icon_url", ParamUtil.getString(queryData, AppParams.S_ICON_IMG_URL));
		image.put("front_url", ParamUtil.getString(queryData, AppParams.S_FRONT_IMG_URL));
		image.put("front_width", ParamUtil.getString(queryData, AppParams.S_FRONT_IMG_WIDTH));
		image.put("front_height", ParamUtil.getString(queryData, AppParams.S_FRONT_IMG_HEIGHT));
		image.put("back_url", ParamUtil.getString(queryData, AppParams.S_BACK_IMG_URL));
		image.put("back_width", ParamUtil.getString(queryData, AppParams.S_BACK_IMG_WIDTH));
		image.put("back_height", ParamUtil.getString(queryData, AppParams.S_BACK_IMG_HEIGHT));

		resultMap.put(AppParams.SIZES, ParamUtil.getString(queryData, AppParams.S_SIZES));
		resultMap.put(AppParams.COLORS, ParamUtil.getString(queryData, AppParams.S_COLORS));

		resultMap.put(AppParams.IMAGE, image);
		resultMap.put(AppParams.PRINTABLE, printTable);
		resultMap.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
		resultMap.put(AppParams.SHIPPING_COST_US, ParamUtil.getString(queryData, AppParams.S_SHIPPING_COST_US));
		resultMap.put(AppParams.SHIPPING_COST_WW, ParamUtil.getString(queryData, AppParams.S_SHIPPING_COST_WW));
		resultMap.put(AppParams.SHIPPING_LINES, ParamUtil.getString(queryData, AppParams.S_SHIPPING_LINES));
		resultMap.put(AppParams.SHIPPING_TIME_US, ParamUtil.getString(queryData, AppParams.S_SHIPPING_TIME_US));
		resultMap.put(AppParams.SHIPPING_TIME_WW, ParamUtil.getString(queryData, AppParams.S_SHIPPING_TIME_WW));
		
		resultMap.put(AppParams.GROUP_ID, ParamUtil.getString(queryData, AppParams.S_GROUP_ID));
		resultMap.put(AppParams.GROUP_NAME, ParamUtil.getString(queryData, AppParams.S_GROUP_NAME));
		return resultMap;
	}

	private static final Logger LOGGER = Logger.getLogger(BaseService.class.getName());
}
