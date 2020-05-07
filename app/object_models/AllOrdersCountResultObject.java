package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllOrdersCountResultObject {

    @SerializedName("total_order_by_status")
    @Expose
    private String totalOrderByStatus;
    @SerializedName("status_group_name")
    @Expose
    private String statusGroupName;

    public String getTotalOrderByStatus() {
        return totalOrderByStatus;
    }

    public void setTotalOrderByStatus(String totalOrderByStatus) {
        this.totalOrderByStatus = totalOrderByStatus;
    }

    public String getStatusGroupName() {
        return statusGroupName;
    }

    public void setStatusGroupName(String statusGroupName) {
        this.statusGroupName = statusGroupName;
    }

}