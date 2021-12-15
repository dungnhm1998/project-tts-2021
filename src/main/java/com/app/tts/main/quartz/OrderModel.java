package com.app.tts.main.quartz;

import com.opencsv.bean.CsvBindByPosition;

public class OrderModel {
    @CsvBindByPosition(position = 0)
    private String order;
    @CsvBindByPosition(position = 1)
    private String ref;
    @CsvBindByPosition(position = 2)
    private String createDate;

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

    public String getFullfillState() {
        return fullfillState;
    }

    public void setFullfillState(String fullfillState) {
        this.fullfillState = fullfillState;
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

    @CsvBindByPosition(position = 3)
    private String paymentDate;
    @CsvBindByPosition(position = 4)
    private String productName;
    @CsvBindByPosition(position = 5)
    private String customers;
    @CsvBindByPosition(position = 6)
    private String quantity;
    @CsvBindByPosition(position = 7)
    private String amount;
    @CsvBindByPosition(position = 8)
    private String shippingMethod;
    @CsvBindByPosition(position = 9)
    private String state;
    @CsvBindByPosition(position = 10)
    private String fullfillState;
    @CsvBindByPosition(position = 11)
    private String tracking;
    @CsvBindByPosition(position = 12)
    private String country;
    @CsvBindByPosition(position = 13)
    private String zipcode;





}
