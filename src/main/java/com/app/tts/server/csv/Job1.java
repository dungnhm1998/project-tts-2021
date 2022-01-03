package com.app.tts.server.csv;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.services.BaseService;
import com.app.tts.util.ParamUtil;
import com.opencsv.exceptions.CsvValidationException;

public class Job1 extends QuartzJobBean{
	
	public void getFile() throws SQLException, CsvValidationException, IOException{
		String fileName = "C:\\Users\\Admin\\Downloads\\Telegram Desktop\\dung_test_import.csv";
		Map file = BaseService.getFile();
		System.out.println("File: " + file);
		String id = ParamUtil.getString(file, "S_ID");
		String url = ParamUtil.getString(file, "S_URL");
		DownFile.Download(url, fileName);
		GetCsv.readCSV(fileName);
		List<Map> rows = GetCsv.listMapData;
		System.out.println("Row: " + rows);
		Map update = BaseService.updateStateFile(id);
		
	}
	
	public Map read() { 
		Map s = null;
		int i = GetCsv.n;
		List<Map> output = GetCsv.listMapData;
		if (i < output.size()) {
			s = output.get(i);
			GetCsv.n++;
		}
		return s;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			try {
				getFile();
				
			} catch (CsvValidationException | IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
 