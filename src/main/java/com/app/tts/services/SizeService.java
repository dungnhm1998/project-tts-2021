package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class SizeService extends MasterService{

	public static final String GET_SIZE = "{call PKG_TTS_TRUONG.getSizeByBaseId(?, ?, ?)}";
	
	public static List<Map> getListSize() throws SQLException {

		List<Map> result = new ArrayList();
		List<Map> resultDataList = excuteQuery(GET_SIZE, new Object[] {});
		for (Map b : resultDataList) {
			b = format(b);
			result.add(b);
		}
		return result;
	}
	
	private static Map format(Map queryData) {
		Map resultMap = new LinkedHashMap<>();
		resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_SIZE_ID));
		resultMap.put(AppParams.NAME, ParamUtil.getString(queryData, AppParams.S_SIZE_NAME));
		resultMap.put(AppParams.PRICE, ParamUtil.getString(queryData, AppParams.S_PRICE));
		resultMap.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
		resultMap.put(AppParams.DROPSHIP_PRICE, ParamUtil.getString(queryData, AppParams.S_DROPSHIP_PRICE));
		resultMap.put(AppParams.SECOND_SIDE_PRICE, ParamUtil.getString(queryData, AppParams.S_SECOND_SIDE_PRICE));
		resultMap.put("base", ParamUtil.getString(queryData, AppParams.S_BASE_ID));
		return resultMap;
	}
    private static final Logger LOGGER = Logger.getLogger(SizeService.class.getName());
}

