package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.*;

public class CampaignService extends MasterService {
    public static final String GET_CAMPAIGN = "{call PKG_QUY.GET_CAMPAIGN(?,?,?)}";


    public static List<Map> GetCampaign() throws SQLException {
        List<Map> result = new ArrayList<>();
        List<Map> List_Campaign = excuteQuery(GET_CAMPAIGN, new Object[]{});

        for (Map b : List_Campaign) {
            b = format(b);
            result.add(b);
        }

        return result;
    }

    public static Map format(Map queryData) {
        Map resultData = new LinkedHashMap();

        resultData.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
        resultData.put(AppParams.DOMAIN, ParamUtil.getString(queryData, AppParams.S_DOMAIN));
        resultData.put(AppParams.DOMAIN_ID, ParamUtil.getString(queryData, AppParams.S_DOMAIN_ID));
        resultData.put(AppParams.TITLE, ParamUtil.getString(queryData, AppParams.S_TITLE));
        resultData.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
        resultData.put(AppParams.BACK_VIEW, ParamUtil.getString(queryData, AppParams.N_BACK_VIEW));
        resultData.put(AppParams.PRIVATE, ParamUtil.getBoolean(queryData, AppParams.N_PRIVATE)? true : false);
        resultData.put(AppParams.CATEGORIES, ParamUtil.getString(queryData, AppParams.S_CATEGORY_IDS));
        resultData.put(AppParams.CREATE, ParamUtil.getString(queryData, AppParams.D_CREATED_AT));
        return resultData;
    }

}
