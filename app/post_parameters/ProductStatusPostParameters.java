package com.example.ordermadeeasy.post_parameters;

public class ProductStatusPostParameters {


    private String created_by;
    private String product_id;

    public ProductStatusPostParameters(String created_by, String product_id) {
        this.created_by = created_by;
        this.product_id = product_id;
    }

    public String getRetailer_id() {
        return created_by;
    }

    public void setRetailer_id(String retailer_id) {
        this.created_by = created_by;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = created_by;
    }

}
