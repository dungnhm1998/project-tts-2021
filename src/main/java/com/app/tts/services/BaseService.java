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
import java.util.logging.Logger;

public class BaseService extends MasterService{


	public static final String GET_LIST_BASE = "{call PKG_BASE.get_list_base(?,?,?)}";

	public static final String GET_10_BASE = "{call PKG_PHUONG.GET_10_BASE(?,?,?,?,?)}";
	public static final String LIST_GROUP_BASE_COLOR_SIZE = "{call PKG_PHUONG.LIST_GROUP_BASE_COLOR_SIZE(?,?,?,?,?,?)}";

	public static Map listGroupBaseColorSize() throws SQLException{
		Map resultQuery = excuteQuery4OutPut(LIST_GROUP_BASE_COLOR_SIZE, new Object[]{});
		List<Map> listBaseDB = ParamUtil.getListData(resultQuery, AppParams.RESULT_DATA);
		List<Map> listColorDB = ParamUtil.getListData(resultQuery, AppParams.RESULT_DATA_2);
		List<Map> listSizeDB = ParamUtil.getListData(resultQuery, AppParams.RESULT_DATA_3);
		List<Map> listPriceDB = ParamUtil.getListData(resultQuery, AppParams.RESULT_DATA_4);

		Map<String, List<Map>> mapSize = new LinkedHashMap<>();
		for(Map size : listSizeDB){
			String baseIdInSize = ParamUtil.getString(size, "BASE_ID");
			String sizeId = ParamUtil.getString(size, "SIZE_ID");

			for(Map price : listPriceDB){
				String baseIdInPrice = ParamUtil.getString(price, "BASE_ID");
				String sizeIdInPrice = ParamUtil.getString(price, "SIZE_ID");

				if(baseIdInSize.equals(baseIdInPrice) && sizeId.equals(sizeIdInPrice)){
					size.put(AppParams.S_PRICE, ParamUtil.getString(price, AppParams.S_PRICE));
					size.put(AppParams.S_DROPSHIP_PRICE, ParamUtil.getString(price, AppParams.S_DROPSHIP_PRICE));
					size.put(AppParams.S_SECOND_SIDE_PRICE,ParamUtil.getString(price, AppParams.S_SECOND_SIDE_PRICE));
				}
			}

			size = formatSize(size);

			if(mapSize.containsKey(baseIdInSize)){
				mapSize.get(baseIdInSize).add(size);
			}else{
				List<Map> listSize = new LinkedList<>();
				listSize.add(size);
				mapSize.put(baseIdInSize, listSize);
			}
		}

		Map<String, List<Map>> mapColor = new LinkedHashMap<>();
		for(Map color: listColorDB){
			String baseIdInColor = ParamUtil.getString(color, "BASE_ID");
			color = formatColor(color);

			if(mapColor.containsKey(baseIdInColor)){
				mapColor.get(baseIdInColor).add(color);
			}else{
				List<Map> listColor = new LinkedList<>();
				listColor.add(color);
				mapColor.put(baseIdInColor, listColor);
			}
		}

		Map<String, List<Map>> resultGroup = new LinkedHashMap();
		for(Map base : listBaseDB){
			String groupName = ParamUtil.getString(base, AppParams.S_GROUP_NAME);
			String baseId = ParamUtil.getString(base, AppParams.S_ID);

			base = format(base);

			List<Map> sizeList = mapSize.get(baseId);
			if(sizeList != null) {
				base.put(AppParams.SIZES, sizeList);
			}else{
				base.put(AppParams.SIZES, new ArrayList<>());
			}

			List<Map> colorList = mapColor.get(baseId);
			if(colorList != null){
			base.put(AppParams.COLORS, colorList);
			}else{
				base.put(AppParams.COLORS, new ArrayList<>());
			}
			if(resultGroup.containsKey(groupName)){
				resultGroup.get(groupName).add(base);
			}else{
				List<Map> listBase = new LinkedList<>();
				listBase.add(base);
				resultGroup.put(groupName, listBase);
			}
		}
		return resultGroup;
	}

	public static Map formatSize(Map mapSize){
		Map result = new LinkedHashMap();
		result.put(AppParams.ID, ParamUtil.getString(mapSize, "SIZE_ID"));
		result.put(AppParams.NAME, ParamUtil.getString(mapSize, "SIZE_NAME"));
		result.put(AppParams.PRICE, ParamUtil.getString(mapSize, AppParams.S_PRICE));
		result.put(AppParams.STATE, ParamUtil.getString(mapSize, AppParams.S_STATE));
		result.put(AppParams.DROPSHIP_PRICE, ParamUtil.getString(mapSize, AppParams.S_DROPSHIP_PRICE));
		result.put(AppParams.SECOND_SIDE_PRICE, ParamUtil.getString(mapSize, AppParams.S_SECOND_SIDE_PRICE));
		return result;
	}

	public static Map formatColor(Map mapColor){
		Map result = new LinkedHashMap();
		result.put(AppParams.ID, ParamUtil.getString(mapColor, AppParams.S_ID));
		result.put(AppParams.NAME, ParamUtil.getString(mapColor, AppParams.S_NAME));
		result.put(AppParams.VALUE, ParamUtil.getString(mapColor, AppParams.S_VALUE));
		result.put(AppParams.POSITION, ParamUtil.getInt(mapColor, AppParams.N_POSITION));

		return result;
	}

	public static List<Map> get10Base(int posStart, int posNumber) throws SQLException{
		List<Map> result = excuteQuery(GET_10_BASE, new Object[]{posStart, posNumber});
		List<Map> resultMap = new ArrayList<>();
		for (Map b : result) {
			b = format(b);
			resultMap.add(b);
		}
		return resultMap;
	}

	public static List<Map> searchBase(String page, String page_size, String name) throws SQLException{
		List<Map> result = excuteQuery(GET_10_BASE, new Object[]{page, page_size, name});
		return result;
	}

	public static List<Map> getListBase() throws SQLException {

		Map inputParams = new LinkedHashMap<Integer, String>();
		Map<Integer, Integer> outputParamsTypes = new LinkedHashMap<>();
		outputParamsTypes.put(1, OracleTypes.NUMBER);
		outputParamsTypes.put(2, OracleTypes.VARCHAR);
		outputParamsTypes.put(3, OracleTypes.CURSOR);

		Map<Integer, String> outputParamsNames = new LinkedHashMap<>();
		outputParamsNames.put(1, AppParams.RESULT_CODE);
		outputParamsNames.put(2, AppParams.RESULT_MSG);
		outputParamsNames.put(3, AppParams.RESULT_DATA);

		Map searchResultMap = DBProcedureUtil.execute(dataSource, GET_LIST_BASE, inputParams, outputParamsTypes,
				outputParamsNames);

		int resultCode = ParamUtil.getInt(searchResultMap, AppParams.RESULT_CODE);


		if (resultCode != HttpResponseStatus.OK.code()) {
			throw new OracleException(ParamUtil.getString(searchResultMap, AppParams.RESULT_MSG));
		}

		Map resultMap = new HashMap<>();
		List<Map> result = new ArrayList();
		List<Map> resultDataList = ParamUtil.getListData(searchResultMap, AppParams.RESULT_DATA);
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


	private static final Logger LOGGER = Logger.getLogger(BaseService.class.getName());
}
