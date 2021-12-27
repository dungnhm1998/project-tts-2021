package com.app.tts.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BaseService extends MasterService {
	public static final String IMPORT_ORDER = "{call PKG_IMPORT_ORDER.import_order(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
	public static final String GET_FILE_ROWS = "{call PKG_IMPORT_ORDER.get_file_rows(?, ?, ?)}";
	public static final String SELECT_VARIANT = "{call PKG_IMPORT_ORDER.select_variant(?, ?, ?, ?)}";
	public static final String SELECT_DROPSHIP_SKU = "{call PKG_IMPORT_ORDER.select_dropship_sku(?,?,?,?)}";
	public static final String SELECT_PRICE = "{call PKG_IMPORT_ORDER.select_price(?, ?, ?, ?, ?)}";
	public static final String INSERT_DROPSHIP_ORDER = "{call PKG_IMPORT_ORDER.insert_dropship_order(?,?,?,?,?,?)}";
	public static final String INSERT_DROPSHIP_ORDER_PRODUCT = "{call PKG_IMPORT_ORDER.insert_dropship_order_product(?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
	public static final String INSERT_SHIPPING = "{call PKG_IMPORT_ORDER.insert_shipping(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
	public static final String UPDATE_STATE = "{call PKG_IMPORT_ORDER.update_state(?,?,?,?,?)}";

	public static List<Map> importOrder(String fileId, String fileName, String userId, String order_id, String name, String email,
			String financialStatus, Date create_at, String lineitemQuantity, String lineitemName, String lineitemSku,
			String shippingName, String shippingStreet, String shippingAddress1, String shippingAddress2,
			String shippingCompany, String shippingCity, String shippingZip, String shippingProvince,
			String shippingCountry, String shippingPhone, String shippingMethod, String notes, String id,
			String designFrontUrl, String designBackUrl, String mockupFrontUrl, String mockupBackUrl,
			String checkValidAddress, String currency, String unitAmount, String location, String state) throws SQLException {

		List<Map> result = excuteQuery(IMPORT_ORDER,
				new Object[] { fileId, fileName, userId, order_id, name, email, financialStatus, create_at, lineitemQuantity,
						lineitemName, lineitemSku, shippingName, shippingStreet, shippingAddress1, shippingAddress2,
						shippingCompany, shippingCity, shippingZip, shippingProvince, shippingCountry, shippingPhone,
						shippingMethod, notes, id, designFrontUrl, designBackUrl, mockupFrontUrl, mockupBackUrl,
						checkValidAddress, currency, unitAmount, location, state});
		return result;
	}

	public static List<Map> getOrder() throws SQLException {
		List<Map> result = excuteQuery(GET_FILE_ROWS, new Object[] {});
		return result;
	}

	public static Map selectVariant(String variantId) throws SQLException {
		Map result = searchOne(SELECT_VARIANT, new Object[] { variantId });
		return result;
	}

	public static Map selectSku(String sku) throws SQLException{
		Map result = searchOne(SELECT_DROPSHIP_SKU, new Object[] {sku});
		return result;
	}
	
	public static Map selectPrice(String baseId, String sizeId) throws SQLException {
		Map result = searchOne(SELECT_PRICE, new Object[] { baseId, sizeId });
		return result;
	}
	
	public static Map insertDropshipOrder(String id, String note, String currency) throws SQLException{
		Map result = searchOne(INSERT_DROPSHIP_ORDER, new Object[] {id, note, currency});
		return result;
	}

	public static Map insertDropshipOrderProduct(String id, String baseId, String colorId, String colorValue,
			String sizeId, String price, String url, String designBackUrl, String mockupFrontUrl, String mockupBackUrl,
			String variantName, String currency, String unitAmount, int quantity)
			throws SQLException {
		Map result = searchOne(INSERT_DROPSHIP_ORDER_PRODUCT,
				new Object[] { id, baseId, colorId, colorValue, sizeId, price, url, designBackUrl, mockupFrontUrl,
						mockupBackUrl, variantName, currency, unitAmount, quantity});
		return result;
	}

	public static Map insertShipping(String shippingId, String email, String shippingName,
			String shippingAddress1, String shippingAddress2, String shippingCountry, String shipingPhone,
			String shippingZip) throws SQLException {
		Map result = searchOne(INSERT_SHIPPING, new Object[] { shippingId, email, shippingName,
				shippingAddress1, shippingAddress2, shippingCountry, shipingPhone, shippingZip });
		return result;
	}
	
	public static Map updateState(String id, String state) throws SQLException{
		Map result = searchOne(UPDATE_STATE, new Object[] {id, state});
		return result;
	}

	private static final Logger LOGGER = Logger.getLogger(BaseService.class.getName());
}
