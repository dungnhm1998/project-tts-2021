package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CreateProductServices extends MasterService {
    public static final String CREATE_PRODUCT = "{call PKG_QUY.create_product(?,?,?,?,?,?,?,?,?,?)}";
    public static final String GET_PRODUCT = "{call PKG_QUY.get_product(?,?,?,?)}";
    public static final String GET_COLORS = "{call PKG_QUY.get_color3(?,?,?,?)}";
    public static final String GET_SIZES = "{call PKG_QUY.get_color4(?,?,?,?)}";
    private static int count = -1;

    public static List<Map> createProduct(String p_campaign_id, String p_base_id, String p_colors, String p_sizes, String priceid, String p_design_json, String p_mockup_img_url) throws SQLException {
        List<Map> resultMap = new ArrayList<>();
        List<Map> createProduct = excuteQuery(CREATE_PRODUCT, new Object[]{p_campaign_id, p_base_id, p_colors, p_sizes, priceid, p_design_json, p_mockup_img_url});
//        List<Map> getProduct = ParamUtil.getListData(createProduct, AppParams.RESULT_DATA);

        LOGGER.info("Create Product" + createProduct);
//        Map resultMap = format(getProduct);
//        return resultMap;
        for (Map b : createProduct) {
            b = format(b);
            resultMap.add(b);
        }
//
        return resultMap;
    }

    public static List<Map> getPoduct(String p_id) throws SQLException {
        List<Map> resultMap = new ArrayList<>();
        List<Map> get_color = excuteQuery(GET_PRODUCT, new Object[]{p_id});
        for (Map b : get_color) {
            b = format1(b);
            resultMap.add(b);
        }

        return resultMap;
    }

    public static List<Map> get_color(String p_id) throws SQLException {
        List<Map> resultMap = new ArrayList<>();
        List<Map> get_color3 = excuteQuery(GET_COLORS, new Object[]{p_id});
        for (Map b : get_color3) {
            b = format2(b);
            resultMap.add(b);
        }

        return resultMap;
    }

    public static List<Map> get_size(String p_id) throws SQLException {
        List<Map> resultMap = new ArrayList<>();
        List<Map> get_color4 = excuteQuery(GET_SIZES, new Object[]{p_id});
        for (Map b : get_color4) {
            b = format3(b);
            resultMap.add(b);
        }

        return resultMap;
    }


    private static Map format1(Map productMap) throws SQLException {
        Map resultProduct = new LinkedHashMap();
        resultProduct.put(AppParams.ID, ParamUtil.getString(productMap, AppParams.S_ID1));
        resultProduct.put(AppParams.BASE_ID, ParamUtil.getString(productMap, AppParams.S_BASE_ID));
        resultProduct.put(AppParams.NAME, ParamUtil.getString(productMap, AppParams.S_NAME));
        resultProduct.put(AppParams.DESC, ParamUtil.getString(productMap, AppParams.S_DESC));
        resultProduct.put(AppParams.BASE_COST, ParamUtil.getString(productMap, AppParams.S_BASE_COST));
        resultProduct.put(AppParams.SALE_PRICE, ParamUtil.getString(productMap, AppParams.S_SALE_PRICE));
        resultProduct.put(AppParams.CURRENCY, ParamUtil.getString(productMap, AppParams.S_CURRENCY));
        resultProduct.put(AppParams.BACK_VIEW, ParamUtil.getString(productMap, AppParams.N_BACK_VIEW));
        resultProduct.put(AppParams.MOCKUP_IMG_URL, ParamUtil.getString(productMap, AppParams.S_MOCKUP_IMG_URL));
        resultProduct.put(AppParams.POSITION, ParamUtil.getString(productMap, AppParams.N_POSITION));
        resultProduct.put(AppParams.STATE, ParamUtil.getString(productMap, AppParams.S_STATE1));
        resultProduct.put(AppParams.CREATE_AT, ParamUtil.getString(productMap, AppParams.D_CREATE));
        resultProduct.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(productMap, AppParams.S_CAMPAIGN_ID));

        resultProduct.put(AppParams.DEFAULT, ParamUtil.getInt(productMap, AppParams.N_DEFAULT));
        resultProduct.put(AppParams.DEFAULT_COLOR_ID, ParamUtil.getString(productMap, AppParams.S_DEFAULT_COLOR_ID));
        resultProduct.put(AppParams.DOMAIN, ParamUtil.getString(productMap, AppParams.S_DOMAIN));
        resultProduct.put(AppParams.IMG_URL, ParamUtil.getString(productMap, AppParams.S_IMG_URL));
        resultProduct.put(AppParams.DESIGN, ParamUtil.getString(productMap, AppParams.S_DESIGN_JSON));
        resultProduct.put(AppParams.PRICE, ParamUtil.getString(productMap, "S_SIZES"));
        resultProduct.put(AppParams.COLORS, ParamUtil.getString(productMap, "S_COLORS"));
        return resultProduct;
    }

    private static Map format2(Map colorMapList) throws SQLException {
        // danh sach id color co trong product
        // list id color
        Map colorMap = new LinkedHashMap();
        colorMap.put(AppParams.ID, ParamUtil.getString(colorMapList, AppParams.S_COLORS));
        colorMap.put(AppParams.NAME, ParamUtil.getString(colorMapList, AppParams.NAME_COLOR));
        colorMap.put(AppParams.VALUE, ParamUtil.getString(colorMapList, AppParams.S_VALUE));
        colorMap.put(AppParams.POSITION, ParamUtil.getString(colorMapList, AppParams.N_POSITION));

        return colorMap;

    }

    private static Map format3(Map queryData) throws SQLException {
        Map resultMap = new LinkedHashMap<>();

        resultMap.put(AppParams.SIZES, ParamUtil.getString(queryData, AppParams.S_SIZES));
        resultMap.put(AppParams.NAME, ParamUtil.getString(queryData, "SIZE_NAME"));


        String priceInSize = null;

        resultMap.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE1));
        resultMap.put("dropship_price", ParamUtil.getString(queryData, AppParams.S_DROPSHIP_PRICE));
        resultMap.put("second_side_price", ParamUtil.getString(queryData, AppParams.S_SECOND_SIDE_PRICE));


        return resultMap;
    }

    private static Map format(Map campaign) throws SQLException {
        Map campaignMap = new LinkedHashMap();


        campaignMap.put(AppParams.ID, ParamUtil.getString(campaign, AppParams.S_ID1));
        campaignMap.put(AppParams.USER_ID, ParamUtil.getString(campaign, AppParams.S_USER_ID));
        campaignMap.put(AppParams.TITLE, ParamUtil.getString(campaign, AppParams.S_TITLE));
        campaignMap.put(AppParams.DESC, ParamUtil.getString(campaign, AppParams.S_DESC));
        campaignMap.put(AppParams.CATEGORY_IDS, ParamUtil.getString(campaign, AppParams.S_CATEGORY_IDS));
        campaignMap.put(AppParams.TAGS, ParamUtil.getString(campaign, AppParams.S_TAGS));
        campaignMap.put(AppParams.START, ParamUtil.getString(campaign, AppParams.D_START));
        campaignMap.put(AppParams.END, ParamUtil.getString(campaign, AppParams.D_END));
        campaignMap.put(AppParams.RELAUNCH, ParamUtil.getString(campaign, AppParams.N_AUTO_RELAUNCH));
        campaignMap.put(AppParams.PRIVATE, ParamUtil.getString(campaign, AppParams.N_PRIVATE));
        campaignMap.put(AppParams.FB_PIXEL, ParamUtil.getString(campaign, AppParams.S_FB_PIXEL));
        campaignMap.put(AppParams.GG_PIXEL, ParamUtil.getString(campaign, AppParams.S_GG_PIXEL));
        campaignMap.put(AppParams.CREATE, ParamUtil.getString(campaign, AppParams.D_CREATE));
        campaignMap.put(AppParams.UPDATE, ParamUtil.getString(campaign, AppParams.D_UPDATE));
        campaignMap.put(AppParams.STATE, ParamUtil.getString(campaign, AppParams.S_STATE1));
        campaignMap.put(AppParams.LENGTH, ParamUtil.getString(campaign, AppParams.N_LENGTH));
        campaignMap.put(AppParams.SALE_PRICE, ParamUtil.getString(campaign, AppParams.S_SALE_PRICE));
        campaignMap.put(AppParams.FAVORITE, ParamUtil.getString(campaign, AppParams.N_FAVORITE));
        campaignMap.put(AppParams.ARCHIVED, ParamUtil.getString(campaign, AppParams.N_ARCHIVED));
        campaignMap.put(AppParams.DESIGN_FRONT_URL, ParamUtil.getString(campaign, AppParams.S_DESIGN_FRONT_URL));
        campaignMap.put(AppParams.DESIGN_BACK_URL, ParamUtil.getString(campaign, AppParams.S_DESIGN_BACK_URL));
        campaignMap.put(AppParams.DOMAIN_ID, ParamUtil.getString(campaign, AppParams.S_DOMAIN_ID));
        campaignMap.put(AppParams.DOMAIN, ParamUtil.getString(campaign, AppParams.S_DOMAIN));
        campaignMap.put(AppParams.ART_IDS, ParamUtil.getString(campaign, AppParams.S_ART_IDS));
        campaignMap.put(AppParams.BASE_GROUP_ID, ParamUtil.getString(campaign, AppParams.S_BASE_GROUP_ID));
        campaignMap.put(AppParams.BACK_VIEW, ParamUtil.getString(campaign, AppParams.N_BACK_VIEW));
        campaignMap.put(AppParams.AS_TM, ParamUtil.getString(campaign, AppParams.N_AS_TM));
        campaignMap.put(AppParams.AD_TAGS, ParamUtil.getString(campaign, AppParams.S_AD_TAGS));
        campaignMap.put(AppParams.SEO_TITLE, ParamUtil.getString(campaign, AppParams.S_SEO_TITLE));
        campaignMap.put(AppParams.SEO_DESC, ParamUtil.getString(campaign, AppParams.S_SEO_DESC));
        campaignMap.put(AppParams.SEO_IMAGE_COVER, ParamUtil.getString(campaign, AppParams.S_SEO_IMAGE_COVER));
        campaignMap.put(AppParams.DESIGN_CHECK, ParamUtil.getString(campaign, AppParams.N_DESIGN_CHECK));
        campaignMap.put(AppParams.DESIGN_VERSION, ParamUtil.getString(campaign, AppParams.S_DESIGN_VERSION));
        campaignMap.put(AppParams.LEFT_CHEST, ParamUtil.getString(campaign, AppParams.N_LEFT_CHEST));
        campaignMap.put(AppParams.SUB_STATE, ParamUtil.getString(campaign, AppParams.S_SUB_STATE));
        campaignMap.put(AppParams.MODIFIED_AT, ParamUtil.getString(campaign, "MODIFIED_AT"));
        campaignMap.put(AppParams.OLD_TAGS, ParamUtil.getString(campaign, "OLD_TAGS"));

        return campaignMap;
    }


    private static final Logger LOGGER = Logger.getLogger(CreateProductServices.class.getName());
}
