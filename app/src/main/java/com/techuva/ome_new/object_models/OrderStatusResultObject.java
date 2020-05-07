package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderStatusResultObject {

    @SerializedName("last_modified_on")
    @Expose
    private String lastModifiedOn;
    @SerializedName("last_modified_by")
    @Expose
    private Integer lastModifiedBy;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("status_short_code")
    @Expose
    private String statusShortCode;
    @SerializedName("status_description")
    @Expose
    private String statusDescription;
    @SerializedName("status_group_id")
    @Expose
    private Integer statusGroupId;
    @SerializedName("status_group_name")
    @Expose
    private String statusGroupName;
    @SerializedName("status_for")
    @Expose
    private String statusFor;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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

    public Integer getStatusGroupId() {
        return statusGroupId;
    }

    public void setStatusGroupId(Integer statusGroupId) {
        this.statusGroupId = statusGroupId;
    }

    public String getStatusGroupName() {
        return statusGroupName;
    }

    public void setStatusGroupName(String statusGroupName) {
        this.statusGroupName = statusGroupName;
    }

    public String getStatusFor() {
        return statusFor;
    }

    public void setStatusFor(String statusFor) {
        this.statusFor = statusFor;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    @Override
    public String toString() {
        return getStatusDescription();
    }

}