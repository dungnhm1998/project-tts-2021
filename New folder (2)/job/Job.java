package com.app.tts.server.job;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.error.exception.OracleException;
import com.app.tts.server.handler.file.ImportFileHandler;
import com.app.tts.services.FileService;
import com.app.tts.util.ParamUtil;

public class Job extends QuartzJobBean {
	
	public static Map lineMap = new HashMap();

	public static Map readOnelineMapCSV() {
		int countRecord = ReadFile.count;
		List<Map> listRecords = ReadFile.listMapData;
		Map record = new LinkedHashMap();
		if (countRecord < listRecords.size()) {
			record = listRecords.get(countRecord);
			ReadFile.count++;
		}
		return record;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		lineMap = readOnelineMapCSV();
		Map mapRequest = ImportFileHandler.jsonRequest;
		String id = UUID.randomUUID().toString().substring(5, 20);
		String user_id = ParamUtil.getString(mapRequest, "user_id");
		String store_id = ParamUtil.getString(mapRequest, "store_id");
		String name = ParamUtil.getString(lineMap, "Name");
		String email = ParamUtil.getString(lineMap, "Email");
		String shipping_name = ParamUtil.getString(lineMap, "Shipping name");
		String shipping_street = ParamUtil.getString(lineMap, "Shipping street");
		String shipping_address1 = ParamUtil.getString(lineMap, "Shipping address1");
		String shipping_address2 = ParamUtil.getString(lineMap, "Shipping address2");
		String shipping_company = ParamUtil.getString(lineMap, "Shipping company");
		String shipping_city = ParamUtil.getString(lineMap, "Shipping city");
		String shipping_zip = ParamUtil.getString(lineMap, "Shipping zip");
		String shipping_province = ParamUtil.getString(lineMap, "Shipping province");
		String shipping_country = ParamUtil.getString(lineMap, "Shipping country");
		String shipping_phone = ParamUtil.getString(lineMap, "Shipping phone");
		String notes = ParamUtil.getString(lineMap, "Notes");
		String financial_status = ParamUtil.getString(lineMap, "Financial status");
		String lineMapitem_quantity = ParamUtil.getString(lineMap, "lineMapitem quantity");
		String lineMapitem_name = ParamUtil.getString(lineMap, "lineMapitem name");
		String lineMapitem_sku = ParamUtil.getString(lineMap, "lineMapitem sku");
		String design_front_url = ParamUtil.getString(lineMap, "Design front url");
		String design_back_url = ParamUtil.getString(lineMap, "Design back url");
		String mockup_front_url = ParamUtil.getString(lineMap, "Mockup front url");
		String mockup_back_url = ParamUtil.getString(lineMap, "Mockup back url");
		String check_vaild_adress = ParamUtil.getString(lineMap, "Check vaild adress");
		String source = ParamUtil.getString(mapRequest, "source");
		String created_at = ParamUtil.getString(lineMap, "Created at");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date created = null;
		try {
			created = formatter.parse(created_at);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String state = null;
		String error_note = null;
		String file = ParamUtil.getString(mapRequest, "file_name");
		String file_name = FilenameUtils.getBaseName(file);
		String type = FilenameUtils.getExtension(file);
		int reprocess = 0;
		String shipping_method = ParamUtil.getString(lineMap, "Shipping method");
		String currency = ParamUtil.getString(lineMap, "Currency");
		String unit_amount = ParamUtil.getString(lineMap, "Unit amount");
		String location = ParamUtil.getString(lineMap, "Location");

		try {
			FileService.importFile(id, user_id, store_id, name, email, shipping_name, shipping_street,
					shipping_address1, shipping_address2, shipping_company, shipping_city, shipping_zip,
					shipping_province, shipping_country, shipping_phone, notes, financial_status, lineMapitem_quantity,
					lineMapitem_name, lineMapitem_sku, design_front_url, design_back_url, mockup_front_url, mockup_back_url,
					check_vaild_adress, source, created, type, file_name, shipping_method, currency, unit_amount,
					location);
		} catch (OracleException | SQLException e) {
			e.printStackTrace();
		}

//		Trigger trigger = TriggerBuilder.newTrigger()
//				.withIdentity("demoTrigger", "group")
//				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
//						.withIntervalInSeconds(5)
//						.repeatForever())
//				.build();
//
//		JobDetail job = JobBuilder.newJob(Job.class)
//				.withIdentity("demoJob", "group")
//				.build();
//
//		Scheduler scheduler;
//		try {
//			scheduler = new StdSchedulerFactory().getScheduler();
//			scheduler.start();
//			scheduler.scheduleJob(job, trigger);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}

	}

}
