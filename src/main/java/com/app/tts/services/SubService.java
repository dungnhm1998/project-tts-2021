package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
	public static final String UPDATE_DROPSHIP_ORDER = "{call PKG_BQP.update_dropship_order(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static final String UPDATE_SHIPPING = "{call PKG_BQP.update_shipping(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static final String UPDATE_ORDER_PRODUCT = "{call PKG_BQP.update_order_product(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
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
	
	public static Map updateOrder(String orderId, String subAmount, String shippingFee, String taxAmount, 
			String state, String trackingCode, String shippingMethod, String requireRefund,
			String referenceId, String iossNumber, String extraFee, String amount,
			int addrVerified, String addrVerifiedNote) throws SQLException{
		Map result = searchOne(UPDATE_DROPSHIP_ORDER, new Object[] {orderId, subAmount, shippingFee, taxAmount, state, 
				trackingCode, shippingMethod, requireRefund, referenceId, iossNumber, extraFee, amount,
				addrVerified, addrVerifiedNote});
		return result;
	}
	
	public static Map updateShipping(String shippingId, String name, String email, String phone, int gift, 
			String line1, String line2, String city, String state2, String postalCode, String country,
			String coutryName) throws SQLException{
		Map result = searchOne(UPDATE_SHIPPING, new Object[] {shippingId, name, email, phone, gift, 
				line1, line2, city, state2, postalCode, country, coutryName});
		LOGGER.info("Shipping: " + result);
		return result;
	}
	
	public static List<Map> updateProduct(String id, String orderId, String baseId, String variantName, String color, 
			String colorId, String colorName, String sizeId, String sizeName, String quantity, String amount2, 
			String baseCost, String unitAmount, String mockupFrontUrl, String mockupBackUrl, 
			String designFrontUrl, String designBackUrl) throws SQLException{
		List<Map> result = excuteQuery(UPDATE_ORDER_PRODUCT, new Object[] {id, orderId, baseId, variantName, color, 
				colorId, colorName, sizeId, sizeName, quantity, amount2, baseCost, unitAmount, 
				mockupFrontUrl, mockupBackUrl, designFrontUrl, designBackUrl}); 
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
	
	public static Map formatUpdateOrder(Map orderInput, Map shippingInput, List<Map> productList) {
        Map resultMap = new LinkedHashMap();

        resultMap.put(AppParams.ID, ParamUtil.getString(orderInput, AppParams.S_ID));
        resultMap.put(AppParams.CURRENCY, ParamUtil.getString(orderInput, AppParams.S_CURRENCY));
        resultMap.put("sub_amount", ParamUtil.getString(orderInput, AppParams.S_SUB_AMOUNT));
        resultMap.put("shipping_fee", ParamUtil.getString(orderInput, AppParams.S_SHIPPING_FEE));
        resultMap.put("tax_amount", ParamUtil.getString(orderInput, AppParams.S_TAX_AMOUNT));
        resultMap.put(AppParams.STATE, ParamUtil.getString(orderInput, AppParams.S_STATE));
        resultMap.put("quantity", ParamUtil.getString(orderInput, "N_TOTAL_ITEM"));
        resultMap.put("create_date", ParamUtil.getString(orderInput, AppParams.D_CREATE));
        resultMap.put("update_date", ParamUtil.getString(orderInput, AppParams.D_UPDATE));
        resultMap.put("tracking_code", ParamUtil.getString(orderInput, AppParams.S_TRACKING_CODE));
        resultMap.put("order_date", ParamUtil.getString(orderInput, "D_ORDER    "));
        resultMap.put("note", ParamUtil.getString(orderInput, AppParams.S_NOTE));
        resultMap.put("chanel", ParamUtil.getString(orderInput, AppParams.S_CHANNEL));
        resultMap.put(AppParams.USER_ID, ParamUtil.getString(orderInput, AppParams.S_USER_ID));

        resultMap.put("store_id", ParamUtil.getString(orderInput, AppParams.S_STORE_ID));
        resultMap.put("store_name", ParamUtil.getString(orderInput, ""));
        resultMap.put("shipping_id", ParamUtil.getString(orderInput, AppParams.S_SHIPPING_ID));
        resultMap.put("original_id", ParamUtil.getString(orderInput, AppParams.S_ORIGINAL_ID));
        resultMap.put("source", ParamUtil.getString(orderInput, AppParams.S_SOURCE));
        resultMap.put("shipping_method", ParamUtil.getString(orderInput, AppParams.S_SHIPPING_METHOD));
        boolean Unfulfilled = true, fulfilled = false;

        resultMap.put("fulfill_state", ParamUtil.getBoolean(orderInput, AppParams.N_FULFILLED_ITEM) ? Unfulfilled : fulfilled);

        resultMap.put("ioss_number", ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));

        Map shippingMap = new LinkedHashMap();
        shippingMap.put(AppParams.ID, ParamUtil.getString(shippingInput, AppParams.S_ID));
        shippingMap.put(AppParams.NAME, ParamUtil.getString(shippingInput, AppParams.S_NAME));
        shippingMap.put(AppParams.EMAIL, ParamUtil.getString(shippingInput, AppParams.S_EMAIL));
        shippingMap.put(AppParams.PHONE, ParamUtil.getString(shippingInput, AppParams.S_PHONE));
        shippingMap.put("gift", ParamUtil.getString(shippingInput, AppParams.N_GIFT));

        Map addressMap = new LinkedHashMap();
        addressMap.put(AppParams.LINE1, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE1));
        addressMap.put(AppParams.LINE2, ParamUtil.getString(shippingInput, AppParams.S_ADD_LINE2));
        addressMap.put(AppParams.CITY, ParamUtil.getString(shippingInput, AppParams.S_ADD_CITY));
        addressMap.put(AppParams.STATE, ParamUtil.getString(shippingInput, AppParams.S_STATE));
        addressMap.put(AppParams.POSTAL_CODE, ParamUtil.getString(shippingInput, AppParams.S_POSTAL_CODE));
        addressMap.put(AppParams.COUNTRY, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_CODE));
        addressMap.put(AppParams.COUNTRY_NAME, ParamUtil.getString(shippingInput, AppParams.S_COUNTRY_NAME));
        addressMap.put(AppParams.ADDR_VERIFIED, ParamUtil.getString(orderInput, AppParams.N_ADDR_VERIFIED));
        addressMap.put(AppParams.ADDR_VERIFIED_NOTE, ParamUtil.getString(orderInput, AppParams.S_ADDR_VERIFIED_NOTE));

        shippingMap.put(AppParams.ADDRESS, addressMap);
        resultMap.put("shipping", shippingMap);
        resultMap.put("extra_fee", ParamUtil.getString(orderInput, AppParams.S_EXTRA_FEE));
        resultMap.put("amount", ParamUtil.getString(orderInput, AppParams.S_AMOUNT));


        List<Map> itemsList = new LinkedList<>();
        for (Map productInput : productList) {
            Map productMap = new LinkedHashMap();
            productMap.put(AppParams.ID, ParamUtil.getString(productInput, AppParams.S_ID));
            productMap.put("campaign_title", ParamUtil.getString(productInput, AppParams.S_TITLE));
            productMap.put("campaign_url", ParamUtil.getString(productInput, ""));
            productMap.put("user_id", ParamUtil.getString(productInput, AppParams.S_USER_ID));

            Map campaign = new LinkedHashMap();
            campaign.put(AppParams.ID, ParamUtil.getString(productInput, AppParams.S_CAMPAIGN_ID));
            campaign.put("title", ParamUtil.getString(productInput, AppParams.S_TITLE));
            campaign.put("url", ParamUtil.getString(productInput, ""));
            campaign.put("end_time", ParamUtil.getString(productInput, "MODIFIED_AT"));
            campaign.put("domain", ParamUtil.getString(productInput, AppParams.S_DOMAIN));
            campaign.put("domain_id", ParamUtil.getString(productInput, AppParams.S_DOMAIN_ID));
            productMap.put("campaign", campaign);

            productMap.put(AppParams.PRODUCT_ID, ParamUtil.getString(productInput, AppParams.S_PRODUCT_ID));
            productMap.put(AppParams.BASE_ID, ParamUtil.getString(productInput, AppParams.S_BASE_ID));
            productMap.put("variant_id", ParamUtil.getString(productInput, AppParams.S_VARIANT_ID));
            productMap.put("variant_name", ParamUtil.getString(productInput, AppParams.S_VARIANT_NAME));
            productMap.put(AppParams.SIZES, ParamUtil.getString(productInput, "S_SIZE_ID"));
            productMap.put(AppParams.SIZE_NAME, ParamUtil.getString(productInput, "S_SIZE_NAME"));
            productMap.put(AppParams.COLOR, ParamUtil.getString(productInput, AppParams.S_COLOR_VALUE));
            productMap.put(AppParams.COLOR_ID, ParamUtil.getString(productInput, AppParams.S_COLOR_ID));
            productMap.put(AppParams.COLOR_NAME, ParamUtil.getString(productInput, AppParams.S_COLOR_NAME));
            productMap.put(AppParams.VARIANT_NAME, ParamUtil.getString(productInput, AppParams.S_VARIANT_NAME));
            productMap.put("currency", ParamUtil.getString(productInput, AppParams.S_CURRENCY));
            productMap.put(AppParams.QUANTITY, ParamUtil.getString(productInput, AppParams.N_QUANTITY));
            productMap.put("shipping_fee", ParamUtil.getString(productInput, AppParams.S_SHIPPING_FEE));
            productMap.put("amount", ParamUtil.getString(productInput, AppParams.S_AMOUNT));
            productMap.put("state", ParamUtil.getString(productInput, AppParams.S_STATE));
            productMap.put("base_cost", ParamUtil.getString(productInput, AppParams.S_BASE_COST));
            productMap.put(AppParams.LINE_ITEM_ID, ParamUtil.getString(productInput, AppParams.S_LINE_ITEM_ID));
            productMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(productInput, AppParams.S_SHIPPING_METHOD));
            productMap.put(AppParams.ITEM_TYPE, ParamUtil.getString(productInput, AppParams.S_ITEM_TYPE));
            productMap.put(AppParams.PRINT_DETAIL, ParamUtil.getString(productInput, AppParams.S_PRINT_DETAIL));

            Map designsMap = new LinkedHashMap();
            designsMap.put("design_front_url", ParamUtil.getString(productInput, AppParams.S_DESIGN_FRONT_URL));
            designsMap.put("design_back_url", ParamUtil.getString(productInput, "S_DESIGN_BACK_URL"));
            designsMap.put("mockup_front_url", ParamUtil.getString(productInput, AppParams.S_VARIANT_FRONT_URL));
            designsMap.put("mockup_back_url", ParamUtil.getString(productInput, AppParams.S_VARIANT_BACK_URL));


            productMap.put(AppParams.DESIGNS, designsMap);

            productMap.put(AppParams.PARTNER_SKU, ParamUtil.getString(productInput, AppParams.S_PARTNER_SKU));
            productMap.put("partner_url", ParamUtil.getString(productInput, ""));
            productMap.put("base_short_code", ParamUtil.getString(productInput, AppParams.S_BASE_COST));
            productMap.put("unit_amount", ParamUtil.getString(productInput, AppParams.S_UNIT_AMOUNT));
            productMap.put("tax", ParamUtil.getString(productInput, ""));
            productMap.put("tax_amount", ParamUtil.getString(productInput, AppParams.S_TAX_AMOUNT));
            productMap.put("tax_rate", ParamUtil.getString(productInput, ""));


            Map brand = new LinkedHashMap();
            productMap.put("brand", brand);

            productMap.put("source", ParamUtil.getString(productInput, ""));


            itemsList.add(productMap);
        }

        resultMap.put("items", itemsList);

        resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(orderInput, AppParams.S_TAX_AMOUNT));
        resultMap.put(AppParams.IOSS_NUMBER, ParamUtil.getString(orderInput, AppParams.S_IOSS_NUMBER));

        return resultMap;
    }
 
	
	private static final Logger LOGGER = Logger.getLogger(SubService.class.getName());
}

