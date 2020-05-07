package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierConsumerHistoryProductObject  {

    @SerializedName("order_id")
    @Expose
    public Integer orderId;
    @SerializedName("ordered_on")
    @Expose
    public String orderedOn;
    @SerializedName("quantity")
    @Expose
    public String quantity;
    @SerializedName("total_amount")
    @Expose
    public Integer totalAmount;
    @SerializedName("product_id")
    @Expose
    public Integer productId;
    @SerializedName("product_image")
    @Expose
    public String productImage;
    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("product_description")
    @Expose
    public String productDescription;
    @SerializedName("product_short_desc")
    @Expose
    public String productShortDesc;
    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("sub_category_name")
    @Expose
    public String subCategoryName;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("order_status")
    @Expose
    public String orderStatus;
    @SerializedName("status_short_code")
    @Expose
    public String statusShortCode;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("order_by_name")
    @Expose
    public String orderByName;
    @SerializedName("mobile_no")
    @Expose
    public String mobileNo;
    @SerializedName("address_1")
    @Expose
    public String address1;
    @SerializedName("address_2")
    @Expose
    public String address2;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(String orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductShortDesc() {
        return productShortDesc;
    }

    public void setProductShortDesc(String productShortDesc) {
        this.productShortDesc = productShortDesc;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStatusShortCode() {
        return statusShortCode;
    }

    public void setStatusShortCode(String statusShortCode) {
        this.statusShortCode = statusShortCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrderByName() {
        return orderByName;
    }

    public void setOrderByName(String orderByName) {
        this.orderByName = orderByName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

}