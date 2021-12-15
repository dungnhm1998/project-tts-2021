package com.app.tts.server.handler.unirest;

import java.util.List;
import java.util.logging.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.server.job.ReadFileTXT;
import com.app.tts.util.AppConstants;
import com.app.tts.util.AppParams;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class CallDropshipAPI extends QuartzJobBean implements Handler<RoutingContext> {

	private String Path = "/check-log/";
	private String url = AppConstants.URLAPIDRS + Path;

	@Override
	public void handle(RoutingContext routingContext) {
		routingContext.vertx().executeBlocking(future -> {
			try {
				ReadFileTXT.readFile();
				String id = routingContext.request().getParam("id");
				HttpResponse<JsonNode> response = Unirest.get(url + id).asJson();

//				Trigger trigger = TriggerBuilder.newTrigger()
//						.withIdentity("demoTrigger", "group")
//						.withSchedule(SimpleScheduleBuilder.simpleSchedule()
//								.withIntervalInSeconds(5)
//								.repeatForever())
//						.build();
//
//				JobDetail job = JobBuilder.newJob(CallDropshipAPI.class)
//						.withIdentity("demoJob", "group")
//						.build();
//
//				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//				scheduler.start();
//				scheduler.scheduleJob(job, trigger);
				
				
				
				 

				routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
				routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
				routingContext.put(AppParams.RESPONSE_DATA, response.getBody().toString());
				future.complete();
			} catch (Exception e) {
				routingContext.fail(e);
			}
		},asyncResult->

	{
			if (asyncResult.succeeded()) {
				routingContext.next();
			} else {
				routingContext.fail(asyncResult.cause());
			}
		});}

	private static final Logger LOGGER = Logger.getLogger(CallDropshipAPI.class.getName());

//	@Override
//	public void execute(JobExecutionContext context) throws JobExecutionException {
//		System.out.println(oneLine());
//		
//	}
	
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
		// TODO Auto-generated method stub
		
	}
}