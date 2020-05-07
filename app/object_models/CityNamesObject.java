package com.example.ordermadeeasy.object_models;

import com.example.ordermadeeasy.utilities.Listable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityNamesObject implements Listable {

    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("city_name")
    @Expose
    private String cityName;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return String.valueOf(cityName);
    }

    @Override
    public String getLabel() {
        return cityName;
    }
}