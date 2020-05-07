package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityListMainObject{

    @SerializedName("result")
    @Expose
    private CityListResultObject result;
    @SerializedName("info")
    @Expose
    private CityListInfoObject info;

    public CityListResultObject getResult() {
        return result;
    }

    public void setResult(CityListResultObject result) {
        this.result = result;
    }

    public CityListInfoObject getInfo() {
        return info;
    }

    public void setInfo(CityListInfoObject info) {
        this.info = info;
    }

}