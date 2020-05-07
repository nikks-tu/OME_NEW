package com.techuva.ome_new.post_parameters;

public class GetDealerPostParameters {
    private String retailerId;


    public GetDealerPostParameters(String retailerId) {
        this.retailerId = retailerId;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }
}
