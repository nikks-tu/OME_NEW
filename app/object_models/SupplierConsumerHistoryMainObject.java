package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SupplierConsumerHistoryMainObject {

    @SerializedName("result")
    @Expose
    private ArrayList<SupplierConsumerHistoryResultObject> result = null;
    @SerializedName("info")
    @Expose
    private SupplierConsumerOrderInfoObject info;

    public ArrayList<SupplierConsumerHistoryResultObject> getResult() {
        return result;
    }

    public void setResult(ArrayList<SupplierConsumerHistoryResultObject> result) {
        this.result = result;
    }

    public SupplierConsumerOrderInfoObject getInfo() {
        return info;
    }

    public void setInfo(SupplierConsumerOrderInfoObject info) {
        this.info = info;
    }

}