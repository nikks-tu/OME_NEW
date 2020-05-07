package com.example.ordermadeeasy.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConsumerAllOrderResultObject {

    @SerializedName("ordered_on")
    @Expose
    private String orderedOn;
    @SerializedName("po_id")
    @Expose
    private String poId;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("products")
    @Expose
    private List<ConsumerAllOrderObject> products = null;
    private int productListSize;

    public String getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(String orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<ConsumerAllOrderObject> getProducts() {
        return products;
    }

    public void setProducts(List<ConsumerAllOrderObject> products) {
        this.products = products;
    }

    public int getProductListSize() {
        return productListSize;
    }

    public void setProductListSize(int productListSize) {
        this.productListSize = productListSize;
    }
}