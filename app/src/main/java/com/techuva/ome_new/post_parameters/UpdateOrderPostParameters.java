package com.techuva.ome_new.post_parameters;

public class UpdateOrderPostParameters {
    private String demandOrderId;
    private String statusId;
    private String receivedQuantity;
    private String order_id;
    private String role;

    public UpdateOrderPostParameters(String order_id, String role) {
        this.order_id = order_id;
        this.role = role;
    }


    public UpdateOrderPostParameters(String demandOrderId, String statusId, String receivedQuantity) {
        this.demandOrderId = demandOrderId;
        this.statusId = statusId;
        this.receivedQuantity = receivedQuantity;
    }

    public String getDemandOrderId() {
        return demandOrderId;
    }

    public void setDemandOrderId(String demandOrderId) {
        this.demandOrderId = demandOrderId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(String receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }



}
