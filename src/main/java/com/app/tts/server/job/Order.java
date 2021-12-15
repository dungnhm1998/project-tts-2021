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

	public String getORDER() {
		return ORDER;
	}

	public void setORDER(String oRDER) {
		ORDER = oRDER;
	}

	public String getREF() {
		return REF;
	}

	public void setREF(String rEF) {
		REF = rEF;
	}

	public String getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getPAYMENT_DATE() {
		return PAYMENT_DATE;
	}

	public void setPAYMENT_DATE(String pAYMENT_DATE) {
		PAYMENT_DATE = pAYMENT_DATE;
	}

	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}

	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}

	public String getCUSTOMERS() {
		return CUSTOMERS;
	}

	public void setCUSTOMERS(String cUSTOMERS) {
		CUSTOMERS = cUSTOMERS;
	}

	public String getQUANTITY() {
		return QUANTITY;
	}

	public void setQUANTITY(String qUANTITY) {
		QUANTITY = qUANTITY;
	}

	public String getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public String getSHIPPING_METHOD() {
		return SHIPPING_METHOD;
	}

	public void setSHIPPING_METHOD(String sHIPPING_METHOD) {
		SHIPPING_METHOD = sHIPPING_METHOD;
	}

	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}

	public String getFULFILL_STATE() {
		return FULFILL_STATE;
	}

	public void setFULFILL_STATE(String fULFILL_STATE) {
		FULFILL_STATE = fULFILL_STATE;
	}

	public String getTRACKING() {
		return TRACKING;
	}

	public void setTRACKING(String tRACKING) {
		TRACKING = tRACKING;
	}

	public String getCOUNTRY() {
		return COUNTRY;
	}

	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}

	public String getZIPCODE() {
		return ZIPCODE;
	}

	public void setZIPCODE(String zIPCODE) {
		ZIPCODE = zIPCODE;
	}
	
	
}
