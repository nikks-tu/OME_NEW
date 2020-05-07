package com.example.ordermadeeasy.post_parameters;

public class GetCityPostParameters {

    private String stateId;
    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }


    public GetCityPostParameters(String stateId) {
        this.stateId = stateId;
    }

}
