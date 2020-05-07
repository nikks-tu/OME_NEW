package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyProfileMainObject {

    @SerializedName("result")
    @Expose
    private List<MyProfileResultObject> result = null;
    @SerializedName("info")
    @Expose
    private MyProfileInfoObject info;

    public List<MyProfileResultObject> getResult() {
        return result;
    }

    public void setResult(List<MyProfileResultObject> result) {
        this.result = result;
    }

    public MyProfileInfoObject getInfo() {
        return info;
    }

    public void setInfo(MyProfileInfoObject info) {
        this.info = info;
    }

}