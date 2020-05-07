package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvertisementObject {

    @SerializedName("advertisement_id")
    @Expose
    private Integer advertisementId;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("image_name")
    @Expose
    private String imageName;
    @SerializedName("image_desc")
    @Expose
    private String imageDesc;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("type_code")
    @Expose
    private String typeCode;

    public Integer getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Integer advertisementId) {
        this.advertisementId = advertisementId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

}