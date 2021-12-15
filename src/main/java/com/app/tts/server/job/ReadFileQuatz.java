package com.app.tts.server.job;

import java.io.FileNotFoundException;
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
        System.out.println(listQuartz);
        if(countQuartz < listQuartz.size()){
            line = listQuartz.get(countQuartz);
            ReadFileTXT.count++;
        }
        return line;
	}
	
	public User readOneLine() {
		User line = null; 
        int countQuartz = ReadCSV.count;
        List<User> listQuartz = ReadCSV.list;
        System.out.println(listQuartz);
        if(countQuartz < listQuartz.size()){
            line = listQuartz.get(countQuartz);
            ReadCSV.count++;
        }
        return line;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.out.println(readOneLine());
//		try {
//			ReadCSV.readCSV();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
