package com.app.tts.server.handler.importFile;

import com.app.tts.server.handler.importFile.utilGoogle.CreateGoogleApi;
import com.app.tts.services.importFileService.AddOrderServiceImport;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GoogleApiHandler implements Handler<RoutingContext> {
    public static Map jsonRequest = new HashMap();
    public static List<Map> listDataOrderImport = new LinkedList<>();
    public static int countOrderImport = 0;

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try {

                Map jsonRequest = routingContext.getBodyAsJson().getMap();
                String csvFile = ParamUtil.getString(jsonRequest, "file_name");
                Random random = new Random();
                String id = String.valueOf(random.nextInt(100000));
                File uploadFile = new File(csvFile);
                String nameFile = CreateGoogleApi.fileName(uploadFile);
                com.google.api.services.drive.model.File googleFile = CreateGoogleApi.createGoogleFile("1o5VrvjzjSuEa-OZMWXplehBdz3vuG6A8", "text/plain", nameFile, uploadFile);
                String url = googleFile.getWebContentLink();

                String source = ParamUtil.getString(jsonRequest, AppParams.SOURCE);
                String user_id = ParamUtil.getString(jsonRequest, AppParams.USER_ID);
                String store_id = ParamUtil.getString(jsonRequest, AppParams.STORE_ID);
                String state = "created";
                String type = FilenameUtils.getExtension(csvFile);
                String error_note = "";

                Map data = AddOrderServiceImport.format(AddOrderServiceImport.insertFile(id, nameFile, url, type, user_id,
                        store_id, source, state, error_note));

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
}
