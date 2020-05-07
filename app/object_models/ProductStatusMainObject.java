package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductStatusMainObject {

    @SerializedName("result")
    @Expose
    private ProductStatusResultObject result;
    @SerializedName("info")
    @Expose
    private ProductStatusInfoObject info;

    public ProductStatusResultObject getResult() {
        return result;
    }

    public void setResult(ProductStatusResultObject result) {
        this.result = result;
    }

    public ProductStatusInfoObject getInfo() {
        return info;
    }

    public void setInfo(ProductStatusInfoObject info) {
        this.info = info;
    }

}