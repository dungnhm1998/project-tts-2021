package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.mail.javamail.MimeMailMessage;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class SubService extends MasterService{
	
	public static final String INSERT_USER = "{call PKG_REGISTER.insert_user(?,?,?,?,?,?,?)}";
	public static final String GET_USER_BY_EMAIL = "{call PKG_REGISTER.get_user_by_email(?,?,?,?)}";
	public static final String RECOVER_PASSWORD = "{call PKG_REGISTER.update_password(?,?,?,?,?)}";
	public static final String CHANGE_PASSWORD = "{call PKG_REGISTER.update_password(?,?,?,?,?)}";
	public static final String CREATE_CAM = "{call PKG_BQP.create_campaign(?,?,?,?)}";
	public static final String CREATE_PRODUCT = "{call PKG_BQP.create_product(?,?,?,?,?,?,?,?,?)}";
	public static final String GET_PRODUCT = "{call PKG.BQP.get_product(?,?,?,?)}";
	public static final String GET_COLORS = "{call PKG.BQP.get_colors(?,?,?,?)}";
	public static final String GET_SIZES = "{call PKB.get_sizes(?,?,?,?)}";
	
	public static List<Map> insertUser (String id, String email, String password, String phone) throws SQLException{
		List<Map> result = excuteQuery(INSERT_USER, new Object[] {id, email, password, phone});
		return result;
	}
	
	public static List<Map> getUserByEmail (String email) throws SQLException{
		List<Map> result = excuteQuery(GET_USER_BY_EMAIL, new Object[] {email});
		return result;
	}
	
	public static List<Map> recoverPassword (String email, String password) throws SQLException{
		List<Map> result = excuteQuery(RECOVER_PASSWORD, new Object[] {email, password});
		return result;	
	}
	
	public static List<Map> changePassword (String email, String password) throws SQLException{
		List<Map> result = excuteQuery(CHANGE_PASSWORD, new Object[] {email, password});
		return result;
	}
	
	public static List<Map> createCam (String user_id) throws SQLException{
		List<Map> result = excuteQuery(CREATE_CAM, new Object[] {user_id});
		return result;
	}
	
	public static List<Map> createProduct (String campaign_id, String base_id, String color_id, 
			String size_id, String design, String mockups) throws SQLException{
		List<Map> result = excuteQuery(CREATE_PRODUCT, new Object[] {campaign_id, base_id, color_id, 
				size_id, design, mockups});
		return result;
	}
	
	public static List<Map> getProduct (String campaign_id) throws SQLException{
		List<Map> result = excuteQuery(GET_PRODUCT, new Object[] {campaign_id});
		return result;
	}
	
	public static List<Map> getColor (String productId) throws SQLException{
		List<Map> result = excuteQuery(GET_COLORS, new Object[] {productId});
		return result;
	}
	
	public static List<Map> getSize (String productId) throws SQLException{
		List<Map> result = excuteQuery(GET_SIZES, new Object[] {productId});
		return result;
	}
	
	public static Map format(Map queryData) throws SQLException{
		Map resultData = new LinkedHashMap<>();
		Map campaign = new LinkedHashMap<>();
		Map products = new LinkedHashMap<>();
		//campaign
		campaign.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_CAMPAIGN_ID));
		campaign.put(AppParams.USER_ID, ParamUtil.getString(queryData, AppParams.S_USER_ID));
		campaign.put(AppParams.TITLE, ParamUtil.getString(queryData, AppParams.S_TITLE));
		campaign.put(AppParams.DESC, ParamUtil.getString(queryData, AppParams.S_DESC));
		campaign.put(AppParams.CATEGORY_IDS, ParamUtil.getString(queryData, AppParams.S_CATEGORY_IDS));
		campaign.put(AppParams.TAGS, ParamUtil.getString(queryData, AppParams.S_TAGS));
		campaign.put(AppParams.START, ParamUtil.getString(queryData, AppParams.D_START));
		campaign.put(AppParams.END, ParamUtil.getString(queryData, AppParams.D_END));
		campaign.put(AppParams.RELAUNCH, ParamUtil.getString(queryData, AppParams.N_AUTO_RELAUNCH));
		campaign.put(AppParams.PRIVATE, ParamUtil.getString(queryData, AppParams.N_PRIVATE));
		campaign.put(AppParams.FB_PIXEL, ParamUtil.getString(queryData, AppParams.S_FB_PIXEL));
		campaign.put(AppParams.GG_PIXEL, ParamUtil.getString(queryData, AppParams.S_GG_PIXEL));
		campaign.put(AppParams.CREATE, ParamUtil.getString(queryData, AppParams.D_CREATE));
		campaign.put(AppParams.UPDATE, ParamUtil.getString(queryData, AppParams.D_UPDATE));
		campaign.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
		campaign.put(AppParams.LENGTH, ParamUtil.getString(queryData, AppParams.N_LENGTH));
		campaign.put(AppParams.SALE_PRICE, ParamUtil.getString(queryData, AppParams.S_SALE_PRICE));
		campaign.put(AppParams.FAVORITE, ParamUtil.getString(queryData, AppParams.N_FAVORITE));
		campaign.put(AppParams.ARCHIVED, ParamUtil.getString(queryData, AppParams.N_ARCHIVED));
		campaign.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(queryData, AppParams.S_DESIGN_FRONT_URL));
		campaign.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(queryData, AppParams.S_DESIGN_BACK_URL));
		campaign.put(AppParams.DOMAIN_ID, ParamUtil.getString(queryData, AppParams.S_DOMAIN_ID));
		campaign.put(AppParams.DOMAIN, ParamUtil.getString(queryData, AppParams.S_DOMAIN));
		campaign.put(AppParams.ART_IDS, ParamUtil.getString(queryData, AppParams.S_ART_IDS));
		campaign.put(AppParams.BASE_GROUP_ID, ParamUtil.getString(queryData, AppParams.S_BASE_GROUP_ID));
		campaign.put(AppParams.BACK_VIEW, ParamUtil.getString(queryData, AppParams.N_BACK_VIEW));
		campaign.put(AppParams.AS_TM, ParamUtil.getString(queryData, AppParams.N_AS_TM));
		campaign.put(AppParams.AD_TAGS, ParamUtil.getString(queryData, AppParams.S_AD_TAGS));
		campaign.put(AppParams.SEO_TITLE, ParamUtil.getString(queryData, AppParams.S_SEO_TITLE));
		campaign.put(AppParams.SEO_DESC, ParamUtil.getString(queryData, AppParams.S_SEO_DESC));
		campaign.put(AppParams.SEO_IMAGE_COVER, ParamUtil.getString(queryData, AppParams.S_SEO_IMAGE_COVER));
		campaign.put(AppParams.DESIGN_CHECK, ParamUtil.getString(queryData, AppParams.N_DESIGN_CHECK));
		campaign.put(AppParams.DESIGN_VERSION, ParamUtil.getString(queryData, AppParams.S_DESIGN_VERSION));
		campaign.put(AppParams.LEFT_CHEST, ParamUtil.getString(queryData, AppParams.N_LEFT_CHEST));
		campaign.put(AppParams.SUB_STATE, ParamUtil.getString(queryData, AppParams.S_SUB_STATE));
		campaign.put(AppParams.MODIFIED_AT, ParamUtil.getString(queryData, AppParams.MODIFIED_AT));
		campaign.put(AppParams.OLD_TAGS, ParamUtil.getString(queryData, AppParams.OLD_TAGS));
		
		resultData.put("", campaign);
		return resultData;
	}
}

