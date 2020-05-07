package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchProductDealersList implements Serializable {

    @SerializedName("dealerId")
    @Expose
    private Integer dealerId;
    @SerializedName("dealerName")
    @Expose
    private String dealerName;
    @SerializedName("dealerCode")
    @Expose
    private String dealerCode;

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

}