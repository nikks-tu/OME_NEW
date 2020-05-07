package com.example.ordermadeeasy.object_models;

public class QuatityList {

    private Integer quantity;

    public QuatityList(Integer quantity) {
        this.quantity = quantity;
    }

    public QuatityList() {

    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.valueOf(quantity);
    }
}