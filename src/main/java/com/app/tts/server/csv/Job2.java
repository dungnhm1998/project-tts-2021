package com.app.tts.server.csv;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.services.BaseService;
import com.app.tts.services.SubService;
import com.app.tts.util.ParamUtil;

public class Job2 extends QuartzJobBean{
	static int i;
	static String regex1 = "^(.*[a-zA-Z0-9].*)[|](.*[a-zA-Z0-9].*)$";
	static String regex2 = "^[0-9]*[-][0-9]*[-][0-9]*$";
	
	public Map getOrder() throws SQLException {
		Map s = null;
		i = GetListServer.n;
		List<Map> output = GetListServer.getListOrder();
		if(i < output.size()) {
			s = output.get(i);
			GetListServer.n++;
		}
		return s;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			Map fileRow = getOrder();
			System.out.println(fileRow);
			String lineitemSku = ParamUtil.getString(fileRow, "S_LINEITEM_SKU");
			System.out.println(lineitemSku);
			Map variantOrSku = new HashMap();
			Map prices = new HashMap();
			Map order = new HashMap();
			Map orderProduct = new HashMap();
			Map shipping = new HashMap();
			
			if (lineitemSku.matches(regex1)) {
				String[] part = lineitemSku.split("\\|");
				String variantId = part[0];
				String sizeId = part[1];
				variantOrSku = BaseService.selectVariant(variantId);
				String id = ParamUtil.getString(variantOrSku, "S_ID");
				if (!(id == null)) {
					String baseId = ParamUtil.getString(variantOrSku, "S_BASE_ID"); 
					prices = BaseService.selectPrice(baseId, sizeId);
					
					String orderId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
					String note = ParamUtil.getString(fileRow, "S_NOTE");
					String currency = ParamUtil.getString(fileRow, "S_CURRENCY");
					String createAt = ParamUtil.getString(fileRow, "D_CREATE");
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				    Date created = null;
				    try {
				      created = formatter.parse(createAt);
				    } catch (ParseException e1) {
				      e1.printStackTrace();
				    }
					String orderProductId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
					String colorId = ParamUtil.getString(variantOrSku, "S_COLOR_ID");
					String colorValue = ParamUtil.getString(variantOrSku, "S_COLOR_VALUE");
					String price = ParamUtil.getString(prices, "S_PRICE");
					String frontDesignUrl = ParamUtil.getString(variantOrSku, "S_IMAGE_ID");
					String backDesignUrl = ParamUtil.getString(fileRow, "S_DESIGN_BACK_URL");
					String mockupFrontUrl = ParamUtil.getString(fileRow, "S_MOCKUP_FRONT_URL");
					String mockupBackUrl = ParamUtil.getString(fileRow, "S_MOCKUP_BACK_URL");
					String VariantName = ParamUtil.getString(fileRow, "S_LINEITEM_NAME");
					String unitAmount = ParamUtil.getString(fileRow, "S_UNIT_AMOUNT");
					int quantity = ParamUtil.getInt(fileRow, "S_LINEITEM_QUANTITY");
					
//					order = BaseService.insertDropshipOrder(orderId, note, currency, created);
//					orderProduct = BaseService.insertDropshipOrderProduct(orderProductId, baseId, colorId, colorValue, sizeId, price, 
//							frontDesignUrl, backDesignUrl, mockupFrontUrl, mockupBackUrl, VariantName, currency, unitAmount, quantity, created);
				}else {
					System.out.println("VariantId: " + id + "không tồn tại");
				}
			}
			if(lineitemSku.matches(regex2)) {
				variantOrSku = BaseService.selectSku(lineitemSku);
				String baseId = ParamUtil.getString(variantOrSku, "S_BASE_ID");
				String sizeId = ParamUtil.getString(variantOrSku, "S_SIZE_ID");
				
				String orderId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
				String note = ParamUtil.getString(fileRow, "S_NOTE");
				String currency = ParamUtil.getString(fileRow, "S_CURRENCY");
				String createAt = ParamUtil.getString(fileRow, "D_CREATE");
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			    Date created = null;
			    try {
			      created = formatter.parse(createAt);
			    } catch (ParseException e1) {
			      e1.printStackTrace();
			    }
			    
			    String orderProductId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
				String colorId = ParamUtil.getString(variantOrSku, "S_COLOR_ID");
				String colorValue = ParamUtil.getString(variantOrSku, "S_COLOR_VALUE");
				String price = ParamUtil.getString(variantOrSku, "S_PRICE");
				String frontDesignUrl = ParamUtil.getString(fileRow, "S_DESIGN_FRONT_URL");
				String backDesignUrl = ParamUtil.getString(fileRow, "S_DESIGN_BACK_URL");
				String mockupFrontUrl = ParamUtil.getString(fileRow, "S_MOCKUP_FRONT_URL");
				String mockupBackUrl = ParamUtil.getString(fileRow, "S_MOCKUP_BACK_URL");
				String VariantName = ParamUtil.getString(fileRow, "S_LINEITEM_NAME");
				String unitAmount = ParamUtil.getString(fileRow, "S_UNIT_AMOUNT");
				int quantity = ParamUtil.getInt(fileRow, "S_LINEITEM_QUANTITY");
				
//				order = SubService.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
