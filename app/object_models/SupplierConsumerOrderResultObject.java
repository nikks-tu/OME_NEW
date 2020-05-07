package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupplierConsumerOrderResultObject  {

    @SerializedName("ordered_on")
    @Expose
    private String orderedOn;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("po_id")
    @Expose
    private String poId;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("pin_code")
    @Expose
    private Integer pinCode;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("products")
    @Expose
    private List<SupplierConsumerOrderProduct> products = null;

    public String getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(String orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public List<SupplierConsumerOrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<SupplierConsumerOrderProduct> products) {
        this.products = products;
    }

}