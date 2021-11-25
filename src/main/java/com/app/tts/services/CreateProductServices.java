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
    public static final String CREATE_PRODUCT = "{call PKG_QUY.create_product(?,?,?,?,?,?,?,?,?)}";
    public static final String GET_PRODUCT = "{call PKG_QUY.get_color2(?,?,?,?)}";
    public static final String GET_COLORS = "{call PKG_QUY.get_color3(?,?,?,?)}";
    public static final String GET_SIZES = "{call PKG_QUY.get_color4(?,?,?,?)}";

    public static Map createProduct(String p_campaign_id, String p_base_id, String p_colors, String p_sizes, String p_design_json, String p_mockup_img_url) throws SQLException {
//        List<Map> resultMap = new ArrayList<>();
        Map createProduct = searchOne(CREATE_PRODUCT, new Object[]{p_campaign_id, p_base_id, p_colors, p_sizes, p_design_json, p_mockup_img_url});
        List<Map> getProduct = ParamUtil.getListData(createProduct, AppParams.RESULT_DATA);
        List<Map> getCampaign = ParamUtil.getListData(createProduct, AppParams.RESULT_DATA_2);
        List<Map> getColor = ParamUtil.getListData(createProduct, AppParams.RESULT_DATA_3);
        List<Map> getSize = ParamUtil.getListData(createProduct, AppParams.RESULT_DATA_4);
        LOGGER.info("Create Product" + createProduct);
        Map resultMap = format(getProduct, getCampaign, getColor, getSize);
        return resultMap;
//        for (Map b : createProduct) {
//            b = format(b);
//            resultMap.add(b);
//        }
//
//        return resultMap;
    }

//    public static List<Map> getPoduct(String p_id) throws SQLException {
//        List<Map> resultMap = new ArrayList<>();
//        List<Map> get_color = excuteQuery(GET_PRODUCT, new Object[]{p_id});
//        for (Map b : get_color) {
//            b = format1(b);
//            resultMap.add(b);
//        }
//
//        return resultMap;
//    }

//    public static List<Map> get_size(String p_id) throws SQLException {
//        List<Map> resultMap = new ArrayList<>();
//        List<Map> get_color3 = excuteQuery(GET_COLORS, new Object[]{p_id});
//        for (Map b : get_color3) {
//            b = format(b);
//            resultMap.add(b);
//        }
//
//        return resultMap;
//    }
//    public static List<Map> get_color(String p_id) throws SQLException {
//        List<Map> resultMap = new ArrayList<>();
//        List<Map> get_color4 = excuteQuery(GET_SIZES, new Object[]{p_id});
//        for (Map b : get_color4) {
//            b = format2(b);
//            resultMap.add(b);
//        }
//
//        return resultMap;
//    }
//
//
//    private static Map format1(Map queryData) throws SQLException {
//        Map resultMap = new LinkedHashMap<>();
//        Map colors = new LinkedHashMap<>();
//        Map sizes = new LinkedHashMap<>();
////        resultMap.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(queryData, AppParams.S_CAMPAIGN_ID));
////        resultMap.put(AppParams.USER_ID, ParamUtil.getString(queryData, AppParams.S_USER_ID));
//        resultMap.put("id", ParamUtil.getString(queryData, AppParams.S_ID1));
//        resultMap.put("default", ParamUtil.getString(queryData, AppParams.N_DEFAULT));
//        resultMap.put("base_id", ParamUtil.getString(queryData, AppParams.S_BASE_ID));
//
//
////        colors.put("colors", ParamUtil.getString(queryData, AppParams.S_COLORS));
////        colors.put("name_color", ParamUtil.getString(queryData, AppParams.NAME_COLOR));
////        colors.put("value", ParamUtil.getString(queryData, AppParams.S_VALUE));
////
////        sizes.put("size_id", ParamUtil.getString(queryData, AppParams.S_SIZES));
////        sizes.put("size_name", ParamUtil.getString(queryData, AppParams.SIZE_NAME));
////        sizes.put("price", ParamUtil.getString(queryData, AppParams.PRICE));
////
////
//        resultMap.put("design", ParamUtil.getString(queryData, AppParams.S_DESIGN_JSON));
//        resultMap.put("mockup", ParamUtil.getString(queryData, AppParams.S_MOCKUP_IMG_URL));
////
////        resultMap.put("colors",colors);
////        resultMap.put("sizes",sizes);
//        return resultMap;
//    }

    private static Map format2(Map queryData) throws SQLException {
        Map resultMap = new LinkedHashMap<>();
        Map colors = new LinkedHashMap<>();

        resultMap.put("colors", ParamUtil.getString(queryData, AppParams.S_COLORS));
        resultMap.put("name_color", ParamUtil.getString(queryData, AppParams.NAME_COLOR));
        resultMap.put("value", ParamUtil.getString(queryData, AppParams.S_VALUE));


        resultMap.put("colors",colors);

        return resultMap;
    }

    private static Map format3(Map queryData) throws SQLException {
        Map resultMap = new LinkedHashMap<>();


        resultMap.put("size_id", ParamUtil.getString(queryData, AppParams.S_SIZES));
        resultMap.put("size_name", ParamUtil.getString(queryData, AppParams.SIZE_NAME));
        resultMap.put("price", ParamUtil.getString(queryData, AppParams.PRICE));



        return resultMap;
    }

    private static Map format(List<Map> productInput, List<Map> listCampaign, List<Map> colorInput, List<Map> sizeInput) throws SQLException {
        Map campaignMap = new LinkedHashMap();
        Map campaign = listCampaign.get(0);

        Map resultMap = new LinkedHashMap<>();
        Map products = new LinkedHashMap<>();
        Map colors = new LinkedHashMap<>();
        Map sizes = new LinkedHashMap<>();
        Map results = new LinkedHashMap<>();
        resultMap.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(queryData, AppParams.S_ID1));
        resultMap.put(AppParams.TITLE, ParamUtil.getString(queryData, AppParams.S_TITLE));
        resultMap.put(AppParams.USER_ID, ParamUtil.getString(queryData, AppParams.S_USER_ID));

        products.put("default", ParamUtil.getString(queryData, AppParams.N_DEFAULT1));
        products.put("base_id", ParamUtil.getString(queryData, AppParams.S_BASE_ID));

        colors.put("colors", ParamUtil.getString(queryData, AppParams.S_COLORS));
        colors.put("name_color", ParamUtil.getString(queryData, AppParams.NAME_COLOR));
        colors.put("value", ParamUtil.getString(queryData, AppParams.S_VALUE));


        sizes.put("size_id", ParamUtil.getString(queryData, AppParams.S_SIZES));
        sizes.put("size_name", ParamUtil.getString(queryData, AppParams.SIZE_NAME));
        sizes.put("price", ParamUtil.getString(queryData, AppParams.PRICE));

        products.put("design", ParamUtil.getString(queryData, AppParams.S_DESIGN_JSON));
        products.put("mockup", ParamUtil.getString(queryData, AppParams.S_MOCKUP_IMG_URL));


        results.put("products", products);
        results.put("colors", colors);
        results.put("sizes", sizes);
        resultMap.put("product" , results);
        return resultMap;
    }


    private static final Logger LOGGER = Logger.getLogger(CreateProductServices.class.getName());
}
