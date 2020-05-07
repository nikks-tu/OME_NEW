package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditProfileMainObject {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("info")
    @Expose
    private EditProfileInfoObject info;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public EditProfileInfoObject getInfo() {
        return info;
    }

    public void setInfo(EditProfileInfoObject info) {
        this.info = info;
    }
}
