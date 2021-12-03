package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class ColorService extends MasterService{
	
	public static final String GET_COLOR = "{call PKG_TTS_TRUONG.getColorByBaseId(?, ?, ?)}";

	public static List<Map> getListColor() throws SQLException {

		List<Map> result = new ArrayList();
		List<Map> resultDataList = excuteQuery(GET_COLOR, new Object[] {});
		for (Map b : resultDataList) {
			b = format(b);
			result.add(b);
		}
		return result;
	}
	
	private static Map format(Map queryData) {
		Map resultMap = new LinkedHashMap<>();
		resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_COLOR_ID));
		resultMap.put(AppParams.NAME, ParamUtil.getString(queryData, AppParams.S_COLOR_NAME));
		resultMap.put(AppParams.VALUE, ParamUtil.getString(queryData, AppParams.S_COLOR_VALUE));
		resultMap.put(AppParams.POSITION, ParamUtil.getString(queryData, AppParams.N_POSITION));
		resultMap.put("base", ParamUtil.getString(queryData, AppParams.S_BASE_ID));
		return resultMap;
	}
    private static final Logger LOGGER = Logger.getLogger(ColorService.class.getName());
}
