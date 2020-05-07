package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateListMainObject {

    @SerializedName("result")
    @Expose
    private List<StateListResultObject> result = null;
    @SerializedName("info")
    @Expose
    private StateListInfoObject info;

    public List<StateListResultObject> getResult() {
        return result;
    }

    public void setResult(List<StateListResultObject> result) {
        this.result = result;
    }

    public StateListInfoObject getInfo() {
        return info;
    }

    public void setInfo(StateListInfoObject info) {
        this.info = info;
    }

}