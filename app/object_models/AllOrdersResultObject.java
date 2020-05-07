package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllOrdersResultObject {

    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("category_type_id")
    @Expose
    private Integer categoryTypeId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("demand_order_id")
    @Expose
    private Integer demandOrderId;
    @SerializedName("status_name")
    @Expose
    private String statusName;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("DealerName")
    @Expose
    private String dealerName;
    @SerializedName("dealer_id")
    @Expose
    private Integer dealerId;
    @SerializedName("retailer_id")
    @Expose
    private Integer retailerId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("received_on")
    @Expose
    private String received_on;
    @SerializedName("image_url")
    @Expose
    private String image_url;

    @SerializedName("display_name")
    @Expose
    private String display_name;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Integer categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getDemandOrderId() {
        return demandOrderId;
    }

    public void setDemandOrderId(Integer demandOrderId) {
        this.demandOrderId = demandOrderId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReceived_on() {
        return received_on;
    }

    public void setReceived_on(String received_on) {
        this.received_on = received_on;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

}