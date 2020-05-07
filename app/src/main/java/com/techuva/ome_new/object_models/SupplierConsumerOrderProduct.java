package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierConsumerOrderProduct {

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
    public String productDescription;
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
    @SerializedName("ordered_by")
    @Expose
    public Integer orderedBy;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("mobile_no")
    @Expose
    public String mobileNo;
    @SerializedName("city_name")
    @Expose
    public String cityName;
    @SerializedName("state_name")
    @Expose
    public String stateName;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("status_id")
    @Expose
    public Integer statusId;
    @SerializedName("pin_code")
    @Expose
    public Integer pinCode;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String checked;

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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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

    public Integer getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(Integer orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }


}