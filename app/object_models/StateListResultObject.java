package com.example.ordermadeeasy.object_models;

import com.example.ordermadeeasy.utilities.Listable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateListResultObject implements Listable {

    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("last_modified_on")
    @Expose
    private String lastModifiedOn;
    @SerializedName("last_modified_by_name")
    @Expose
    private String lastModifiedByName;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    public void setLastModifiedByName(String lastModifiedByName) {
        this.lastModifiedByName = lastModifiedByName;
    }

    @Override
    public String toString() {
        return String.valueOf(stateName);
    }

    @Override
    public String getLabel() {
        return stateName;
    }

}