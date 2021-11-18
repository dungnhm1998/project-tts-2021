package com.app.tts.services;

import com.app.tts.error.exception.OracleException;
import com.app.tts.util.AppParams;
import com.app.tts.util.DBProcedureUtil;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import oracle.jdbc.OracleTypes;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListBaseService extends MasterService{

    public static final String GET_LIST_DATA = "{call PKG_PHUONG.LIST_BASE(?,?,?)}";
    public static final String GET_BASE = "{call PKG_TTS_TRUONG.FINDBASEBYID(?,?,?,?,?,?)}";

    public static List<Map> getListBase() throws SQLException {
        Map inputData = new LinkedHashMap<Integer, String>();

        Map<Integer, Integer> outputParamTypes = new LinkedHashMap<>();
        outputParamTypes.put(1, OracleTypes.NUMBER);
        outputParamTypes.put(2, OracleTypes.VARCHAR);
        outputParamTypes.put(3, OracleTypes.CURSOR);

        Map<Integer, String> outputParamNames = new LinkedHashMap<>();
        outputParamNames.put(1, AppParams.RESULT_CODE);
        outputParamNames.put(2, AppParams.RESULT_MSG);
        outputParamNames.put(3, AppParams.RESULT_DATA);

        Map searchResultMap = DBProcedureUtil.execute(dataSource, GET_LIST_DATA, inputData,
                outputParamTypes, outputParamNames);

        int resultCode = ParamUtil.getInt(searchResultMap, AppParams.RESULT_CODE);

        if (resultCode != HttpResponseStatus.OK.code()) {
            throw new OracleException(ParamUtil.getString(searchResultMap, AppParams.RESULT_MSG));
        }

        List<Map> result = new ArrayList<>();

            List<Map> resultDataList = ParamUtil.getListData(searchResultMap, AppParams.RESULT_DATA);
            //excuteQuery(GET_LIST_DATA, new Object[] {basse, siat});


            for (Map b : resultDataList) {
                b = format(b);
                result.add(b);
            }

            return result;
        }

    public static Map getListBases(String id) throws SQLException{
    	Map inputData = new LinkedHashMap<Integer, String>();
    	inputData.put(1, id);

        Map<Integer, Integer> outputParamTypes = new LinkedHashMap<>();
        outputParamTypes.put(2, OracleTypes.NUMBER);
        outputParamTypes.put(3, OracleTypes.VARCHAR);
        outputParamTypes.put(4, OracleTypes.CURSOR);
        outputParamTypes.put(5, OracleTypes.CURSOR);
        outputParamTypes.put(6, OracleTypes.CURSOR);

        Map<Integer, String> outputParamNames = new LinkedHashMap<>();
        outputParamNames.put(2, AppParams.RESULT_CODE);
        outputParamNames.put(3, AppParams.RESULT_MSG);
        outputParamNames.put(4, AppParams.RESULT_DATA);
        outputParamNames.put(5, AppParams.RESULT_DATA_2);
        outputParamNames.put(6, AppParams.RESULT_DATA_3);

        Map searchResultMap = DBProcedureUtil.execute(dataSource, GET_BASE, inputData,
                outputParamTypes, outputParamNames);

        int resultCode = ParamUtil.getInt(searchResultMap, AppParams.RESULT_CODE);

        if (resultCode != HttpResponseStatus.OK.code()) {
            throw new OracleException(ParamUtil.getString(searchResultMap, AppParams.RESULT_MSG));
        }
    	//Map searchResultMap = searchOne(GET_BASE, new Object[] {id});

        Map resultMap = new LinkedHashMap();
        List<Map> resultDataList = ParamUtil.getListData(searchResultMap, AppParams.RESULT_DATA);
        List<Map> resultDataList2 = ParamUtil.getListData(searchResultMap, AppParams.RESULT_DATA_2);
        List<Map> resultDataList3 = ParamUtil.getListData(searchResultMap, AppParams.RESULT_DATA_3);
        List<Map> result = new ArrayList<>();
        for (Map a : resultDataList) {
            for (Map b : resultDataList2) {
            	for (Map c : resultDataList3) {
            		c = formatBase(a, b, c );
            		result.add(c);
            	}
            }
        }

        resultMap.put("Base id: "+id, result);
        return resultMap;
    }


    public static Map format(Map queryData) {

        Map resultMap = new LinkedHashMap<>();
        Map printTable = new LinkedHashMap<>();
        Map image = new LinkedHashMap<>();
        resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
        resultMap.put(AppParams.TYPE_ID, ParamUtil.getString(queryData, AppParams.S_TYPE_ID));
        resultMap.put(AppParams.NAME, ParamUtil.getString(queryData, AppParams.S_NAME));
        resultMap.put(AppParams.GROUP_ID, ParamUtil.getString(queryData, AppParams.S_GROUP_ID));
        resultMap.put(AppParams.GROUP_NAME, ParamUtil.getString(queryData, AppParams.S_GROUP_NAME));
        resultMap.put(AppParams.SIZES, ParamUtil.getString(queryData, AppParams.S_SIZES));
        resultMap.put(AppParams.COLORS, ParamUtil.getString(queryData, AppParams.S_COLORS));
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
        
        
        resultMap.put(AppParams.IMAGE, image);
        resultMap.put(AppParams.PRINTABLE, printTable);
        
        return resultMap;
    }
    
    
    public static Map formatBase(Map queryData1, Map queryData2, Map queryData3) {
    	Map resultMap = new LinkedHashMap<>();
        Map colors = new LinkedHashMap<>();
        Map sizes = new LinkedHashMap<>();
        resultMap.put(AppParams.ID, ParamUtil.getString(queryData1, AppParams.S_ID));
        resultMap.put(AppParams.NAME, ParamUtil.getString(queryData1, AppParams.S_NAME));
        resultMap.put(AppParams.SIZES, ParamUtil.getString(queryData1, AppParams.S_SIZES));
        resultMap.put(AppParams.COLORS, ParamUtil.getString(queryData1, AppParams.S_COLORS));
        resultMap.put(AppParams.PRICE, ParamUtil.getString(queryData1, AppParams.S_PRICE));
        // colors
        colors.put(AppParams.ID, ParamUtil.getString(queryData2, AppParams.S_ID));
        colors.put(AppParams.NAME, ParamUtil.getString(queryData2, AppParams.S_NAME));
        colors.put(AppParams.VALUE, ParamUtil.getString(queryData2, AppParams.S_VALUE));
        // sizes
        sizes.put(AppParams.ID, ParamUtil.getString(queryData3, AppParams.S_ID));
        sizes.put(AppParams.NAME, ParamUtil.getString(queryData3, AppParams.S_NAME));
        resultMap.put(AppParams.COLORS, colors);
        resultMap.put(AppParams.SIZES, sizes);
		return resultMap;
    }
}
