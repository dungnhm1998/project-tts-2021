package com.app.tts.server.job;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.server.handler.file.ImportFileHandler;
import com.app.tts.services.DropShipOrderService;
import com.app.tts.services.FileService;
import com.app.tts.services.OrderProductService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class InsertShippingByFileJob extends QuartzJobBean {

	static int countRecord;

	public static Map readOnelineMapCSV() {
//		countRecord = ImportFileHandler.countOrderImport;
		countRecord = ReadImportFileJob.countJob1;
		Map record = new LinkedHashMap();
		List<Map> listRecords = ReadImportFileJob.listImportFile;
		if (!listRecords.isEmpty()) {
			if (countRecord < listRecords.size()) {
				record = listRecords.get(countRecord);
//				ImportFileHandler.countOrderImport++;
				ReadImportFileJob.countJob1++;
				countRecord++;
			} else {
				countRecord = -1;
			}
		} else {
			countRecord = -1;
		}
		return record;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Map listMap = readOnelineMapCSV();
		System.out.println("os :"+countRecord);
		if (countRecord > -1) {
			String shipping_id = UUID.randomUUID().toString().substring(5, 20);
			String shipping_name = ParamUtil.getString(listMap, "S_SHIPPING_NAME");
			String email = ParamUtil.getString(listMap, AppParams.S_EMAIL);
			String phone = ParamUtil.getString(listMap, "S_SHIPPING_PHONE");
			String add_line1 = ParamUtil.getString(listMap, "S_SHIPPING_ADDRESS1");
			String add_line2 = ParamUtil.getString(listMap, "S_SHIPPING_ADDRESS2");
			String add_city = ParamUtil.getString(listMap, "S_SHIPPING_CITY");
			String country_code = ParamUtil.getString(listMap, "S_SHIPPING_COUNTRY");

			String order_id = UUID.randomUUID().toString().substring(5, 20);
			String currency = ParamUtil.getString(listMap, "S_CURRENCY");
			String store_id = ParamUtil.getString(listMap, "S_STORE_ID");
			String user_id = ParamUtil.getString(listMap, "S_USER_ID");
			String reference_order = ParamUtil.getString(listMap, "S_REFERENCE_ORDER");
			String source = ParamUtil.getString(listMap, "S_SOURCE");
			String shipping_method = ParamUtil.getString(listMap, "S_SHIPPING_METHOD");
			try {
				FileService.insertShipping(shipping_id, shipping_name, email, phone, add_line1, add_line2, add_city,
						country_code);
				DropShipOrderService.insertOrder(order_id, currency, shipping_id, store_id, user_id, reference_order,
						source, shipping_method);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
