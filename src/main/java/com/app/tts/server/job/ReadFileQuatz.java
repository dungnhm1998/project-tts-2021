package com.app.tts.server.job;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.encode.Md5Code;
import com.app.tts.services.SubService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadFileQuatz /*implements Job*/ extends QuartzJobBean {
	public static String email;
	public static String password;
	public static String confirmPassword;
	public static String phone;
	
	public String oneLine() {
		String line = null;
        int countQuartz = ReadFileTXT.count;
        List<String> listQuartz = ReadFileTXT.listData;
        if(countQuartz < listQuartz.size()){
            line = listQuartz.get(countQuartz);
            ReadFileTXT.count++;
        }
        return line;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String o = oneLine(); 
		ObjectMapper mapper = new ObjectMapper();	
		Map<String, String> map = null;
		try {
			map = mapper.readValue(o, Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		email = ParamUtil.getString(map, AppParams.EMAIL);
		password = ParamUtil.getString(map, AppParams.PASSWORD);
		confirmPassword = ParamUtil.getString(map, "confirm_password");
		phone = ParamUtil.getString(map, AppParams.PHONE);
		System.out.println(email+"+"+password+"+"+confirmPassword+"+"+phone);
		String id = UUID.randomUUID().toString().substring(5, 20);
		String encodePassword = Md5Code.md5(password); 
		try {
			SubService.insertUser(id, email, encodePassword, phone);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
