package com.app.tts.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CampaignService extends MasterService{
    private static final String UPDATE_CAMPAIGN = "{call PKG_BQP.UPDATE_CAMPAIGN(?,?,?,?,?, ?,?,?)}";
    private static final String CREATE_CAMPAIGN = "{call PKG_DROPSHIP_ORDER_PHUONG.CREATE_CAMPAIGN(?, ?,?,?)}";

    public static Map createCampaign(String userId) throws SQLException{
        List<Map> result = excuteQuery(CREATE_CAMPAIGN, new Object[]{userId});
        Map resultMap = result.get(0);
        return resultMap;
    }

    public static List<Map> updateCampaign(String id_campaign, String title, String desc_in,
                                           String design_front_url, String design_back_url) throws SQLException {
        List<Map> resultMap = excuteQuery(UPDATE_CAMPAIGN, new Object[]{id_campaign, title, desc_in,
                design_front_url,  design_back_url});

        return resultMap;
    }
}
