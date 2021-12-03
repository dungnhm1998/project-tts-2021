package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.mail.javamail.MimeMailMessage;

import com.app.tts.server.handler.ucant.AddProductHandler;
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
	public static final String LIST_BASE_AND_GROUP = "{call PKG_BQP.get_base_and_group(?,?,?)}";
	public static final String GET_LIST_SIZE = "{call PKG_BQP.get_list_size(?,?,?)}";
	public static final String GET_LIST_COLOR = "{call PKG_BQP.get_list_color(?,?,?)}";
	
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
	
	public static List<Map> createCam (String userId) throws SQLException{
		List<Map> result = excuteQuery(CREATE_CAM, new Object[] {userId});
		return result;
	}
	
	public static Map createProduct (String campaignResult_id, String baseId, String colorId, 
			String sizeId, String design, String mockups, String price) throws SQLException{
		List<Map> result = excuteQuery(CREATE_PRODUCT, new Object[] {campaignResult_id, baseId, colorId, 
				sizeId, design, mockups, price});
		Map resultData = format1(result);
		return resultData;
	}
	
	public static List<Map> getProduct (String campaignId) throws SQLException{
		List<Map> result = excuteQuery(GET_PRODUCT, new Object[] {campaignId});
		List<Map> resultData = new ArrayList<>();
		for (Map b : result) {
            b = format2(b);
            resultData.add(b);
	        }
		return resultData;
		
	}
	
	public static List<Map> getColor (String productId) throws SQLException{
		List<Map> result = excuteQuery(GET_COLORS, new Object[] {productId});
		List<Map> resultData = new ArrayList<>();
		for (Map b : result) {
            b = format3(b);
            resultData.add(b);
	        }
		return resultData;
	}
	
	public static List<Map> getSize (String productId) throws SQLException{
		List<Map> result = excuteQuery(GET_SIZES, new Object[] {productId});
		List<Map> resultData = new ArrayList<>();
		for (Map b : result) {
            b = format4(b);
            resultData.add(b);
	        }
		return resultData;
	}
	
	public static List<Map> getListBaseGroup () throws SQLException{
		List<Map> result = excuteQuery(LIST_BASE_AND_GROUP, new Object[] {});
		List<Map> resultData = new ArrayList<>();
		for (Map b : result) {
			b = formatBaseGroup(b);
			resultData.add(b);
		}
		return resultData;
	}
	
	public static List<Map> getListSize() throws SQLException{
		List<Map> result = excuteQuery(GET_LIST_SIZE, new Object[] {});
		List<Map> resultData = new ArrayList<>();
		for (Map b : result) {
			b = formatSize(b);
			resultData.add(b);
		}
		return result;
	}
	
	public static List<Map> getListColor() throws SQLException{
		List<Map> result = excuteQuery(GET_LIST_COLOR, new Object[] {});
		List<Map> resultData = new ArrayList<>();
		for (Map b : result) {
			b = formatColor(b);
			resultData.add(b);
		}
		return result;
	}
	
	public static Map format1(List<Map> result) throws SQLException{
		Map campaignResult = new LinkedHashMap<>();
		Map campaign = result.get(0);
		campaignResult.put(AppParams.ID, ParamUtil.getString(campaign , AppParams.S_ID));
		campaignResult.put(AppParams.USER_ID, ParamUtil.getString(campaign , AppParams.S_USER_ID));
		campaignResult.put(AppParams.TITLE, ParamUtil.getString(campaign , AppParams.S_TITLE));
		campaignResult.put(AppParams.DESC, ParamUtil.getString(campaign , AppParams.S_DESC));
		campaignResult.put(AppParams.CATEGORY_IDS, ParamUtil.getString(campaign , AppParams.S_CATEGORY_IDS));
		campaignResult.put(AppParams.TAGS, ParamUtil.getString(campaign , AppParams.S_TAGS));
		campaignResult.put(AppParams.START, ParamUtil.getString(campaign , AppParams.D_START));
		campaignResult.put(AppParams.END, ParamUtil.getString(campaign , AppParams.D_END));
		campaignResult.put(AppParams.RELAUNCH, ParamUtil.getString(campaign , AppParams.N_AUTO_RELAUNCH));
		campaignResult.put(AppParams.PRIVATE, ParamUtil.getString(campaign , AppParams.N_PRIVATE));
		campaignResult.put(AppParams.FB_PIXEL, ParamUtil.getString(campaign , AppParams.S_FB_PIXEL));
		campaignResult.put(AppParams.GG_PIXEL, ParamUtil.getString(campaign , AppParams.S_GG_PIXEL));
		campaignResult.put(AppParams.CREATE, ParamUtil.getString(campaign , AppParams.D_CREATE));
		campaignResult.put(AppParams.UPDATE, ParamUtil.getString(campaign , AppParams.D_UPDATE));
		campaignResult.put(AppParams.STATE, ParamUtil.getString(campaign , AppParams.S_STATE));
		campaignResult.put(AppParams.LENGTH, ParamUtil.getString(campaign , AppParams.N_LENGTH));
		campaignResult.put(AppParams.SALE_PRICE, ParamUtil.getString(campaign , AppParams.S_SALE_PRICE));
		campaignResult.put(AppParams.FAVORITE, ParamUtil.getString(campaign , AppParams.N_FAVORITE));
		campaignResult.put(AppParams.ARCHIVED, ParamUtil.getString(campaign , AppParams.N_ARCHIVED));
		campaignResult.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(campaign , AppParams.S_DESIGN_FRONT_URL));
		campaignResult.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(campaign , AppParams.S_DESIGN_BACK_URL));
		campaignResult.put(AppParams.DOMAIN_ID, ParamUtil.getString(campaign , AppParams.S_DOMAIN_ID));
		campaignResult.put(AppParams.DOMAIN, ParamUtil.getString(campaign , AppParams.S_DOMAIN));
		campaignResult.put(AppParams.ART_IDS, ParamUtil.getString(campaign , AppParams.S_ART_IDS));
		campaignResult.put(AppParams.BASE_GROUP_ID, ParamUtil.getString(campaign , AppParams.S_BASE_GROUP_ID));
		campaignResult.put(AppParams.BACK_VIEW, ParamUtil.getString(campaign , AppParams.N_BACK_VIEW));
		campaignResult.put(AppParams.AS_TM, ParamUtil.getString(campaign , AppParams.N_AS_TM));
		campaignResult.put(AppParams.AD_TAGS, ParamUtil.getString(campaign , AppParams.S_AD_TAGS));
		campaignResult.put(AppParams.SEO_TITLE, ParamUtil.getString(campaign , AppParams.S_SEO_TITLE));
		campaignResult.put(AppParams.SEO_DESC, ParamUtil.getString(campaign , AppParams.S_SEO_DESC));
		campaignResult.put(AppParams.SEO_IMAGE_COVER, ParamUtil.getString(campaign , AppParams.S_SEO_IMAGE_COVER));
		campaignResult.put(AppParams.DESIGN_CHECK, ParamUtil.getString(campaign , AppParams.N_DESIGN_CHECK));
		campaignResult.put(AppParams.DESIGN_VERSION, ParamUtil.getString(campaign , AppParams.S_DESIGN_VERSION));
		campaignResult.put(AppParams.LEFT_CHEST, ParamUtil.getString(campaign , AppParams.N_LEFT_CHEST));
		campaignResult.put(AppParams.SUB_STATE, ParamUtil.getString(campaign , AppParams.S_SUB_STATE));
		campaignResult.put(AppParams.MODIFIED_AT, ParamUtil.getString(campaign , "MODIFIED_AT"));
		campaignResult.put(AppParams.OLD_TAGS, ParamUtil.getString(campaign , AppParams.OLD_TAGS));
		
		return campaignResult;
	}
	
	public static Map format2(Map product ) throws SQLException{
		Map productResult = new LinkedHashMap<>();
		productResult.put(AppParams.ID, ParamUtil.getString(product, AppParams.S_ID));
		productResult.put(AppParams.BASE_ID, ParamUtil.getString(product, AppParams.S_BASE_ID));
		productResult.put(AppParams.NAME, ParamUtil.getString(product, AppParams.S_NAME));
		productResult.put(AppParams.DESC, ParamUtil.getString(product, AppParams.S_DESC));
		productResult.put(AppParams.BASE_COST, ParamUtil.getString(product, AppParams.S_ID));
		productResult.put(AppParams.SALE_PRICE, ParamUtil.getString(product, AppParams.S_SALE_PRICE));
		productResult.put(AppParams.CURRENCY, ParamUtil.getString(product, AppParams.S_CURRENCY));
		productResult.put(AppParams.BACK_VIEW, ParamUtil.getString(product, AppParams.N_BACK_VIEW));
		productResult.put(AppParams.MOCKUP_IMG_URL, ParamUtil.getString(product, AppParams.S_MOCKUP_IMG_URL));
		productResult.put(AppParams.POSITION, ParamUtil.getString(product, AppParams.N_POSITION));
		productResult.put(AppParams.STATE, ParamUtil.getString(product, AppParams.S_STATE));
		productResult.put(AppParams.CREATED_AT, ParamUtil.getString(product, AppParams.D_CREATE));
		productResult.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(product, AppParams.S_CAMPAIGN_ID));
		productResult.put(AppParams.DEFAULT, ParamUtil.getString(product, AppParams.N_DEFAULT));
		productResult.put(AppParams.DEFAULT_COLOR_ID, ParamUtil.getString(product, AppParams.S_DEFAULT_COLOR_ID));
		productResult.put(AppParams.DOMAIN, ParamUtil.getString(product, AppParams.S_DOMAIN));
		productResult.put(AppParams.IMG_URL, ParamUtil.getString(product, AppParams.S_IMG_URL));
		productResult.put(AppParams.DESIGN, ParamUtil.getString(product, AppParams.S_DESIGN_JSON));
		
		return productResult;
	}
	
	public static Map format3(Map color) throws SQLException{
		Map colorResult = new LinkedHashMap<>();
		colorResult.put(AppParams.ID, ParamUtil.getString(color, AppParams.S_ID));
		colorResult.put(AppParams.NAME, ParamUtil.getString(color, AppParams.S_NAME));
		colorResult.put(AppParams.VALUE, ParamUtil.getString(color, AppParams.S_VALUE));
		colorResult.put(AppParams.POSITION, ParamUtil.getString(color, AppParams.N_POSITION));
		
		return colorResult;
	}
	
	public static Map format4(Map size) throws SQLException{
		Map sizeResult = new LinkedHashMap<>();
		sizeResult.put(AppParams.ID, ParamUtil.getString(size, AppParams.S_ID));
		sizeResult.put(AppParams.NAME, ParamUtil.getString(size, AppParams.S_NAME));
		sizeResult.put(AppParams.PRICE, ParamUtil.getString(size, AppParams.S_PRICE));
		sizeResult.put(AppParams.STATE, ParamUtil.getString(size, AppParams.S_STATE));
		sizeResult.put(AppParams.DROPSHIP_PRICE, ParamUtil.getString(size, AppParams.S_DROPSHIP_PRICE));
		sizeResult.put(AppParams.SECOND_SIDE_PRICE, ParamUtil.getString(size, AppParams.S_SECOND_SIDE_PRICE));
		
		return sizeResult;
	}
	
	public static Map formatBaseGroup(Map queryData) {

        Map resultMap = new LinkedHashMap<>();
//        Map size = new LinkedHashMap<>();
//        Map color = new LinkedHashMap<>();
        Map printTable = new LinkedHashMap<>();
        Map image = new LinkedHashMap<>();
        resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
        resultMap.put(AppParams.TYPE_ID, ParamUtil.getString(queryData, AppParams.S_TYPE_ID));
        resultMap.put(AppParams.NAME, ParamUtil.getString(queryData, AppParams.S_NAME));
        resultMap.put(AppParams.GROUP_ID, ParamUtil.getString(queryData, AppParams.S_GROUP_ID));
        resultMap.put(AppParams.GROUP_NAME, ParamUtil.getString(queryData, AppParams.S_GROUP_NAME));
        
//        resultMap.put(AppParams.SIZES, ParamUtil.getString(queryData, AppParams.S_SIZES));
//        resultMap.put(AppParams.COLORS, ParamUtil.getString(queryData, AppParams.S_COLORS));
		
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
	
	public static Map formatSize(Map queryData) {
		
		Map listSize = new LinkedHashMap<>();
		listSize.put("id", ParamUtil.getString(queryData, AppParams.S_ID));
		listSize.put("name", ParamUtil.getString(queryData, AppParams.S_NAME));
		listSize.put("price", ParamUtil.getString(queryData, AppParams.S_PRICE));
		listSize.put("state", ParamUtil.getString(queryData, AppParams.S_STATE));
		listSize.put("dropship_price", ParamUtil.getString(queryData, AppParams.S_DROPSHIP_PRICE));
		listSize.put("second_side_price", ParamUtil.getString(queryData, AppParams.S_SECOND_SIDE_PRICE));
//		listSize.put("baseId", ParamUtil.getString(queryData, AppParams.S_BASE_ID));
		
		return listSize;
	} 
	
	public static Map formatColor(Map queryData) {
		
		Map listColor = new LinkedHashMap<>();
		listColor.put("id", ParamUtil.getString(queryData, AppParams.S_ID));
		listColor.put("name", ParamUtil.getString(queryData, AppParams.S_NAME));
		listColor.put("value", ParamUtil.getString(queryData, AppParams.S_VALUE));
		listColor.put("position", ParamUtil.getString(queryData, AppParams.N_POSITION));
//		listColor.put("baseId", ParamUtil.getString(queryData, AppParams.S_BASE_ID));;
		
		return listColor;
	}
	
	private static final Logger LOGGER = Logger.getLogger(SubService.class.getName());
}

