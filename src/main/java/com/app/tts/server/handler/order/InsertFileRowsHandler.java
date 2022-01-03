package com.app.tts.server.handler.order;

import com.app.tts.main.quartz.ReadFile;
import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InsertFileRowsHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try{
                String storeId = routingContext.request().getParam("storeId");
                Map request = routingContext.getBodyAsJson().getMap();
                String userId = ParamUtil.getString(request, "user_id");
                ReadFile.csvFile = ParamUtil.getString(request, "url");
                String fileId = ParamUtil.getString(request, "file_id");
                ReadFile.readFile();
                String message = insertFileRows(userId, storeId, fileId);

                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
                Map data = new LinkedHashMap();
                data.put("message", message);
                routingContext.put(AppParams.RESPONSE_DATA, data);

                future.complete();
            }catch (Exception e){
                routingContext.fail(e);
            }
        }, asyncResult -> {
            if(asyncResult.succeeded()){
                routingContext.next();
            }else{
                routingContext.fail(asyncResult.cause());
            }
        });
    }

    public static String insertFileRows(String userId, String storeId, String fileId){
        String message = "fail";
        int countRecord = 0;
        List<Map> listRecords = ReadFile.listMapData;
        while (countRecord < listRecords.size()){
            Map<String, String> record = listRecords.get(countRecord);
            countRecord++;

        //file_rows
        Random rand = new Random();
        String id = String.valueOf(rand.nextInt(1000000000));
        String  name = record.get("Name");
        String  email = record.get("Email");
        String  financialStatus = record.get("Financial Status");

        String createdAtDate = record.get("Created at");
        DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");//yyyyMMdd'T'HHmmss'Z'
        Date createdAt = null;
        try {
            createdAt = df1.parse(createdAtDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String  lineItemQuantity = record.get("Lineitem quantity");
        String  lineItemName = record.get("Lineitem name");
        String  lineItemSku = record.get("Lineitem sku");

        String shippingName = record.get("Shipping Name");
        String  shippingStreet = record.get("Shipping Street");
        String  shippingAddress1 = record.get("Shipping Address1");
        String  shippingAddress2 = record.get("Shipping Address2");
        String  shippingCompany = record.get("Shipping Company");
        String  shippingCity = record.get("Shipping City");

        String shippingZip = record.get("Shipping Zip");
        String  shippingProvince = record.get("Shipping Province");
        String  shippingCountry = record.get("Shipping Country");
        String  shippingPhone = record.get("Shipping Phone");
        String  shippingMethod = record.get("Shipping method");

        String notes = record.get("Notes");
        String  designFrontUrl = record.get("Design front url");
        String  designBackUrl = record.get("Design back url");
        String  mockFrontUrl = record.get("Mockup front url");
        String  mockBackUrl = record.get("Mockup back url");

        String currency = record.get("Currency");
        String  unitAmount = record.get("Unit amount");
        String  location = record.get("Location");
        String state = "Created";

//        try {
            //ham insertTBFileRows da thay doi cac tham so
//            List<Map> resultMap = OrderService.insertTBFileRows(
//                    userId, storeId, fileId,
//                    id, name, email, financialStatus,
//                    createdAt, lineItemQuantity, lineItemName, lineItemSku,
//                    shippingName, shippingStreet, shippingAddress1, shippingAddress2, shippingCompany, shippingCity,
//                    shippingZip, shippingProvince, shippingCountry, shippingPhone, shippingMethod,
//                    notes, designFrontUrl, designBackUrl, mockFrontUrl, mockBackUrl,
//                    currency, unitAmount, location, state
//            );
//            if(!resultMap.isEmpty()){
//                message = "successed";
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        }
        return message;
    }
}
