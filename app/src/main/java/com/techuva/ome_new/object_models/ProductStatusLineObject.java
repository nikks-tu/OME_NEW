package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductStatusLineObject{

    @SerializedName("dealer_id")
    @Expose
    private Integer dealerId;
    @SerializedName("order_number")
    @Expose
    private Integer orderNumber;
    @SerializedName("retailer_id")
    @Expose
    private Integer retailerId;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("status_name")
    @Expose
    private String statusName;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("last_modified_by")
    @Expose
    private Integer lastModifiedBy;
    @SerializedName("dealer_name")
    @Expose
    private String dealerName;
    @SerializedName("retailer_name")
    @Expose
    private String retailerName;
    @SerializedName("last_modified_by_name")
    @Expose
    private String lastModifiedByName;
    @SerializedName("display_name")
    @Expose
    private String display_name;
    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    public void setLastModifiedByName(String lastModifiedByName) {
        this.lastModifiedByName = lastModifiedByName;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }


}