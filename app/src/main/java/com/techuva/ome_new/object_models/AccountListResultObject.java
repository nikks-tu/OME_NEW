package com.techuva.ome_new.object_models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techuva.ome_new.domain.BaseDomain;


public class AccountListResultObject extends BaseDomain {

    @SerializedName("type_id")
    @Expose
    public Integer typeId;
    @SerializedName("type_code")
    @Expose
    public String typeCode;
    @SerializedName("type_description")
    @Expose
    public String typeDescription;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    private void readFromParcel(Parcel in) {
        typeId = in.readInt();
        typeCode = in.readString();
        typeDescription = in.readString();
    }
}