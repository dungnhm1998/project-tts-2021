package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class CampaignService extends MasterService{
	public static final String CREATE_ORDER = "{call PKG_BQP.create_campaign(?,?,?,?)}";
	
	public static List<Map> createCampaign (String id) throws SQLException{
		List<Map> resultData = excuteQuery(CREATE_ORDER, new Object[] {id});
		List<Map> result = new ArrayList<>();
		 for (Map a : resultData) {
	            a = format1(a);
	            result.add(a);
	        }
		return result;
	}
	 public static Map format1(Map queryData) {
		 Map result = new LinkedHashMap();
		 
		 result.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID ));
		 result.put(AppParams.USER_ID, ParamUtil.getString(queryData, AppParams.S_USER_ID));
		 
		 return result;
	 }
	
}
