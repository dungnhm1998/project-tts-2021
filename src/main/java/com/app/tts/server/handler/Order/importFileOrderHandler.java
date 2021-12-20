package com.app.tts.server.handler.Order;

import com.app.tts.main.siupham.JobA;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import com.opencsv.CSVReader;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

public class importFileOrderHandler implements Handler<RoutingContext>, Job {

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                Map json = routingContext.getBodyAsJson().getMap();

                String file_name = ParamUtil.getString(json, "file_name");
                String source = ParamUtil.getString(json, "source");
                String url = ParamUtil.getString(json, "url");
                String user_id = ParamUtil.getString(json, "user_id");


                Map data = new LinkedHashMap();


                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.CREATED.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.CREATED.reasonPhrase());
                routingContext.put(AppParams.RESPONSE_DATA, data);

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

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }

    private static final Logger LOGGER = Logger.getLogger(importFileOrderHandler.class.getName());
}
