package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetDealerResultObject implements Serializable {

    @SerializedName("dealer_id")
    @Expose
    private Integer dealerId;
    @SerializedName("dealer_name")
    @Expose
    private String dealerName;
    @SerializedName("user_code")
    @Expose
    private String userCode;

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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    @Override
    public String toString() {
        return String.valueOf(dealerName);
    }

}
