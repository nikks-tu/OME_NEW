package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SupplierShopperOrderMainObject
{

    @SerializedName("result")
    @Expose
    private ArrayList<SupplierShopperOrderResultObject> result = null;
    @SerializedName("info")
    @Expose
    private SupplierShopperOrderInfoObject info;

    public ArrayList<SupplierShopperOrderResultObject> getResult() {
        return result;
    }

    public void setResult(ArrayList<SupplierShopperOrderResultObject> result) {
        this.result = result;
    }

    public SupplierShopperOrderInfoObject getInfo() {
        return info;
    }

    public void setInfo(SupplierShopperOrderInfoObject info) {
        this.info = info;
    }

}