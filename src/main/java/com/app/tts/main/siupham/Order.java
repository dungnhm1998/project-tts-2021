package com.app.tts.main.siupham;

import com.opencsv.bean.CsvBindByName;


public class Order {
    @CsvBindByName(column = "ORDER", required = true)
    private String order;
    @CsvBindByName(column = "REF")
    private String ref;
    @CsvBindByName(column = "PAYMENT DATE")
    private String createDate;
    @CsvBindByName(column = "PAYMENT DATE")
    private String paymentDate;
    @CsvBindByName(column = "PRODUCT NAME")
    private String productName;
    @CsvBindByName(column = "CUSTOMERS")
    private String customers;
    @CsvBindByName(column = "QUANTITY")
    private String quantity;
    @CsvBindByName(column = "AMOUNT")
    private String amount;
    @CsvBindByName(column = "SHIPPING METHOD")
    private String shippingMethod;
    @CsvBindByName(column = "STATE")
    private String state;
    @CsvBindByName(column = "FULFILL STATE")
    private String fulfillState;
    @CsvBindByName(column = "TRACKING")
    private String tracking;
    @CsvBindByName(column = "COUNTRY")
    private String country;
    @CsvBindByName()
    private String zipcode;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFulfillState() {
        return fulfillState;
    }

    public void setFulfillState(String fulfillState) {
        this.fulfillState = fulfillState;
    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order='" + order + '\'' +
                ", ref='" + ref + '\'' +
                ", createDate='" + createDate + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", productName='" + productName + '\'' +
                ", customers='" + customers + '\'' +
                ", quantity='" + quantity + '\'' +
                ", amount='" + amount + '\'' +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", state='" + state + '\'' +
                ", fulfillState='" + fulfillState + '\'' +
                ", tracking='" + tracking + '\'' +
                ", country='" + country + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
