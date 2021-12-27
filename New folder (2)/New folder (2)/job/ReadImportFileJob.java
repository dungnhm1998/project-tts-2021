package com.app.tts.server.job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.server.handler.file.ImportFileHandler;
import com.app.tts.services.FileService;
import com.app.tts.util.ParamUtil;

public class ReadImportFileJob extends QuartzJobBean {
	public static List<Map> listImportFile = new ArrayList<Map>();
	static int countJob1 = 0;
	static int countJob2 = 0;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Map mapRequest = ImportFileHandler.jsonRequest;
		String file_id = ParamUtil.getString(mapRequest, "file_id");
		try {
			listImportFile = FileService.getDataImportFile(file_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
