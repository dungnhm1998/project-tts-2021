package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.error.exception.OracleException;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class UserService  extends MasterService{

    public static final String INSERT_USER = "{call PKG_QUY.create_user(?,?,?,?,?,?,?,?,?)}";
    public static final String GET_USER_BY_ID = "{call PKG_QUY.get_user_byid(?,?,?,?,?)}";
    public static final String GET_ALL_USER = "{call PKG_QUY.get_all_user(?,?,?,?)}";
    public static final String DEL_USER_BY_ID = "{call PKG_QUY.del_user_by_id(?,?,?,?)}";
    public static final String UPDATE_USER = "{call PKG_QUY.update_user(?,?,?,?,?,?,?,?)}";
    public static final String GET_PASS_BY_EMAIL = "{call PKG_QUY.get_user_by_password(?,?,?,?)}";
    public static final String RECOVER_PASSWORD = "{call PKG_QUY.get_user_by_password(?,?,?,?)}";
    public static final String UPDATE_PASSWORD = "{call PKG_TTS_TRUONG.update_password(?, ?, ?, ?, ?)}";
    public static final String GET_USER_BY_EMAIL = "{call PKG_TTS_TRUONG.get_user_by_email(?, ?, ?, ?)}";
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

    public static List<Map> getUserById(String id, String state) throws SQLException {
        List<Map> result = new ArrayList();
        List<Map> resultDataList = excuteQuery(GET_USER_BY_ID, new Object[]{id, state});

        LOGGER.info("=> GET USER by id result: " + resultDataList);
        for (Map b : resultDataList) {
            b = format(b);
            result.add(b);
        }

        return result;

    }
    public static List<Map> delUserById(String id) throws SQLException {
        List<Map> result = new ArrayList();
        List<Map> resultDataList = excuteQuery(DEL_USER_BY_ID, new Object[]{id});

        LOGGER.info("=> DELETE USER by id result: " + resultDataList);
        for (Map b : resultDataList) {
            b = format(b);
            result.add(b);
        }

        return result;



    }
    
    public static List<Map> getUserByEmailRecover(String email) throws SQLException {
    	
    	List<Map> result = new ArrayList();
        List<Map> resultDataList = excuteQuery(GET_USER_BY_EMAIL, new Object[]{email});
        LOGGER.info("=> GET EMAIL  result: " + resultDataList);
        for (Map b : resultDataList) {
            b = format(b);
            result.add(b);
        }
        return result;
    }
    
    public static List<Map> updatePassword(String email, String password)throws SQLException {
        
        List<Map> searchResultMap = update(UPDATE_PASSWORD, new Object[] {email, password});
        Map resultMap = new LinkedHashMap();
        resultMap.put("Pass: ", searchResultMap);
        
        return searchResultMap;
    }
    // insert user in tts_user

    public static  List<Map> insertUser(String email, String password, String username, String address,
                                        String phone, String state) throws SQLException {

        Map resultMap = new HashMap<>();
        List<Map> result = new ArrayList();
        List<Map> resultDataList = excuteQuery(INSERT_USER, new Object[]{email, password, username, address, phone, state});
        LOGGER.info("=> INSERT  result: "+ resultDataList);
        for (Map b : resultDataList) {
            b = format(b);
            result.add(b);
        }

        return result;
    }

    // get user by email  in tts_user



    public static Map getUserByEmail(String email) throws SQLException {

        Map resultMap = new HashMap<>();
        Map resultDataList = searchOne(GET_USER_BY_EMAIL, new Object[]{email});

        LOGGER.info("=> GET EMAIL  result: " + resultDataList);

        return resultDataList;
    }

    public static Map getUserByEmailq(String email) throws SQLException {

        Map resultMap = new HashMap<>();
        Map resultDataList = searchOne(GET_USER_BY_EMAIL, new Object[]{email});

        LOGGER.info("=> GET EMAIL  result: " + resultDataList);

        return resultDataList;
    }
    public static List<Map> getUserByEmail1(String email) throws SQLException {

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
    //update PASSWORD BY email
    public static List<Map> updatePass(String email, String password) throws SQLException, OracleException {

        Map resultMap = new HashMap<>();
        List<Map> resultDataList = excuteQuery(UPDATE_PASSWORD, new Object[]{email, password});

        LOGGER.info("=> UPDATE_PASSWORD BY EMAIL result: " + resultDataList );

        return resultDataList;
    }
    //get pass word

    public static List<Map> getPassByEmail(String email)throws SQLException {


        List<Map> resultDataList = excuteQuery(GET_PASS_BY_EMAIL, new Object[]{email});

        LOGGER.info("=> GET PASSWORD BY EMAIL  result: " + resultDataList);

        return resultDataList;
    }


    public static List<Map> recoverPassword()throws SQLException{

        List<Map> recoverPassword = excuteQuery(RECOVER_PASSWORD, new Object[]{});
        LOGGER.info("=> RECOVER PASSWORD result: " + recoverPassword);
        return recoverPassword;
    }

    private static Map format(Map queryData) {
		Map resultMap = new LinkedHashMap<>();
		Map printTable = new LinkedHashMap<>();
		Map image = new LinkedHashMap<>();
		resultMap.put(AppParams.ID, ParamUtil.getString(queryData, AppParams.S_ID));
		resultMap.put(AppParams.TYPE_ID, ParamUtil.getString(queryData, AppParams.S_TYPE_ID));
		resultMap.put(AppParams.NAME, ParamUtil.getString(queryData, AppParams.S_NAME));
		resultMap.put(AppParams.GROUP_ID, ParamUtil.getString(queryData, AppParams.S_GROUP_ID));
		resultMap.put(AppParams.GROUP_NAME, ParamUtil.getString(queryData, AppParams.S_GROUP_NAME));
		resultMap.put(AppParams.SIZES, ParamUtil.getString(queryData, AppParams.S_SIZES));
		resultMap.put(AppParams.COLORS, ParamUtil.getString(queryData, AppParams.S_COLORS));
		// printable
		printTable.put("front_top", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_TOP));
		printTable.put("front_left", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_LEFT));
		printTable.put("front_width", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_WIDTH));
		printTable.put("front_height", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_FRONT_HEIGHT));
		printTable.put("back_top", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_TOP));
		printTable.put("back_left", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_LEFT));
		printTable.put("back_width", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_WIDTH));
		printTable.put("back_height", ParamUtil.getString(queryData, AppParams.S_PRINTABLE_BACK_HEIGHT));
		// image
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

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());


}
