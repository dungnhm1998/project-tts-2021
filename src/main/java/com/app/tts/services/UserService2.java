package com.app.tts.services;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserService2 extends MasterService{
    private static final String REGISTER_USER = "{call PKG_USER_PHUONG.REGISTER_USER(?,?,?, ?,?,?)}";
    private static final String GET_USER_BY_EMAIL = "{call PKG_USER_PHUONG.GET_USER_BY_EMAIL(?, ?,?,?)}";
    private static final String UPDATE_PASSWORD = "{call PKG_USER_PHUONG.UPDATE_PASSWORD(?,?, ?,?,?)}";

    public static Map updatePassword(String email, String password) throws SQLException{
        List<Map> result = excuteQuery(UPDATE_PASSWORD, new Object[]{email, password});
        Map resultMap = result.get(0);
        return resultMap;
    }

    public static Map getUserByEmail(String email) throws SQLException{
        List<Map> result = excuteQuery(GET_USER_BY_EMAIL, new Object[]{email});
        Map resultMap = result.get(0);
        return resultMap;
    }

    public static Map registerUser(String email, String password, String phone) throws SQLException {
        List<Map> result = excuteQuery(REGISTER_USER, new Object[]{email, password, phone});

        Map resultMap = format(result.get(0));
        return resultMap;
    }

    public static Map format(Map inputMap){
        Map result = new LinkedHashMap();
        result.put(AppParams.ID, ParamUtil.getString(inputMap, AppParams.S_ID));
        result.put(AppParams.EMAIL, ParamUtil.getString(inputMap, AppParams.S_EMAIL));
        return result;
    }
}
