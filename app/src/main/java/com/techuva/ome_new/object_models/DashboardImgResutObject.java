package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardImgResutObject  {

    @SerializedName("dealer")
    @Expose
    private List<DealerProductImgObject> dealer = null;
    @SerializedName("admin")
    @Expose
    private List<AdminProductImgObject> admin = null;

    public List<DealerProductImgObject> getDealer() {
        return dealer;
    }

    public void setDealer(List<DealerProductImgObject> dealer) {
        this.dealer = dealer;
    }

    public List<AdminProductImgObject> getAdmin() {
        return admin;
    }

    public void setAdmin(List<AdminProductImgObject> admin) {
        this.admin = admin;
    }

}