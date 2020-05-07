package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityListResultObject {

    @SerializedName("Values")
    @Expose
    private List<CityNamesObject> values = null;

    public List<CityNamesObject> getValues() {
        return values;
    }

    public void setValues(List<CityNamesObject> values) {
        this.values = values;
    }

}