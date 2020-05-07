package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordMainObject {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("info")
    @Expose
    private ChangePasswordInfoObject info;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ChangePasswordInfoObject getInfo() {
        return info;
    }

    public void setInfo(ChangePasswordInfoObject info) {
        this.info = info;
    }

}