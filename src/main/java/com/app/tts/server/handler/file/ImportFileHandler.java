package com.app.tts.server.handler.file;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;

import com.app.tts.googledrive.example.CreateGoogleFile;
import com.app.tts.services.FileService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;
import com.google.api.services.drive.model.File;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
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
				String id = UUID.randomUUID().toString().substring(5, 20);
//				jsonRequest.put("file_id", id);
				
				java.io.File uploadFile = new java.io.File(csvFile);
				String nameFile = CreateGoogleFile.fileName(uploadFile);
				File googleFile = CreateGoogleFile.createGoogleFile("1b26RpoYRUyk-4oKj0SuCEt68erZc3Mf7", "text/plain", nameFile, uploadFile);
				String url = googleFile.getWebContentLink();
//				String outFile = "D:/GOOGLE DRIVE API/" + nameFile;
//				DownloadFile.Download(url, outFile);
//				java.io.File out = new java.io.File(outFile);
//				new Thread(new Download(url, out)).start();
//				newThread.stop();
//				System.out.println(outFile);
//				ReadFile.readCSV(outFile);
				
//				jsonRequest.put("url", url);
//				jsonRequest.put("file_download", outFile);
//				System.out.println(ParamUtil.getString(jsonRequest, "file_download"));

				String source = ParamUtil.getString(jsonRequest, AppParams.SOURCE);
				String user_id = ParamUtil.getString(jsonRequest, AppParams.USER_ID);
				String store_id = ParamUtil.getString(jsonRequest, AppParams.STORE_ID);
				String state = "created";
				String type = FilenameUtils.getExtension(csvFile);
				String error_note = "";
				
				data = FileService.format(FileService.insertFile(id, nameFile, url, type, user_id, store_id, state, error_note, source));

				
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
