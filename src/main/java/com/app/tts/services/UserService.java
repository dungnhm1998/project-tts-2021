package com.app.tts.services;

import com.app.tts.error.exception.OracleException;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class UserService  extends MasterService{

    public static final String UPDATE_USER = "{call PKG_QUY.update_user(?,?,?,?,?,?,?,?)}";
//    public static final String GET_USER_BY_EMAIL = "{call PKG_QUY.get_user_by_email(?,?,?,?)}";
    public static final String DEL_USER_BY_ID = "{call PKG_QUY.del_user_by_id(?,?,?,?)}";
    public static final String GET_USER_BY_EMAIL = "{call PKG_PHUONG.SEARCH_USER_BY_EMAIL(?,?,?,?)}";
    public static final String UPDATE_PASSWORD = "{call PKG_QUY.update_password(?,?,?,?)}";
    public static final String GET_PASS_BY_EMAIL = "{call PKG_QUY.get_user_by_password(?,?,?,?)}";
    public static final String DELETE_USER_BY_EMAIL = "{call PKG_PHUONG.DELETE_USER(?,?,?,?)}";

//    public static  List<Map> insertUser(String email, String password, String username, String address,
//                                 String phone, String state) throws SQLException {
//
//        Map resultMap = new HashMap<>();
//        List<Map> result = new ArrayList();
//        List<Map> resultDataList = excuteQuery(INSERT_USER, new Object[]{email, password, username, address, phone, state});
//        LOGGER.info("=> INSERT  result: "+ resultDataList);
//        for (Map b : resultDataList) {
//            b = format(b);
//            result.add(b);
//        }
//
//        return result;
//    }

    public static List<Map> deleteUser(String email) throws SQLException{
        List<Map> result = excuteQuery(DELETE_USER_BY_EMAIL, new Object[]{email});
        return result;
    }

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
    
    public static final String GET_ALL_USER = "{call PKG_QUY.get_all_user(?,?,?,?)}";
    
    public static List<Map> getAllUser(String state) throws SQLException {
        List<Map> result = new ArrayList();
        List<Map> resultDataList = excuteQuery(GET_ALL_USER, new Object[]{state});

        LOGGER.info("=> All USER result: " + resultDataList);

        for (Map b : resultDataList) {
            b = format(b);
            result.add(b);
        }
        return result;
    }
    
    public static List<Map> delUserById(String id) throws SQLException {
		return excuteQuery(DEL_USER_BY_ID, new Object[] { id });
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
