package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopperOrdersResultObject {

    @SerializedName("demand_order_id")
    @Expose
    private Integer demandOrderId;
    @SerializedName("order_number")
    @Expose
    private Integer orderNumber;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("received_quantity")
    @Expose
    private Integer receivedQuantity;
    @SerializedName("dispatched_date")
    @Expose
    private String dispatchedDate;
    @SerializedName("received_on")
    @Expose
    private String receivedOn;
    @SerializedName("company_short_code")
    @Expose
    private String companyShortCode;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("company_addr")
    @Expose
    private String companyAddr;
    @SerializedName("product_image")
    @Expose
    private String productImage ="";
    @SerializedName("product_short_desc")
    @Expose
    private String productShortDesc;
    @SerializedName("display_name")
    @Expose
    private String productDescription;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("status_short_code")
    @Expose
    private String statusShortCode;
    @SerializedName("status_description")
    @Expose
    private String statusDescription;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public String getDispatchedDate() {
        return dispatchedDate;
    }

    public void setDispatchedDate(String dispatchedDate) {
        this.dispatchedDate = dispatchedDate;
    }

    public String getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(String receivedOn) {
        this.receivedOn = receivedOn;
    }

    public String getCompanyShortCode() {
        return companyShortCode;
    }

    public void setCompanyShortCode(String companyShortCode) {
        this.companyShortCode = companyShortCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductShortDesc() {
        return productShortDesc;
    }

    public void setProductShortDesc(String productShortDesc) {
        this.productShortDesc = productShortDesc;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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

}