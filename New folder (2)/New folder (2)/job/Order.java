package com.app.tts.server.job;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import lombok.Data;

@Data
public class Order {
	@CsvBindByName(column = "ORDER")
	  String ORDER;
	
	@CsvBindByName(column = "REF")
	  String REF;
	
	@CsvBindByName(column = "CREATE DATE")
	  String CREATE_DATE;
	
	@CsvBindByName(column = "PAYMENT DATE")
	  String PAYMENT_DATE;
	
	@CsvBindByName(column = "PRODUCT NAME")
	 String PRODUCT_NAME;
	
	@CsvBindByName(column = "CUSTOMERS")
	  String CUSTOMERS;
	
	@CsvBindByName(column = "QUANTITY")
	  String QUANTITY;
	
	@CsvBindByName(column = "AMOUNT")
	  String AMOUNT;
	
	@CsvBindByName(column = "SHIPPING METHOD")
	  String SHIPPING_METHOD;
	
	@CsvBindByName(column = "STATE")
	  String STATE;
	
	@CsvBindByName(column = "FULL FILL")
	  String FULFILL_STATE;
	
	@CsvBindByName(column = "TRACKING")
	  String TRACKING;
	
	@CsvBindByName(column = "COUNTRY")
	  String COUNTRY;
	
	@CsvBindByName(column = "ZIPCODE")
	  String ZIPCODE;
	@Override
	public String toString() {
		return "Order [ORDER=" + ORDER + ", REF=" + REF + ", CREATE_DATE=" + CREATE_DATE + ", PAYMENT_DATE="
				+ PAYMENT_DATE + ", PRODUCT_NAME=" + PRODUCT_NAME + ", CUSTOMERS=" + CUSTOMERS + ", QUANTITY="
				+ QUANTITY + ", AMOUNT=" + AMOUNT + ", SHIPPING_METHOD=" + SHIPPING_METHOD + ", STATE=" + STATE
				+ ", FULFILL_STATE=" + FULFILL_STATE + ", TRACKING=" + TRACKING + ", COUNTRY=" + COUNTRY + ", ZIPCODE="
				+ ZIPCODE + "]";
	}
	
	
}
