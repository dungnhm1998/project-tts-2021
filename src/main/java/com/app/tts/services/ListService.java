package com.app.tts.services;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListService extends MasterService{
	
	public static final String LIST_BASE_SIZE = "{call PKG_CREATE_ORDER.list_base_size(?,?,?,?)}";
	public static final String LIST_BASE_COLOR = "{call PKG_CREATE_ORDER.list_base_color(?,?,?,?)}";
	public static final String LIST_BASE_GROUP = "{call PKG_CREATE_ORDER.list_base_group(?,?,?,?)}";
	
	public static List<Map> getBaseSize(String base_id) throws SQLException{
		List<Map> dataResult = excuteQuery(LIST_BASE_SIZE, new Object[] {base_id});
		return dataResult;
	}
	
	public static List<Map> getBaseColor(String base_id) throws SQLException{
		List<Map> dataResult = excuteQuery(LIST_BASE_COLOR, new Object[] {base_id});
		return dataResult;
	}
	
	public static List<Map> getBaseGroup(String base_group_id) throws SQLException{
		List<Map> dataResult = excuteQuery(LIST_BASE_GROUP, new Object[] {base_group_id});
		return dataResult;
	}
	
	 public static Map format1(Map queryData) {
		 Map Resultmap = new LinkedHashMap();
		 
	 }
}
