package com.app.tts.server.handler.ucant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.app.tts.server.csv.GetCsv;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class ImportOrder implements Handler<RoutingContext>{

	public static Map json = new HashMap();

	@Override
	public void handle(RoutingContext rc) {
		rc.vertx().executeBlocking(future -> {
			try {
//				GetCsv.readCSV();
				json = rc.getBodyAsJson().getMap();
				
				rc.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
				rc.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
				future.complete();
			} catch (Exception e) {
				rc.fail(e);
			}
		}, asyncResult -> {
			if (asyncResult.succeeded()) {
				rc.next();
			} else {
				rc.fail(asyncResult.cause());
			}
		});
	}
}
