/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tts.main;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.app.tts.server.job.ReadCSV;
import com.app.tts.server.job.ReadFile;
import com.app.tts.util.ParamUtil;

/**
 *
 * @author hungdt
 */
public class DCMain {

	public static ApplicationContext appContext;


	public DCMain() {
	}

	public static void main(String[] args) throws Exception {
//		ReadFileTXT.readFile();
		ReadCSV.readFile();
//		Date date_update = new Date(System.currentTimeMillis());
//		System.out.println(date_update);
		String created_at = "7/24/2020  2:07:00 PM";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date created = null;
		try {
			created = formatter.parse(created_at);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		System.out.println(created);
		appContext = new ClassPathXmlApplicationContext("app-context.xml");
	}
}
