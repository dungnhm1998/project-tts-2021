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
	public static final String CREATE_CAM = "{call PKG_BQP.create_campaignResult(?,?,?,?)}";
	public static final String CREATE_PRODUCT = "{call PKG_BQP.create_product(?,?,?,?,?,?,?,?,?,?)}";
	public static final String GET_PRODUCT = "{call PKG_BQP.get_product(?,?,?,?)}";
	public static final String GET_COLORS = "{call PKG_BQP.get_colors(?,?,?,?)}";
	public static final String GET_SIZES = "{call PKG_BQP.get_sizes(?,?,?,?)}";
	
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
	
	public static List<Map> createProduct (String campaignResult_id, String base_id, String color_id, 
			String size_id, String design, String mockups, String price) throws SQLException{
		List<Map> result = excuteQuery(CREATE_PRODUCT, new Object[] {campaignResult_id, base_id, color_id, 
				size_id, design, mockups, price});
		return result;
	}
	
	public static List<Map> getProduct (String campaignResult_id) throws SQLException{
		List<Map> result = excuteQuery(GET_PRODUCT, new Object[] {campaignResult_id});
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
	
	public static Map format(Map Cam ) throws SQLException{
		Map campaignResult = new LinkedHashMap<>();

		//campaignResult
		campaignResult.put(AppParams.ID, ParamUtil.getString(Cam , AppParams.S_campaignResult_ID));
		campaignResult.put(AppParams.USER_ID, ParamUtil.getString(Cam , AppParams.S_USER_ID));
		campaignResult.put(AppParams.TITLE, ParamUtil.getString(Cam , AppParams.S_TITLE));
		campaignResult.put(AppParams.DESC, ParamUtil.getString(Cam , AppParams.S_DESC));
		campaignResult.put(AppParams.CATEGORY_IDS, ParamUtil.getString(Cam , AppParams.S_CATEGORY_IDS));
		campaignResult.put(AppParams.TAGS, ParamUtil.getString(Cam , AppParams.S_TAGS));
		campaignResult.put(AppParams.START, ParamUtil.getString(Cam , AppParams.D_START));
		campaignResult.put(AppParams.END, ParamUtil.getString(Cam , AppParams.D_END));
		campaignResult.put(AppParams.RELAUNCH, ParamUtil.getString(Cam , AppParams.N_AUTO_RELAUNCH));
		campaignResult.put(AppParams.PRIVATE, ParamUtil.getString(Cam , AppParams.N_PRIVATE));
		campaignResult.put(AppParams.FB_PIXEL, ParamUtil.getString(Cam , AppParams.S_FB_PIXEL));
		campaignResult.put(AppParams.GG_PIXEL, ParamUtil.getString(Cam , AppParams.S_GG_PIXEL));
		campaignResult.put(AppParams.CREATE, ParamUtil.getString(Cam , AppParams.D_CREATE));
		campaignResult.put(AppParams.UPDATE, ParamUtil.getString(Cam , AppParams.D_UPDATE));
		campaignResult.put(AppParams.STATE, ParamUtil.getString(Cam , AppParams.S_STATE));
		campaignResult.put(AppParams.LENGTH, ParamUtil.getString(Cam , AppParams.N_LENGTH));
		campaignResult.put(AppParams.SALE_PRICE, ParamUtil.getString(Cam , AppParams.S_SALE_PRICE));
		campaignResult.put(AppParams.FAVORITE, ParamUtil.getString(Cam , AppParams.N_FAVORITE));
		campaignResult.put(AppParams.ARCHIVED, ParamUtil.getString(Cam , AppParams.N_ARCHIVED));
		campaignResult.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(Cam , AppParams.S_DESIGN_FRONT_URL));
		campaignResult.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(Cam , AppParams.S_DESIGN_BACK_URL));
		campaignResult.put(AppParams.DOMAIN_ID, ParamUtil.getString(Cam , AppParams.S_DOMAIN_ID));
		campaignResult.put(AppParams.DOMAIN, ParamUtil.getString(Cam , AppParams.S_DOMAIN));
		campaignResult.put(AppParams.ART_IDS, ParamUtil.getString(Cam , AppParams.S_ART_IDS));
		campaignResult.put(AppParams.BASE_GROUP_ID, ParamUtil.getString(Cam , AppParams.S_BASE_GROUP_ID));
		campaignResult.put(AppParams.BACK_VIEW, ParamUtil.getString(Cam , AppParams.N_BACK_VIEW));
		campaignResult.put(AppParams.AS_TM, ParamUtil.getString(Cam , AppParams.N_AS_TM));
		campaignResult.put(AppParams.AD_TAGS, ParamUtil.getString(Cam , AppParams.S_AD_TAGS));
		campaignResult.put(AppParams.SEO_TITLE, ParamUtil.getString(Cam , AppParams.S_SEO_TITLE));
		campaignResult.put(AppParams.SEO_DESC, ParamUtil.getString(Cam , AppParams.S_SEO_DESC));
		campaignResult.put(AppParams.SEO_IMAGE_COVER, ParamUtil.getString(Cam , AppParams.S_SEO_IMAGE_COVER));
		campaignResult.put(AppParams.DESIGN_CHECK, ParamUtil.getString(Cam , AppParams.N_DESIGN_CHECK));
		campaignResult.put(AppParams.DESIGN_VERSION, ParamUtil.getString(Cam , AppParams.S_DESIGN_VERSION));
		campaignResult.put(AppParams.LEFT_CHEST, ParamUtil.getString(Cam , AppParams.N_LEFT_CHEST));
		campaignResult.put(AppParams.SUB_STATE, ParamUtil.getString(Cam , AppParams.S_SUB_STATE));
		campaignResult.put(AppParams.MODIFIED_AT, ParamUtil.getString(Cam , AppParams.MODIFIED_AT));
		campaignResult.put(AppParams.OLD_TAGS, ParamUtil.getString(Cam , AppParams.OLD_TAGS));
		
		return campaignResult;
	}
}

