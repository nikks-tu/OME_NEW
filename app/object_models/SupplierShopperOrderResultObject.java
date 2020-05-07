package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierShopperOrderResultObject {

    @SerializedName("demand_order_id")
    @Expose
    public Integer demandOrderId;
    @SerializedName("order_number")
    @Expose
    public Integer orderNumber;
    @SerializedName("quantity")
    @Expose
    public Integer quantity;
    @SerializedName("total_amount")
    @Expose
    public Integer totalAmount;
    @SerializedName("received_quantity")
    @Expose
    public Integer receivedQuantity;
    @SerializedName("product_id")
    @Expose
    public Integer productId;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("display_name")
    @Expose
    public String productDescription;
    @SerializedName("status_short_code")
    @Expose
    public String statusShortCode;
    @SerializedName("status_description")
    @Expose
    public String statusDescription;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("sub_category_name")
    @Expose
    public String subCategoryName;
    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("company_name")
    @Expose
    public String companyName;
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

    @SerializedName("shopper_company_name")
    @Expose
    public String shopper_company_name;
    @SerializedName("address")
    @Expose
    public Object address;
    @SerializedName("pin_code")
    @Expose
    public Integer pinCode;
    @SerializedName("status_id")
    @Expose
    public Integer statusId;
    @Expose
    public Integer dispachedQty;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String checked;

    public Integer getDemandOrderId() {
        return demandOrderId;
    }

    public void setDemandOrderId(Integer demandOrderId) {
        this.demandOrderId = demandOrderId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getStatusShortCode() {
        return statusShortCode;
    }

    public void setStatusShortCode(String statusShortCode) {
        this.statusShortCode = statusShortCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
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

    public Integer getDispachedQty() {
        return dispachedQty;
    }

    public void setDispachedQty(Integer dispachedQty) {
        this.dispachedQty = dispachedQty;
    }

    public String getShopper_company_name() {
        return shopper_company_name;
    }

    public void setShopper_company_name(String shopper_company_name) {
        this.shopper_company_name = shopper_company_name;
    }


}