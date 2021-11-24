package com.app.tts.services;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.mail.javamail.MimeMailMessage;

import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class SubService extends MasterService{
	
	public static final String INSERT_USER = "{call PKG_REGISTER.insert_user(?,?,?,?,?,?,?)}";
	public static final String GET_USER_BY_EMAIL = "{call PKG_REGISTER.get_user_by_email(?,?,?,?)}";
	public static final String RECOVER_PASSWORD = "{call PKG_REGISTER.update_password(?,?,?,?,?)}";
	public static final String CHANGE_PASSWORD = "{call PKG_REGISTER.update_password(?,?,?,?,?)}";
	public static final String CREATE_CAM = "{call PKG_BQP.create_campaign(?,?,?,?)}";
	
	public static List<Map> insertUser (String id, String email, String password, String phone) throws SQLException{
		List<Map> result = excuteQuery(INSERT_USER, new Object[] {id, email, password, phone});
		return result;
	}
	
	public static List<Map> getUserByEmail (String email) throws SQLException{
		List<Map> result = excuteQuery(GET_USER_BY_EMAIL, new Object[] {email});
		return result;
	}
	
	public static List<Map> recoverPassword (String email, String password) throws SQLException{
		List<Map> result = excuteQuery(RECOVER_PASSWORD, new Object[] {email, password});
		return result;	
	}
	
	public static List<Map> changePassword (String email, String password) throws SQLException{
		List<Map> result = excuteQuery(CHANGE_PASSWORD, new Object[] {email, password});
		return result;
	}
	
	public static List<Map> createCam (String user_id) throws SQLException{
		List<Map> result = excuteQuery(CREATE_CAM, new Object[] {user_id});
		return result;
	}
}

