package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductStatusResultObject {

    @SerializedName("Values")
    @Expose
    private List<ProductStatusLineObject> values = null;

    public List<ProductStatusLineObject> getValues() {
        return values;
    }

    public void setValues(List<ProductStatusLineObject> values) {
        this.values = values;
    }

}