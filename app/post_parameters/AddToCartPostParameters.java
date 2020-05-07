package com.example.ordermadeeasy.post_parameters;

public class AddToCartPostParameters {

    private String company_id;
    private String product_id;
    private int quantity;
    private String total_amount;
    private int status_id;

    public AddToCartPostParameters(String company_id, String product_id, int quantity, String total_amount, int status_id) {
        this.company_id = company_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.total_amount = total_amount;
        this.status_id = status_id;
    }


    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }




}
