package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchProductMainObject{

    @SerializedName("result")
    @Expose
    private List<SearchProductResultObject> result = null;
    @SerializedName("info")
    @Expose
    private SearchProductInfoObject info;


    public List<SearchProductResultObject> getResult() {
        return result;
    }

    public void setResult(List<SearchProductResultObject> result) {
        this.result = result;
    }

    public SearchProductInfoObject getInfo() {
        return info;
    }

    public void setInfo(SearchProductInfoObject info) {
        this.info = info;
    }


}