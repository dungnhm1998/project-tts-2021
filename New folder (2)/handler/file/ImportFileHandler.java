package com.app.tts.server.handler.file;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.app.tts.server.job.Job;
import com.app.tts.server.job.ReadFile;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.RoutingContext;

public class ImportFileHandler implements Handler<RoutingContext> {
	public static Map jsonRequest = new HashMap();

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
				
				Trigger trigger = TriggerBuilder.newTrigger().withIdentity("demoTrigger", "group")
						.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
						.build();

				JobDetail job = JobBuilder.newJob(Job.class).withIdentity("demoJob", "group").build();

				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
				scheduler.start();
				scheduler.scheduleJob(job, trigger);

				routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
				routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
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
