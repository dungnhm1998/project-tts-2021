package com.app.tts.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.app.tts.error.exception.OracleException;

public class FileService extends MasterService {
	public static final String IMPORT_FILE = "{call PKG_TTS_TRUONG.importFile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	public static Map importFile(String id, String user_id, String store_id, String name, /*String order_id,*/
			String email, String shipping_name, String shipping_street, String shipping_address1,
			String shipping_address2, String shipping_company, String shipping_city, String shipping_zip,
			String shipping_province, String shipping_country, String shipping_phone, String notes,
			String financial_status, String lineitem_quantity, String lineitem_name, String lineitem_sku,
			String design_front_url, String design_back_url, String mockup_front_url, String mockup_back_url,
			String check_vaild_adress, String source, Date created, /*Date paided,*/ /*String state, String error_note,*/
			String type, String file_name, /*int reprocess,*/ String shipping_method, String currency, String unit_amount,
			String location) throws SQLException, OracleException {

		Map resultMap = new HashMap<>();
		Map resultDataList = searchOne(IMPORT_FILE,
				new Object[] { id, user_id, store_id, name, /*order_id,*/ email, shipping_name, shipping_street,
						shipping_address1, shipping_address2, shipping_company, shipping_city, shipping_zip,
						shipping_province, shipping_country, shipping_phone, notes, financial_status, lineitem_quantity,
						lineitem_name, lineitem_sku, design_front_url, design_back_url, mockup_front_url,
						mockup_back_url, check_vaild_adress, source, created, /*paided,*/ /*state, error_note,*/ type,
						file_name, /*reprocess,*/ shipping_method, currency, unit_amount, location });
		return resultDataList;

	}
}
