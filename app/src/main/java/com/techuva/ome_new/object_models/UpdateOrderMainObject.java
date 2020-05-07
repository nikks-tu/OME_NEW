package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateOrderMainObject {

    @SerializedName("result")
    @Expose
    private List<Object> result = null;
    @SerializedName("info")
    @Expose
    private UpdateOrderInfoObject info;

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    public UpdateOrderInfoObject getInfo() {
        return info;
    }

    public void setInfo(UpdateOrderInfoObject info) {
        this.info = info;
    }

}