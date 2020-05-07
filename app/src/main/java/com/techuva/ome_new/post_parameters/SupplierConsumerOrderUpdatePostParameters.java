package com.techuva.ome_new.post_parameters;

public class SupplierConsumerOrderUpdatePostParameters {

    private int po_id;
    private String status;

    public SupplierConsumerOrderUpdatePostParameters(int po_id, String status) {
        this.po_id = po_id;
        this.status = status;
    }


    public int getPo_id() {
        return po_id;
    }

    public void setPo_id(int po_id) {
        this.po_id = po_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
