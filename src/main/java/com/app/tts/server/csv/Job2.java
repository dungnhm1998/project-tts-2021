package com.app.tts.server.csv;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.services.BaseService;
import com.app.tts.util.ParamUtil;

public class Job2 extends QuartzJobBean {
	static int i;
	static String regex1 = "^(.*[a-zA-Z0-9].*)[|](.*[a-zA-Z0-9].*)$";
	static String regex2 = "^[0-9]*[-][0-9]*[-][0-9]*$";

	public void getOrder() throws SQLException {
		List<Map> orders = BaseService.getOrder();
		Map order = new HashMap();
		Map shipping = new HashMap();
		Map variantOrSku = new HashMap();
		Map prices = new HashMap();
		Map orderProduct = new HashMap();
		Map state = new HashMap();

		Map item = orders.get(0);
		String orderId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
		String note = ParamUtil.getString(item, "S_NOTE");
		String currency = ParamUtil.getString(item, "S_CURRENCY");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String shippingId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
		String email = ParamUtil.getString(item, "S_EMAIL");
		String ShippingName = ParamUtil.getString(item, "S_SHIPPING_NAME");
		String ShippingAddress1 = ParamUtil.getString(item, "S_SHIPPING_ADDRESS1");
		String ShippingAddress2 = ParamUtil.getString(item, "S_SHIPPING_ADDRESS2");
		String ShippingCountry = ParamUtil.getString(item, "S_SHIPPING_COUNTRY");
		String ShippingPhone = ParamUtil.getString(item, "S_SHIPPING_PHONE");
		String ShippingZip = ParamUtil.getString(item, "S_SHIPPING_ZIP");

		order = BaseService.insertDropshipOrder(orderId, note, currency);
		shipping = BaseService.insertShipping(shippingId, email, ShippingName, ShippingAddress1, ShippingAddress2,
				ShippingCountry, ShippingPhone, ShippingZip);

		for (Map map : orders) {
			String sku = ParamUtil.getString(map, "S_LINEITEM_SKU");
			String itemId = ParamUtil.getString(map, "S_ID");
			if (sku.matches(regex1)) {
				String[] part = sku.split("\\|");
				String variantId = part[0];
				String sizeId1 = part[1];
				variantOrSku = BaseService.selectVariant(variantId);
				if (!(variantOrSku == null)) {
					String baseId = ParamUtil.getString(variantOrSku, "S_BASE_ID");
					prices = BaseService.selectPrice(baseId, sizeId1);
					String orderProductId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
					String colorId = ParamUtil.getString(variantOrSku, "S_COLOR_ID");
					String colorValue = ParamUtil.getString(variantOrSku, "S_COLOR_VALUE");
					String price = ParamUtil.getString(prices, "S_PRICE");
					String frontDesignUrl = ParamUtil.getString(variantOrSku, "S_IMAGE_ID");
					String backDesignUrl = ParamUtil.getString(map, "S_DESIGN_BACK_URL");
					String mockupFrontUrl = ParamUtil.getString(map, "S_MOCKUP_FRONT_URL");
					String mockupBackUrl = ParamUtil.getString(map, "S_MOCKUP_BACK_URL");
					String VariantName = ParamUtil.getString(map, "S_LINEITEM_NAME");
					String unitAmount = ParamUtil.getString(map, "S_UNIT_AMOUNT");
					int quantity = ParamUtil.getInt(map, "S_LINEITEM_QUANTITY");
					orderProduct = BaseService.insertDropshipOrderProduct(orderProductId, baseId, colorId, colorValue,
							sizeId1, price, frontDesignUrl, backDesignUrl, mockupFrontUrl, mockupBackUrl, VariantName,
							currency, unitAmount, quantity);
				} else {
					System.out.println("VariantId " + variantId + " không tồn tại");
				}
			}else if (sku.matches(regex2)) {
				variantOrSku = BaseService.selectSku(sku);
				if (!(variantOrSku == null)) {
					String baseId = ParamUtil.getString(variantOrSku, "S_BASE_ID");
					String sizeId2 = ParamUtil.getString(variantOrSku, "S_SIZE_ID");
					String orderProductId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
					String colorId = ParamUtil.getString(variantOrSku, "S_COLOR_ID");
					String colorValue = ParamUtil.getString(variantOrSku, "S_COLOR_VALUE");
					String price = ParamUtil.getString(variantOrSku, "S_PRICE");
					String frontDesignUrl = ParamUtil.getString(map, "S_DESIGN_FRONT_URL");
					String backDesignUrl = ParamUtil.getString(map, "S_DESIGN_BACK_URL");
					String mockupFrontUrl = ParamUtil.getString(map, "S_MOCKUP_FRONT_URL");
					String mockupBackUrl = ParamUtil.getString(map, "S_MOCKUP_BACK_URL");
					String VariantName = ParamUtil.getString(map, "S_LINEITEM_NAME");
					String unitAmount = ParamUtil.getString(map, "S_UNIT_AMOUNT");
					int quantity = ParamUtil.getInt(map, "S_LINEITEM_QUANTITY");
					orderProduct = BaseService.insertDropshipOrderProduct(orderProductId, baseId, colorId, colorValue,
							sizeId2, price, frontDesignUrl, backDesignUrl, mockupFrontUrl, mockupBackUrl, VariantName,
							currency, unitAmount, quantity);
				} else {
					System.out.println("Sku " + sku + "không tồn tại");
				}
			}
			System.out.println(itemId);
			String State = "approved";
			System.out.println("data" + BaseService.getOrder());
			BaseService.updateState(itemId, State);
			System.out.println(sku);
			System.out.println("product: " + orderProduct);
		}
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			getOrder();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
