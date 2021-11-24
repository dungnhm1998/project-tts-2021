package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class CreateProductServices extends MasterService {
    public static final String CREATE_PRODUCT = "{call PKG_QUY.create_product(?,?,?,?,?,?,?,?,?)}";
    public static final String GET_COLOR = "{call PKG_QUY.get_color2(?,?,?,?)}";
    public static final String GET_SIZE = "{call PKG_QUY.get_size2(?,?,?,?)}";

    public static List<Map> createProduct(String p_campaign_id, String p_base_id, String p_colors, String p_sizes, String p_design_json,String p_mockup_img_url) throws SQLException {
        List<Map> resultMap =  new ArrayList<>();
        List<Map> createProduct = excuteQuery(CREATE_PRODUCT, new Object[]{p_campaign_id, p_base_id, p_colors, p_sizes, p_design_json, p_mockup_img_url});
        LOGGER.info("Create Product" + createProduct);

        for (Map b : createProduct) {
            b = format(b);
            resultMap.add(b);
        }

        return resultMap;
    }
    public static List<Map> get_color(String p_id) throws SQLException {
        List<Map> resultMap =  new ArrayList<>();
        List<Map> get_color = excuteQuery(GET_COLOR, new Object[]{p_id});
        for (Map b : get_color) {
            b = format(b);
            resultMap.add(b);
        }

        return resultMap;
    }
    public static List<Map> get_size(String p_id) throws SQLException {
        List<Map> resultMap =  new ArrayList<>();
        List<Map> get_size = excuteQuery(GET_SIZE, new Object[]{p_id});
        for (Map b : get_size) {
            b = format(b);
            resultMap.add(b);
        }

        return resultMap;
    }

    private static Map format(Map queryData) throws SQLException {
        Map resultMap = new LinkedHashMap<>();
        Map product =  new HashMap();
        resultMap.put(AppParams.CAMPAIGN_ID, ParamUtil.getString(queryData, AppParams.S_CAMPAIGN_ID));
        resultMap.put(AppParams.USER_ID, ParamUtil.getString(queryData, AppParams.S_USER_ID));
//        for(Map product : products){
            product.put("default", ParamUtil.getString(queryData, AppParams.N_DEFAULT1));
            product.put("base_id", ParamUtil.getString(queryData, AppParams.S_BASE_ID));
//            for (Map colors :  products){
        product.put("colors", ParamUtil.getString(queryData, AppParams.S_COLORS));
        product.put("name_color", ParamUtil.getString(queryData, AppParams.NAME_COLOR));
        product.put("value", ParamUtil.getString(queryData, AppParams.S_VALUE));
//            }
//            for (Map sizes :  products){
        product.put("size_id", ParamUtil.getString(queryData, AppParams.S_SIZES));
        product.put("size_name", ParamUtil.getString(queryData, AppParams.SIZE_NAME));
        product.put("price", ParamUtil.getString(queryData, AppParams.PRICE));
//            }
            product.put("design", ParamUtil.getString(queryData, AppParams.S_DESIGN_JSON));
            product.put("mockup", ParamUtil.getString(queryData, AppParams.S_MOCKUP_IMG_URL));
//        }

        resultMap.put("products", product);
        return resultMap;
    }


    private static final Logger LOGGER = Logger.getLogger(CreateProductServices.class.getName());
}
