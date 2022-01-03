package com.app.tts.server.handler.order;

import com.app.tts.services.BeanDrive;
import com.app.tts.services.DriveFile;
import com.app.tts.services.OrderService;
import com.app.tts.util.AppParams;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.*;

public class InsertFileHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.vertx().executeBlocking(future -> {
            try{
                Map request = routingContext.getBodyAsJson().getMap();
                String message = uploadFile(request);
                Map data = new LinkedHashMap();
                data.put("message", message);
                
                routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
                routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
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

    public String uploadFile(Map<String, String> request){
        String message = null;
        try {
            String pathFile = request.get("url");
            String storeId = request.get("store_id");
            String userId = request.get("user_id");
            String source = request.get("source");
            String[] pathFileList = pathFile.split("\\\\");
            String fileName = pathFileList[pathFileList.length - 1];

            //kiem tra xem file da ton tai trong db chua
            List<Map> listFileDb = OrderService.getImportFile(fileName, userId, storeId);
            if(!listFileDb.isEmpty()){
                message = "error because file exited";
            }else {

                //upload file len google
                BeanDrive beanDrive = new BeanDrive();
                DriveFile driveFile = new DriveFile();
                beanDrive.googleCredential = beanDrive.googleCredential();
                driveFile.googleDrive = beanDrive.getService();

                //c2 tao thu muc trong google drive, download = url, url la link download file
                File uploadFile = new File(pathFile);                     // duoi link dan den thu muc drive chua file
                Map<String, String> fileMap2 = driveFile.createGoogleFile("1gMzX-FQt1d3qwqaNvagYTuLEJkWxB5bL", "text/plain", fileName, uploadFile);

                String urlFile2 = fileMap2.get("urlFile");
                String state2 = "Created";

                //insert tb_file
                Random rand2 = new Random();
                String id2 = String.valueOf(rand2.nextInt(1000000000));
                OrderService.insertImportFile(id2, fileName, urlFile2, userId, storeId, source, state2);

                //download file
                String path = "E:\\fileDriveTest\\" + id2;
                driveFile.downloadFile2(urlFile2, path);
                //end c2

                //c1 luu file tren google api, download = id file
//                Map<String, String> folderMap = driveFile.createNewFolder("test2912");
//                String idFolder = folderMap.get("idFolder");
//
//                List<String> parent = new LinkedList<>();
//                parent.add(idFolder);
//                Map<String, String> fileMap = driveFile.uploadFile(parent, fileName, new File(pathFile));
//                String urlFile = fileMap.get("urlFile");
//                String idFile = fileMap.get("idFile");
//
//                String state = "Created";
//
                //insert tb_file
//                Random rand = new Random();
//                String id = String.valueOf(rand.nextInt(1000000000));
//                OrderService.insertImportFile(id, fileName, urlFile, userId, storeId, source, state);
//
//                //download file
//                driveFile.downloadFile(idFile, id);
//                //end c1

                message = urlFile2;
            }
            System.out.println("======================================upload end==============================================");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return message;
    }
}
