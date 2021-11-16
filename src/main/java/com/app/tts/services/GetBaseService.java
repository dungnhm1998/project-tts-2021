package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GetBaseService extends MasterService {
    public static final String GET_LIST_BASE = "{call PKG_QUY.get_list_base(?,?,?)}";
        
    public static List<Map> getBaseService() throws SQLException {
        List<Map> result = new ArrayList();
        List<Map> resultDataList = excuteQuery(GET_LIST_BASE, new Object[]{});
        for (Map b : resultDataList) {
            b = format(b);
            result.add(b);
        }

        return result;
    }

    private static Map format(Map queryData) {

        Map resultMap = new LinkedHashMap<>();
        Map printTable = new LinkedHashMap<>();
        Map image = new LinkedHashMap<>();
        Map colors = new LinkedHashMap<>();
        Map sizes = new LinkedHashMap<>();
        resultMap.put(AppParams.GROUP_ID, ParamUtil.getString(queryData, AppParams.S_GROUP_ID));
        resultMap.put(AppParams.GROUP_NAME, ParamUtil.getString(queryData, AppParams.S_GROUP_NAME));

        resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
        resultMap.put(AppParams.TYPE_ID, ParamUtil.getString(queryData, AppParams.S_TYPE_ID));
        resultMap.put(AppParams.NAME, ParamUtil.getString(queryData, AppParams.S_NAME));
        resultMap.put(AppParams.RESOLUTION_REQUIRE, ParamUtil.getString(queryData, AppParams.S_RESOLUTION_REQUIRE));


        //sizes
        sizes.put("default_profit", ParamUtil.getString(queryData, AppParams.S_DEFAULT_COLOR_ID));
        //colors
        colors.put("id", ParamUtil.getString(queryData, AppParams.S_COLORS));
        colors.put("name", ParamUtil.getString(queryData, AppParams.S_NAME));
        colors.put("value", ParamUtil.getString(queryData, AppParams.S_VALUE));
        colors.put("position", ParamUtil.getString(queryData, AppParams.N_POSITION));

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


        resultMap.put(AppParams.SIZES, sizes);
        resultMap.put(AppParams.COLORS, colors);
        resultMap.put(AppParams.IMAGE, image);
        resultMap.put(AppParams.PRINTABLE, printTable);

        return resultMap;
    }

    private static final Logger LOGGER = Logger.getLogger(GetBaseService.class.getName());
}
