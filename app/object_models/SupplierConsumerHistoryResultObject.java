package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SupplierConsumerHistoryResultObject {

    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("ordered_on")
    @Expose
    private String orderedOn;
    @SerializedName("po_id")
    @Expose
    private String poId;
    @SerializedName("address_1")
    @Expose
    private String address1;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("address_2")
    @Expose
    private String address2;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("order_by_name")
    @Expose
    private String orderByName;
    @SerializedName("products")
    @Expose
    private ArrayList<SupplierConsumerHistoryProductObject> products = null;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(String orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOrderByName() {
        return orderByName;
    }

    public void setOrderByName(String orderByName) {
        this.orderByName = orderByName;
    }

    public ArrayList<SupplierConsumerHistoryProductObject> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<SupplierConsumerHistoryProductObject> products) {
        this.products = products;
    }

}