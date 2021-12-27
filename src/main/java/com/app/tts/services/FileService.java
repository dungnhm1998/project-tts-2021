package com.app.tts.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.app.tts.error.exception.OracleException;
import com.app.tts.util.AppParams;
import com.app.tts.util.ParamUtil;

public class FileService extends MasterService {
	public static final String IMPORT_FILE = "{call PKG_TTS_TRUONG.importFile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static final String UPDATE_FILE = "{call PKG_TTS_TRUONG.updateImportFile(?,?,?,?,?,?,?,?)}";
	public static final String GET_FILE_BY_FILE_ID = "{call PKG_TTS_TRUONG.getByFileId(?,?,?,?)}";
	public static final String GET_BY_VARIANT_ID = "{call PKG_TTS_TRUONG.getByVariantId(?,?,?,?)}";
	public static final String GET_BY_SKU = "{call PKG_TTS_TRUONG.getBySKU(?,?,?,?)}";
	public static final String INSERT_SHIPPING = "{call PKG_TTS_TRUONG.insertShipping(?,?,?,?,?,?,?,?,?,?,?)}";
	public static final String INSERT_FILE = "{call PACKAGE_IMPORT_FILE.insertFile(?,?,?,?,?,?,?,?,?,?,?,?)}";
	public static final String GET_IMPORT_FILE = "{call PACKAGE_IMPORT_FILE.getDataFile(?,?,?)}";
	public static final String UPDATE_IMPORT_FILE = "{call PACKAGE_IMPORT_FILE.updateDataFie(?,?,?,?,?)}";
	
	public static Map updateImportFile(String file_id, String state) throws SQLException {
		return searchOne(UPDATE_IMPORT_FILE, new Object[] {file_id, state});
	}

	public static Map getImportFile() throws SQLException {
		Map result = excuteQuery1(GET_IMPORT_FILE, new Object[] {});
//		LOGGER.info("Users result: " +result);
//		System.out.println("data"+result);
		return searchOne(GET_IMPORT_FILE, new Object[] {});
	}

	public static Map insertFile(String id, String file_name, String url, String type, String user_id, String store_id,
			String state, String error_note, String source) throws SQLException {
		Map result = searchOne(INSERT_FILE,
				new Object[] { id, file_name, url, type, user_id, store_id, state, error_note, source });
		return result;
	}

	public static Map insertShipping(String id, String name, String email, String phone, String add_line1,
			String add_line2, String add_city, String country_code) throws SQLException {
		Map result = excuteQuery1(INSERT_SHIPPING,
				new Object[] { id, name, email, phone, add_line1, add_line2, add_city, country_code });
		return result;
	}

	public static Map getByVariantId(String id) throws SQLException {
		Map result = excuteQuery1(GET_BY_VARIANT_ID, new Object[] { id });
		return result;
	}

	public static Map getBySKU(String id) throws SQLException {
		Map result = excuteQuery1(GET_BY_SKU, new Object[] { id });
		return result;
	}

	public static List<Map> getDataImportFile(String file_id) throws SQLException {
		List<Map> result = excuteQuery(GET_FILE_BY_FILE_ID, new Object[] { file_id });
		return result;
	}

	public static Map importFile(String id, String file_id, String user_id, String store_id, String name, /*
																											 * String
																											 * order_id,
																											 */
			String email, String shipping_name, String shipping_street, String shipping_address1,
			String shipping_address2, String shipping_company, String shipping_city, String shipping_zip,
			String shipping_province, String shipping_country, String shipping_phone, String notes,
			String financial_status, String lineitem_quantity, String lineitem_name, String lineitem_sku,
			String design_front_url, String design_back_url, String mockup_front_url, String mockup_back_url,
			String check_vaild_adress, String source, String state, /* Date paided, */ /*
																						 * String state, String
																						 * error_note,
																						 */
			String type, String file_name, /* int reprocess, */ String shipping_method, String currency,
			String unit_amount, String location, String group_column) throws SQLException, OracleException {

		Map resultMap = new HashMap<>();
		Map resultDataList = searchOne(IMPORT_FILE,
				new Object[] { id, file_id, user_id, store_id, name, /* order_id, */ email, shipping_name,
						shipping_street, shipping_address1, shipping_address2, shipping_company, shipping_city,
						shipping_zip, shipping_province, shipping_country, shipping_phone, notes, financial_status,
						lineitem_quantity, lineitem_name, lineitem_sku, design_front_url, design_back_url,
						mockup_front_url, mockup_back_url, check_vaild_adress, source, state,
						/* paided, */ /* state, error_note, */ type, file_name, /* reprocess, */ shipping_method,
						currency, unit_amount, location, group_column });
		return resultDataList;
	}

	public static Map updateImportFile(String id, String order_id, String state, String error_note, int reprocess)
			throws SQLException {
		Map result = searchOne(UPDATE_FILE, new Object[] { id, order_id, state, error_note, reprocess });
		return null;

	}

	public static Map format(Map inputMap) {
		Map resultMap = new LinkedHashMap();

		resultMap.put(AppParams.ID, ParamUtil.getString(inputMap, AppParams.S_ID));
		resultMap.put(AppParams.FILE_NAME, ParamUtil.getString(inputMap, AppParams.S_FILE_NAME));
		resultMap.put(AppParams.URL, ParamUtil.getString(inputMap, AppParams.S_URL));
		resultMap.put(AppParams.TYPE, ParamUtil.getString(inputMap, AppParams.S_TYPE));
		resultMap.put(AppParams.USER_ID, ParamUtil.getString(inputMap, AppParams.S_USER_ID));
		resultMap.put(AppParams.STORE_ID, ParamUtil.getString(inputMap, AppParams.S_STORE_ID));
		resultMap.put(AppParams.CREATE_AT, ParamUtil.getString(inputMap, AppParams.D_CREATE));
		resultMap.put(AppParams.STATE, ParamUtil.getString(inputMap, AppParams.S_STATE));
		resultMap.put(AppParams.ERROR_NOTE, ParamUtil.getString(inputMap, AppParams.S_ERROR_NOTE));
		resultMap.put(AppParams.SOURCE, ParamUtil.getString(inputMap, AppParams.S_SOURCE));

		return resultMap;
	}

}
