package com.app.tts.server.job;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.server.handler.file.ImportFileHandler;
import com.app.tts.services.FileService;
import com.app.tts.services.OrderProductService;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class InsertOrderProductByFileJob extends QuartzJobBean {

	static int countRecord;
	static Map mapRequest = new HashMap();

	public static List<Map> allLineMap() {
		mapRequest = ImportFileHandler.jsonRequest;
		String file_id = ParamUtil.getString(mapRequest, "file_id");
		List<Map> listRecords = null;
		try {
			listRecords = FileService.getDataImportFile(file_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listRecords;
	}

	public static Map readOnelineMapCSV() {
		countRecord = ImportFileHandler.countOrderImport;
		Map record = new LinkedHashMap();
		List<Map> listRecords = allLineMap();
		if (!listRecords.isEmpty()) {
			if (countRecord < listRecords.size()) {
				record = listRecords.get(countRecord);
				ImportFileHandler.countOrderImport++;
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
		System.out.println(listMap);
		System.out.println(countRecord);
		if (countRecord > -1) {
			try {
				String shipping_name = ParamUtil.getString(listMap, "S_SHIPPING_NAME");
				String order_id = UUID.randomUUID().toString().substring(5, 20);
				String id = UUID.randomUUID().toString().substring(5, 20);
				String sku = ParamUtil.getString(listMap, "S_LINEITEM_SKU");
				String campaign_id = null;
				String variant_id = null;
				String size_id;
				String price;
				String shipping_fee = null;
				String currency = ParamUtil.getString(listMap, "S_CURRENCY");
				int quantity = ParamUtil.getInt(listMap, "S_QUANTITY");
				String state;
				Date created;
				String variant_name = null;
				String amount;
				int status;
				String base_cost;
				String base_id;
				String line_item_id;
				int refunded_item;
				String variant_front_url = null;
				String variant_back_url = null;
				String color_id;
				String color_value;
				String partner_sku;
				String color_name;
				String size_name;
				String shipping_method = ParamUtil.getString(listMap, "S_SHIPPING_METHOD");
				String unit_amount = ParamUtil.getString(listMap, AppParams.S_UNIT_AMOUNT);
				int refund_item;
				String design_back_url;
				String design_front_url;
				int p = sku.indexOf('|');
				System.out.println("sku" + sku);
				if (p >= 0) {
					variant_id = sku.substring(0, p);
					size_id = sku.substring(p + 1);
					System.out.println(variant_id);
					Map variantMap = FileService.getByVariantId(variant_id);
					if (!variantMap.isEmpty()) {
						price = ParamUtil.getString(variantMap, "S_PRICE");
						variant_name = ParamUtil.getString(variantMap, "S_VARIANT_NAME");
						base_id = ParamUtil.getString(variantMap, "S_BASE_ID");
						variant_front_url = ParamUtil.getString(variantMap, "S_VARIANT_FRONT_URL");
						variant_back_url = ParamUtil.getString(variantMap, "S_VARIANT_BACK_URL");
						color_id = ParamUtil.getString(variantMap, "S_COLOR_ID");
						color_value = ParamUtil.getString(variantMap, "S_COLOR_VALUE");
						color_name = ParamUtil.getString(variantMap, AppParams.S_COLOR_NAME);
						size_name = ParamUtil.getString(variantMap, AppParams.S_SIZE_NAME);
						design_back_url = ParamUtil.getString(variantMap, AppParams.S_DESIGN_BACK_URL);
						design_front_url = ParamUtil.getString(variantMap, AppParams.S_DESIGN_FRONT_URL);
						OrderProductService.insertOrderProduct(id, order_id, campaign_id, variant_id, size_id, price,
								shipping_fee, currency, quantity, variant_name, base_id, variant_front_url,
								variant_back_url, color_id, color_value, color_name, size_name, shipping_method,
								unit_amount, design_back_url, design_front_url);
					}
				} else {
					sku = ParamUtil.getString(listMap, "S_LINEITEM_SKU");
//					System.out.println(sku+"sku");
					Map skuMap = FileService.getBySKU(sku);
					System.out.println("skuMap" + skuMap);
					if (!skuMap.isEmpty()) {
						price = ParamUtil.getString(skuMap, "S_PRICE");
						base_id = ParamUtil.getString(skuMap, "S_BASE_ID");
						color_id = ParamUtil.getString(skuMap, "S_COLOR_ID");
						color_value = ParamUtil.getString(skuMap, "S_COLOR_VALUE");
						color_name = ParamUtil.getString(skuMap, AppParams.S_COLOR_NAME);
						size_name = ParamUtil.getString(skuMap, AppParams.S_SIZE_NAME);
						size_id = ParamUtil.getString(skuMap, AppParams.S_SIZE_ID);
						design_back_url = ParamUtil.getString(listMap, AppParams.S_DESIGN_BACK_URL);
						design_front_url = ParamUtil.getString(listMap, AppParams.S_DESIGN_FRONT_URL);
						OrderProductService.insertOrderProduct(id, order_id, campaign_id, variant_id, size_id, price,
								shipping_fee, currency, quantity, variant_name, base_id, variant_front_url,
								variant_back_url, color_id, color_value, color_name, size_name, shipping_method,
								unit_amount, design_back_url, design_front_url);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(countRecord);
	}
}
