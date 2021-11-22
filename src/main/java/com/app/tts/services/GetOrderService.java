package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GetOrderService extends MasterService {
    public static final String GET_SHIPPING = "{call PKG_QUY.get_shipping(?,?,?)}";


    public static List<Map> getShippingService() throws SQLException {
        List<Map> result = new ArrayList();
        List<Map> resultDataList = excuteQuery(GET_SHIPPING, new Object[]{});
        LOGGER.info("resultDataList" + resultDataList);
        for (Map b : resultDataList) {
            b = format(b);
            result.add(b);
        }

        return result;
    }


    public static Map format(Map queryData) {
        Map resultMap = new LinkedHashMap();
        Map address = new LinkedHashMap();
        Map shipping = new LinkedHashMap();
        resultMap.put(AppParams.ORDER_ID, ParamUtil.getString(queryData, AppParams.S_ORDER_ID));
        resultMap.put(AppParams.CURRENCY, ParamUtil.getString(queryData, AppParams.S_CURRENCY));
        resultMap.put(AppParams.SUB_AMOUNT, ParamUtil.getString(queryData, AppParams.S_SUB_AMOUNT));
        resultMap.put(AppParams.SHIPPING_FEE, ParamUtil.getString(queryData, AppParams.S_SHIPPING_FEE));
        resultMap.put(AppParams.TAX_AMOUNT, ParamUtil.getString(queryData, AppParams.S_TAX_AMOUNT));
        resultMap.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
        resultMap.put(AppParams.QUANTITY, ParamUtil.getString(queryData, AppParams.N_QUANTITY));
        resultMap.put(AppParams.CREATE, ParamUtil.getString(queryData, AppParams.D_CREATED));
        resultMap.put(AppParams.UPDATE, ParamUtil.getString(queryData, AppParams.D_UPDATE));
        resultMap.put(AppParams.SHIPPING_METHOD, ParamUtil.getString(queryData, AppParams.S_SHIPPING_METHOD));
        resultMap.put(AppParams.ORDER, ParamUtil.getString(queryData, AppParams.D_ORDER));
        resultMap.put(AppParams.USER_ID, ParamUtil.getString(queryData, AppParams.S_USER_ID));
        resultMap.put(AppParams.STORE_ID, ParamUtil.getString(queryData, AppParams.S_STORE_ID));


        shipping.put("id", AppParams.S_ID);
        shipping.put("name", AppParams.S_NAME);
        shipping.put("email", AppParams.S_EMAIL);
        shipping.put("gift", AppParams.N_GIFT);
        shipping.put("phone", AppParams.S_PHONE);

        address.put("line1", AppParams.S_ADD_LINE1);
        address.put("line2", AppParams.S_ADD_LINE2);
        address.put("city", AppParams.S_ADD_CITY);
        address.put("state", AppParams.S_ADD_STATE);
        address.put("postal_code", AppParams.S_POSTAL_CODE);
        address.put("country", AppParams.S_COUNTRY_CODE);
        address.put("country_name", AppParams.S_COUNTRY_NAME);

        resultMap.put(AppParams.SHIPPING_FEE, shipping);
        resultMap.put(AppParams.ADDRESS, address);

        return resultMap;
    }

    private static final Logger LOGGER = Logger.getLogger(GetOrderService.class.getName());

}
