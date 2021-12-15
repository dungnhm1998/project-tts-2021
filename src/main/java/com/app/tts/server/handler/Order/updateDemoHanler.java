package com.app.tts.server.handler.Order;

import com.app.tts.main.siupham.Order;
import com.app.tts.main.siupham.mainQuazt;
import com.app.tts.main.siupham.quzt;
import com.app.tts.services.AddOrderServices;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import com.opencsv.CSVReader;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class updateDemoHanler implements Handler<RoutingContext> {



    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {
                String inputCSVFile = "D:/react/order1.csv";
                CSVReader reader = new CSVReader(new FileReader(inputCSVFile));
                Map data = new HashMap();
                data.put("order", reader);



                String [] nextLine;
                int lnNum = 0;

                while ((nextLine = reader.readNext()) != null) {
                    lnNum++;
//                    String id = ParamUtil.getString(reader, nextLine);
                    /* Bind CSV file input to table columns */

                }

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
}
