package com.app.tts.server.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.googledrive.example.DownloadFile;
import com.app.tts.services.FileService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class ReadImportFileJob extends QuartzJobBean {
	public static List<Map> listDataImportFile = new ArrayList<Map>();
	static int count = 0;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
			System.out.println("read file db job " + count);
			Map map;
			try {
				map = FileService.getImportFile();
				String url = ParamUtil.getString(map, AppParams.S_URL);
				String nameFile = ParamUtil.getString(map, AppParams.S_FILE_NAME);
				String id = ParamUtil.getString(map, AppParams.S_ID);
				String outFile = "D:/GOOGLE DRIVE API/" + id + nameFile;
				DownloadFile.Download(url, outFile);
				ReadFile.readCSV(outFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

}
