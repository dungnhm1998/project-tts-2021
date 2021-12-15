package com.app.tts.main.siupham;

import com.opencsv.bean.CsvBindByPosition;


public class Order {
    @CsvBindByPosition(position = 0)
     String ORDER;
    @CsvBindByPosition(position = 1)
     String REF;
    @CsvBindByPosition(position = 2)
     String CREATEDATE;
    @CsvBindByPosition(position = 3)
     String PAYMENTDATE;
    @CsvBindByPosition(position = 4)
     String PRODUCTNAME;
    @CsvBindByPosition(position = 5)
     String CUSTOMERS;
    @CsvBindByPosition(position = 6)
     String QUANTITY;
    @CsvBindByPosition(position = 7)
     String AMOUNT;
    @CsvBindByPosition(position = 8)
     String SHIPPINGMETHOD;
    @CsvBindByPosition(position = 9)
     String STATE;
    @CsvBindByPosition(position = 10)
     String FULFILLSTATE;
    @CsvBindByPosition(position = 11)
     String TRACKING;
    @CsvBindByPosition(position = 12)
     String COUNTRY;
    @CsvBindByPosition(position = 13)
     String ZIPCODE;

    public String getORDER() {
        return ORDER;
    }

    public void setORDER(String ORDER) {
        this.ORDER = ORDER;
    }

    public String getREF() {
        return REF;
    }

    public void setREF(String REF) {
        this.REF = REF;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getPAYMENTDATE() {
        return PAYMENTDATE;
    }

    public void setPAYMENTDATE(String PAYMENTDATE) {
        this.PAYMENTDATE = PAYMENTDATE;
    }

    public String getPRODUCTNAME() {
        return PRODUCTNAME;
    }

    public void setPRODUCTNAME(String PRODUCTNAME) {
        this.PRODUCTNAME = PRODUCTNAME;
    }

    public String getCUSTOMERS() {
        return CUSTOMERS;
    }

    public void setCUSTOMERS(String CUSTOMERS) {
        this.CUSTOMERS = CUSTOMERS;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getSHIPPINGMETHOD() {
        return SHIPPINGMETHOD;
    }

    public void setSHIPPINGMETHOD(String SHIPPINGMETHOD) {
        this.SHIPPINGMETHOD = SHIPPINGMETHOD;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getFULFILLSTATE() {
        return FULFILLSTATE;
    }

    public void setFULFILLSTATE(String FULFILLSTATE) {
        this.FULFILLSTATE = FULFILLSTATE;
    }

    public String getTRACKING() {
        return TRACKING;
    }

    public void setTRACKING(String TRACKING) {
        this.TRACKING = TRACKING;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    public String getZIPCODE() {
        return ZIPCODE;
    }

    public void setZIPCODE(String ZIPCODE) {
        this.ZIPCODE = ZIPCODE;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ORDER='" + ORDER + '\'' +
                ", REF='" + REF + '\'' +
                ", CREATEDATE='" + CREATEDATE + '\'' +
                ", PAYMENTDATE='" + PAYMENTDATE + '\'' +
                ", PRODUCTNAME='" + PRODUCTNAME + '\'' +
                ", CUSTOMERS='" + CUSTOMERS + '\'' +
                ", QUANTITY='" + QUANTITY + '\'' +
                ", AMOUNT='" + AMOUNT + '\'' +
                ", SHIPPINGMETHOD='" + SHIPPINGMETHOD + '\'' +
                ", STATE='" + STATE + '\'' +
                ", FULFILLSTATE='" + FULFILLSTATE + '\'' +
                ", TRACKING='" + TRACKING + '\'' +
                ", COUNTRY='" + COUNTRY + '\'' +
                ", ZIPCODE='" + ZIPCODE + '\'' +
                '}';
    }
}
