package com.app.tts.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SubService extends MasterService{
	
	public static final String GET_USER_BY_EMAIL = "{call PKG_USER.get_user_by_email(?,?,?,?)}";
	
	public static List<Map> getUserByEmail(String email) throws SQLException{
		List<Map> result = excuteQuery(GET_USER_BY_EMAIL, new Object [] {email});
		return result;
	}
	
	public static final String INSERT_USER = "{call PKG_REGISTER.insert_user(?,?,?,?,?,?,?,?,?,?,?)}";
	public static final String GET_USER_BY_EMAIL2 = "{call PKG_REGISTER.get_user_by_email(?,?,?,?)}";
	
	public static List<Map> insertUser(String userName, String password, String firstName, String lastName, String phone, 
			String email, String address, String postal) throws SQLException {
		List<Map> result = excuteQuery(INSERT_USER, new Object[] {userName, password, firstName, lastName, phone, email, address, postal});
		return result;
	}
	
	public static List<Map> getUserByEmail2(String email) throws SQLException {
		List<Map> result = excuteQuery(GET_USER_BY_EMAIL2, new Object[] {email});
		return result;
	}

		private static final Logger LOGGER = Logger.getLogger(BaseService.class.getName());
}
