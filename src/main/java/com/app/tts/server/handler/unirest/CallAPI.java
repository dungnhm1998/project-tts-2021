package com.app.tts.server.handler.unirest;

import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.util.AppConstants;
import com.app.tts.util.AppParams;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class CallAPI extends QuartzJobBean implements Handler<RoutingContext> {

	private String url = "https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/AccessGoogle/login-google&response_type=code\r\n"
			+ "    &client_id=352140522561-vpmetjr6bjce1vod9b0cppihhbcgdesh.apps.googleusercontent.com&approval_prompt=force";

	@Override
	public void handle(RoutingContext routingContext) {
		routingContext.vertx().executeBlocking(future -> {
			try {
				Map	mapRequest = routingContext.getBodyAsJson().getMap();

				JSONObject jsonData = new JSONObject(mapRequest);

				HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
						.header("Content-Type", "application/json")
						.body(jsonData)
						.asJson();

				routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
				routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
				routingContext.put(AppParams.RESPONSE_DATA, jsonResponse.getBody().toString());
				future.complete();
			} catch (Exception e) {
				routingContext.fail(e);
			}
		}, asyncResult ->

		{
			if (asyncResult.succeeded()) {
				routingContext.next();
			} else {
				routingContext.fail(asyncResult.cause());
			}
		});
	}

	private static final Logger LOGGER = Logger.getLogger(CallAPI.class.getName());

//	@Override
//	public void execute(JobExecutionContext context) throws JobExecutionException {
//		System.out.println(oneLine());
//		
//	}

//	public String oneLine() {
//		String line = null;
//        int countQuartz = ReadFileTXT.count;
//        List<String> listQuartz = ReadFileTXT.listData;
//        if(countQuartz < listQuartz.size()){
//            line = listQuartz.get(countQuartz);
//            ReadFileTXT.count++;
//        }
//        return line;
//	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub

	}
}