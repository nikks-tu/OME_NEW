package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SupplierConsumerOrderMainObject {

    @SerializedName("result")
    @Expose
    private ArrayList<SupplierConsumerOrderResultObject> result = null;
    @SerializedName("info")
    @Expose
    private SupplierConsumerOrderInfoObject info;

    public ArrayList<SupplierConsumerOrderResultObject> getResult() {
        return result;
    }

    public void setResult(ArrayList<SupplierConsumerOrderResultObject> result) {
        this.result = result;
    }

    public SupplierConsumerOrderInfoObject getInfo() {
        return info;
    }

    public void setInfo(SupplierConsumerOrderInfoObject info) {
        this.info = info;
    }

}