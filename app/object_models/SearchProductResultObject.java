package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchProductResultObject implements Serializable {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("shopper_price")
    @Expose
    private Integer shopperPrice;
    @SerializedName("consumer_price")
    @Expose
    private Integer consumerPrice;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("product_short_desc")
    @Expose
    private String productShortDesc;
    @SerializedName("display_name")
    @Expose
    private String productDescription;
    @SerializedName("product_description")
    @Expose
    private String productDetails;
    @SerializedName("category_type_id")
    @Expose
    private Integer categoryTypeId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("sub_category_id")
    @Expose
    private Integer subCategoryId;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("company_short_code")
    @Expose
    private String companyShortCode;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getShopperPrice() {
        return shopperPrice;
    }

    public void setShopperPrice(Integer shopperPrice) {
        this.shopperPrice = shopperPrice;
    }

    public Integer getConsumerPrice() {
        return consumerPrice;
    }

    public void setConsumerPrice(Integer consumerPrice) {
        this.consumerPrice = consumerPrice;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Integer getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Integer categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
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

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

}