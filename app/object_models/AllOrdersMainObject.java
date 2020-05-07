package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllOrdersMainObject {

    @SerializedName("result")
    @Expose
    private List<ShopperOrdersResultObject> result = null;
    @SerializedName("info")
    @Expose
    private AllOrdersInfoObject info;

    public List<ShopperOrdersResultObject> getResult() {
        return result;
    }

    public void setResult(List<ShopperOrdersResultObject> result) {
        this.result = result;
    }

    public AllOrdersInfoObject getInfo() {
        return info;
    }

    public void setInfo(AllOrdersInfoObject info) {
        this.info = info;
    }

}