package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class ListService extends MasterService{
	
	public static final String LIST_BASE_SIZE = "{call PKG_CREATE_ORDER.list_base_size(?,?,?,?)}";
	public static final String LIST_BASE_COLOR = "{call PKG_CREATE_ORDER.list_base_color(?,?,?,?)}";
	public static final String LIST_BASE_GROUP = "{call PKG_CREATE_ORDER.list_base_group(?,?,?,?)}";
	
	public static List<Map> getBaseSize() throws SQLException{
		List<Map> dataResult = excuteQuery(LIST_BASE_SIZE, new Object[] {});
		List<Map> result = new ArrayList();
		LOGGER.info("sizeResult" + dataResult);
		for (Map a : dataResult) {
            a = format1(a);
            result.add(a);
        }
		return result;
	}
	
	public static List<Map> getBaseColor() throws SQLException{
		List<Map> dataResult = excuteQuery(LIST_BASE_COLOR, new Object[] {});
		List<Map> result = new ArrayList();
		LOGGER.info("colorResult" + dataResult);
		for (Map a : dataResult) {
            a = format2(a);
            result.add(a);
        }
		return result;
	}
	
	public static List<Map> getBaseGroup() throws SQLException{
		List<Map> dataResult = excuteQuery(LIST_BASE_GROUP, new Object[] {});
		List<Map> result = new ArrayList();
		LOGGER.info("ResultDataList" + dataResult);
		for (Map a : dataResult) {
            a = format3(a);
            result.add(a);
        }
		return result;
	}
	

	    public static Map format1(Map queryData) {
	        Map resultMap = new LinkedHashMap<>();
	        Map sizes = new LinkedHashMap<>();
	        resultMap.put(AppParams.SIZES, sizes);
	        //sizes
	        sizes.put("id", ParamUtil.getString(queryData, AppParams.SIZE_ID));
	        sizes.put("name", ParamUtil.getString(queryData, AppParams.S_SIZE_NAME));
	        sizes.put("unit", ParamUtil.getString(queryData, AppParams.S_UNIT));
	        return resultMap;

	    }
	    
	    public static Map format2(Map queryData) {
	        Map resultMap = new LinkedHashMap<>();
	        Map colors = new LinkedHashMap<>();
	        resultMap.put(AppParams.COLORS, colors);

	        //colors
	        colors.put("id", ParamUtil.getString(queryData, AppParams.S_COLORS));
	        colors.put("name", ParamUtil.getString(queryData, AppParams.S_NAME_COLOR));
	        colors.put("value", ParamUtil.getString(queryData, AppParams.S_VALUE));
	        colors.put("position", ParamUtil.getString(queryData, AppParams.N_POSITION));
	        return resultMap;

	    }



	    public static Map format3(Map queryData) {
	        Map resultMap = new LinkedHashMap<>();


	        Map printTable = new LinkedHashMap<>();
	        Map image = new LinkedHashMap<>();


	        resultMap.put(AppParams.GROUP_ID, ParamUtil.getString(queryData, AppParams.S_GROUP_ID));
	        resultMap.put(AppParams.GROUP_NAME, ParamUtil.getString(queryData, AppParams.S_GROUP_NAME));
	        resultMap.put(AppParams.TYPE_ID, ParamUtil.getString(queryData, AppParams.S_TYPE_ID));
	        resultMap.put(AppParams.RESOLUTION_REQUIRE, ParamUtil.getString(queryData, AppParams.S_RESOLUTION_REQUIRE));
	        resultMap.put(AppParams.BASE_ID, ParamUtil.getString(queryData, AppParams.S_BASE_ID));


	        //printable
	        printTable.put("front_top", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_TOP));
	        printTable.put("front_left", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_LEFT));
	        printTable.put("front_width", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_WIDTH));
	        printTable.put("front_height", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_HEIGHT));
	        printTable.put("back_top", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_TOP));
	        printTable.put("back_left", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_LEFT));
	        printTable.put("back_width", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_WIDTH));
	        printTable.put("back_height", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_HEIGHT));
	        //image
	        image.put("icon_url", ParamUtil.getString(queryData, AppParams.S_ICON_IMG_URL));
	        image.put("front_url", ParamUtil.getString(queryData, AppParams.S_FRONT_IMG_URL));
	        image.put("front_width", ParamUtil.getString(queryData, AppParams.S_FRONT_IMG_WIDTH));
	        image.put("front_height", ParamUtil.getString(queryData, AppParams.S_FRONT_IMG_HEIGHT));
	        image.put("back_url", ParamUtil.getString(queryData, AppParams.S_BACK_IMG_URL));
	        image.put("back_width", ParamUtil.getString(queryData, AppParams.S_BACK_IMG_WIDTH));
	        image.put("back_height", ParamUtil.getString(queryData, AppParams.S_BACK_IMG_HEIGHT));


	        resultMap.put(AppParams.PRINTABLE, printTable);
	        resultMap.put(AppParams.IMAGE, image);


	        return resultMap;
	    }

	    private static final Logger LOGGER = Logger.getLogger(ListService.class.getName());
}
