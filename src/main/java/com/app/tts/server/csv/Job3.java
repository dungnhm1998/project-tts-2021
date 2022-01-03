package com.app.tts.server.csv;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.app.tts.encode.Md5Code;
import com.app.tts.server.handler.ucant.ImportOrder;
import com.app.tts.services.BaseService;
import com.app.tts.util.ParamUtil;

public class Job3 extends QuartzJobBean{
	
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
	static final String fileId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Map obj = ImportOrder.json;
		Map csv = read();
		String userId = "UserB";
		String storeId = "112358";
    	String fileName = ParamUtil.getString(obj, "file_name");
    	String id = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    	String orderId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    	String name = ParamUtil.getString(csv, "Name");		
		String email = ParamUtil.getString(csv, "Email");
		String financialStatus = ParamUtil.getString(csv, "Financial Status");
		String createdAt = ParamUtil.getString(csv, "Created at");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Date created = null;
	    try {
	      created = formatter.parse(createdAt);
	    } catch (ParseException e1) {
	      e1.printStackTrace();
	    }
		
		String lineitemQuantity = ParamUtil.getString(csv, "Lineitem quantity");
		String lineitemName = ParamUtil.getString(csv, "Lineitem name");
		String lineitemSku = ParamUtil.getString(csv, "Lineitem sku");
		String shippingName = ParamUtil.getString(csv, "Shipping Name");
		String shippingStreet = ParamUtil.getString(csv, "Shipping Street");
		String shippingAddress1 = ParamUtil.getString(csv, "Shipping Address1");
		String shippingAddress2 = ParamUtil.getString(csv, "Shipping Address2");
		String shippingCompany = ParamUtil.getString(csv, "Shipping Company");
		String shippingCity = ParamUtil.getString(csv, "Shipping City");
		String shippingZip = ParamUtil.getString(csv, "Shipping Zip");
		String shippingProvince = ParamUtil.getString(csv, "Shipping Province");
		String shippingCountry = ParamUtil.getString(csv, "Shipping Country");
		String shippingPhone = ParamUtil.getString(csv, "Shipping Phone");
		String shippingMethod = ParamUtil.getString(csv, "Shipping method");
		String notes = ParamUtil.getString(csv, "Notes");
		String designFrontUrl = ParamUtil.getString(csv, "Design front url");
		String designBackUrl = ParamUtil.getString(csv, "Design back url");
		String mockupFrontUrl = ParamUtil.getString(csv, "Mockup front url");
		String mockupBackUrl = ParamUtil.getString(csv, "Design back url");
		String checkValidAddress = ParamUtil.getString(csv, "Check vaild adress");
		String currency = ParamUtil.getString(csv, "currency");
		String unitAmount = ParamUtil.getString(csv, "Unit amount");
		String location = ParamUtil.getString(csv, "Location");
		String state = "created";
		String groupColumn = Md5Code.md5(fileId);
		try {
			List<Map> order = BaseService.importOrder(fileId, fileName, userId, orderId, name, email, financialStatus, created, lineitemQuantity, 
					lineitemName, lineitemSku, shippingName, shippingStreet, shippingAddress1, shippingAddress2, shippingCompany, 
					shippingCity, shippingZip, shippingProvince, shippingCountry, shippingPhone, shippingMethod, notes, id, designFrontUrl, 
					designBackUrl, mockupFrontUrl, mockupBackUrl, checkValidAddress, currency, unitAmount, location, state, groupColumn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
