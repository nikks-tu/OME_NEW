package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderStatusMainObject {

    @SerializedName("result")
    @Expose
    private List<OrderStatusResultObject> result = null;
    @SerializedName("info")
    @Expose
    private OrderStatusInfoObject info;

    public List<OrderStatusResultObject> getResult() {
        return result;
    }

    public void setResult(List<OrderStatusResultObject> result) {
        this.result = result;
    }

    public OrderStatusInfoObject getInfo() {
        return info;
    }

    public void setInfo(OrderStatusInfoObject info) {
        this.info = info;
    }

}