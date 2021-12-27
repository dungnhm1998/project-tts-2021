package com.app.tts.server.handler.file;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.app.tts.server.job.ImportFileJob;
import com.app.tts.server.job.ReadFile;
import com.app.tts.services.FileService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.RoutingContext;

public class ImportFileHandler implements Handler<RoutingContext> {
	public static Map jsonRequest = new HashMap();
	public static List<Map> listDataOrderImport = new LinkedList<>();
	public static int countOrderImport = 0;

	@Override
	public void handle(RoutingContext routingContext) {
		routingContext.vertx().executeBlocking(future -> {
			try {
				Map data = new HashMap();
				Date date_update = new Date(System.currentTimeMillis());
				HttpServerResponse response = (HttpServerResponse) routingContext.response();
				jsonRequest = routingContext.getBodyAsJson().getMap();
				String csvFile = ParamUtil.getString(jsonRequest, "file_name");
				ReadFile.readCSV(csvFile);
				String id = UUID.randomUUID().toString().substring(5, 20);
				jsonRequest.put("file_id", id);
//				listDataOrderImport = FileService.getDataImportFile(id);
				
//				Trigger trigger = TriggerBuilder.newTrigger().withIdentity("demoTrigger", "group")
//						.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
//						.build();
//
//				JobDetail job = JobBuilder.newJob(ImportFileJob.class).withIdentity("demoJob", "group").build();
//
//				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//				scheduler.start();
//				scheduler.scheduleJob(job, trigger);

				routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
				routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
				routingContext.put(AppParams.RESPONSE_DATA, data);
				future.complete();
			} catch (Exception e) {
				routingContext.fail(e);
			}
		}, asyncResult -> {
			if (asyncResult.succeeded()) {
				routingContext.next();
			} else {
				routingContext.fail(asyncResult.cause());
			}
		});
	}

	private static final Logger LOGGER = Logger.getLogger(ImportFileHandler.class.getName());
}
