package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceOrderMainObject {

    @SerializedName("result")
    @Expose
    private List<PlaceOrderResultObject> result = null;
    @SerializedName("info")
    @Expose
    private PlaceOrderInfoObject info;

    public List<PlaceOrderResultObject> getResult() {
        return result;
    }

    public void setResult(List<PlaceOrderResultObject> result) {
        this.result = result;
    }

    public PlaceOrderInfoObject getInfo() {
        return info;
    }

    public void setInfo(PlaceOrderInfoObject info) {
        this.info = info;
    }

}