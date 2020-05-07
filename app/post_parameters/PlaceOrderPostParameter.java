package com.example.ordermadeeasy.post_parameters;

public class PlaceOrderPostParameter {

    private  int dealerId;
    private  int retailerId;
    private  int productId;
    private  int quantity;
    private  int statusId;
    private  int receivedQuantity;


    public PlaceOrderPostParameter(int dealerId, int retailerId, int productId, int quantity, int statusId, int receivedQuantity) {
        this.dealerId = dealerId;
        this.retailerId = retailerId;
        this.productId = productId;
        this.quantity = quantity;
        this.statusId = statusId;
        this.receivedQuantity = receivedQuantity;
    }

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(int receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }




}
