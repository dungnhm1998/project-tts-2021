package com.app.tts.server.job;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.encode.Md5Code;
import com.app.tts.error.exception.OracleException;
import com.app.tts.services.FileService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class ImportFileJob extends QuartzJobBean {

	static int countRecord;

	public static Map readOnelineMapCSV() {
		countRecord = ReadFile.count;
		List<Map> listRecords = ReadFile.listMapData;
		Map record = new LinkedHashMap();
		System.out.println(countRecord);
		if(countRecord < ReadFile.countLine) {
			countRecord = 0;
		}else {
			if (countRecord < listRecords.size()) {
				record = listRecords.get(countRecord);
				ReadFile.count++;
				countRecord++;
			} else {
				countRecord = -1;
			}
		}
		

		return record;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Map lineMap = readOnelineMapCSV();
		System.out.println("sssssssssssssssssssss1");
		Map importFileMap;
		try {
			importFileMap = FileService.getImportFile();
			System.out.println("Line " + lineMap + countRecord);
			String file_id = ParamUtil.getString(importFileMap, AppParams.S_ID);
			System.out.println(file_id);
			if (countRecord > -1) {
				String id = UUID.randomUUID().toString().substring(5, 20);
				String user_id = ParamUtil.getString(importFileMap, AppParams.S_USER_ID);
				String store_id = ParamUtil.getString(importFileMap, AppParams.S_STORE_ID);
				String name = ParamUtil.getString(lineMap, "Name");
				String email = ParamUtil.getString(lineMap, "Email");
				String shipping_name = ParamUtil.getString(lineMap, "Shipping name");
				String shipping_street = ParamUtil.getString(lineMap, "Shipping street");
				String shipping_address1 = ParamUtil.getString(lineMap, "Shipping address1");
				String shipping_address2 = ParamUtil.getString(lineMap, "Shipping address2");
				String shipping_company = ParamUtil.getString(lineMap, "Shipping company");
				String shipping_city = ParamUtil.getString(lineMap, "Shipping city");
				String shipping_zip = ParamUtil.getString(lineMap, "Shipping zip");
				String shipping_province = ParamUtil.getString(lineMap, "Shipping province");
				String shipping_country = ParamUtil.getString(lineMap, "Shipping country");
				String shipping_phone = ParamUtil.getString(lineMap, "Shipping phone");
				String notes = ParamUtil.getString(lineMap, "Notes");
				String financial_status = ParamUtil.getString(lineMap, "Financial status");
				String lineMapitem_quantity = ParamUtil.getString(lineMap, "Lineitem quantity");
				String lineMapitem_name = ParamUtil.getString(lineMap, "Lineitem name");
				String lineMapitem_sku = ParamUtil.getString(lineMap, "Lineitem sku");
				String design_front_url = ParamUtil.getString(lineMap, "Design front url");
				String design_back_url = ParamUtil.getString(lineMap, "Design back url");
				String mockup_front_url = ParamUtil.getString(lineMap, "Mockup front url");
				String mockup_back_url = ParamUtil.getString(lineMap, "Mockup back url");
				String check_vaild_adress = ParamUtil.getString(lineMap, "Check vaild adress");
				String source = ParamUtil.getString(importFileMap, AppParams.S_SOURCE);
				String state = "created";
				String error_note = null;
				String file = ParamUtil.getString(importFileMap, AppParams.S_FILE_NAME);
				String file_name = FilenameUtils.getBaseName(file);
				String type = FilenameUtils.getExtension(file);
				int reprocess = 0;
				String shipping_method = ParamUtil.getString(lineMap, "Shipping method");
				String currency = ParamUtil.getString(lineMap, "Currency");
				String unit_amount = ParamUtil.getString(lineMap, "Unit amount");
				String location = ParamUtil.getString(lineMap, "Location");
				String group_column = file_id + user_id + store_id + name;
				FileService.importFile(id, file_id, user_id, store_id, name, email, shipping_name, shipping_street,
						shipping_address1, shipping_address2, shipping_company, shipping_city, shipping_zip,
						shipping_province, shipping_country, shipping_phone, notes, financial_status,
						lineMapitem_quantity, lineMapitem_name, lineMapitem_sku, design_front_url, design_back_url,
						mockup_front_url, mockup_back_url, check_vaild_adress, source, state, type, file_name,
						shipping_method, currency, unit_amount, location, Md5Code.md5(group_column));

//				System.out.println(countRecord);
//				System.out.println("count line   "+ReadFile.countLine);
				if (countRecord == ReadFile.countLine) {
					FileService.updateImportFile(file_id, "Done");
				}
				System.out.println("sssssssssssssssssssss3");
			}
		} catch (OracleException | SQLException e) {
			e.printStackTrace();
		}
	}
}
