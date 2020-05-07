package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardImgMainObject {

    @SerializedName("result")
    @Expose
    private List<DashboardImgResutObject> result = null;
    @SerializedName("info")
    @Expose
    private DashboardImgInfoObject info;

    public List<DashboardImgResutObject> getResult() {
        return result;
    }

    public void setResult(List<DashboardImgResutObject> result) {
        this.result = result;
    }

    public DashboardImgInfoObject getInfo() {
        return info;
    }

    public void setInfo(DashboardImgInfoObject info) {
        this.info = info;
    }

}