package com.app.tts.services;

import com.app.tts.error.exception.OracleException;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class UserService  extends MasterService{


    public static final String UPDATE_USER = "{call PKG_QUY.update_user(?,?,?,?,?,?,?,?)}";
    public static final String GET_USER_BY_EMAIL = "{call PKG_QUY.get_user_by_email(?,?,?,?)}";




    // get user by email  in tts_user


    public static List<Map> getUserByEmail(String email) throws SQLException {

        Map resultMap = new HashMap<>();
        List<Map> resultDataList = excuteQuery(GET_USER_BY_EMAIL, new Object[]{email});

        LOGGER.info("=> GET EMAIL  result: " + resultDataList);

        return resultDataList;
    }

    //update user by email
    public static List<Map> updateUser(String email, String username, String address,
                                       String phone, String state) throws SQLException, OracleException {

        Map resultMap = new HashMap<>();
        List<Map> resultDataList = excuteQuery(UPDATE_USER, new Object[]{email, username, address, phone, state});

        LOGGER.info("=> UPDATE BY ID result: " + resultDataList );

        return resultDataList;
    }


    private static Map format(Map queryData) throws SQLException {

        Map resultMap = new LinkedHashMap<>();
        resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
        resultMap.put(AppParams.EMAIL, ParamUtil.getString(queryData, AppParams.S_EMAIL));
        resultMap.put(AppParams.PASSWORD, ParamUtil.getString(queryData, AppParams.S_PASSWORD));
        resultMap.put(AppParams.USERNAME, ParamUtil.getString(queryData, AppParams.S_USERNAME));
        resultMap.put(AppParams.ADDRESS, ParamUtil.getString(queryData, AppParams.S_ADDRESS));
        resultMap.put(AppParams.PHONE, ParamUtil.getString(queryData, AppParams.S_PHONE));
        resultMap.put(AppParams.STATE, ParamUtil.getString(queryData, AppParams.S_STATE));
        resultMap.put(AppParams.CREATE_AT, ParamUtil.getString(queryData, AppParams.D_CREATED_AT));
        resultMap.put(AppParams.UPDATE_AT, ParamUtil.getString(queryData, AppParams.D_UPDATED_AT));

        return resultMap;
    }

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());


}
