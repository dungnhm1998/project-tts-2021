package com.app.tts.services;

import java.sql.SQLException;
import java.util.Map;

public class CreateCampaignService extends MasterService{
    public static final String CREATE_CAMPAIGN = "{call PKG_QUY.create_campaign(?,?,?,?)}";
    public static Map createCampaign(String user_id) throws SQLException {

        Map createCampaign = searchOne(CREATE_CAMPAIGN, new Object[]{user_id});

        return createCampaign;
    }
}
