package com.techuva.ome_new.object_models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrderResultObject implements Parcelable {

    @SerializedName("demand_order_id")
    @Expose
    private Integer demandOrderId;
    @SerializedName("order_number")
    @Expose
    private Integer orderNumber;
    @SerializedName("dealer_id")
    @Expose
    private Integer dealerId;
    @SerializedName("retailer_id")
    @Expose
    private Integer retailerId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("received_quantity")
    @Expose
    private Integer receivedQuantity;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("received_on")
    @Expose
    private String receivedOn;
    @SerializedName("last_modified_on")
    @Expose
    private String lastModifiedOn;
    @SerializedName("last_modified_by")
    @Expose
    private Integer lastModifiedBy;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("status_name")
    @Expose
    private String statusName;
    @SerializedName("dealer_name")
    @Expose
    private String dealerName;
    @SerializedName("retailer_name")
    @Expose
    private String retailerName;
    @SerializedName("last_modified_by_name")
    @Expose
    private String lastModifiedByName;
    @SerializedName("customer_name")
    @Expose
    private String customer_name;

    @SerializedName("image_url")
    @Expose
    private String image_url;

    @SerializedName("display_name")
    @Expose
    private String display_name;

    protected PlaceOrderResultObject(Parcel in) {
        if (in.readByte() == 0) {
            demandOrderId = null;
        } else {
            demandOrderId = in.readInt();
        }
        if (in.readByte() == 0) {
            orderNumber = null;
        } else {
            orderNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            dealerId = null;
        } else {
            dealerId = in.readInt();
        }
        if (in.readByte() == 0) {
            retailerId = null;
        } else {
            retailerId = in.readInt();
        }
        if (in.readByte() == 0) {
            productId = null;
        } else {
            productId = in.readInt();
        }
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
        if (in.readByte() == 0) {
            receivedQuantity = null;
        } else {
            receivedQuantity = in.readInt();
        }
        if (in.readByte() == 0) {
            statusId = null;
        } else {
            statusId = in.readInt();
        }
        receivedOn = in.readString();
        lastModifiedOn = in.readString();
        if (in.readByte() == 0) {
            lastModifiedBy = null;
        } else {
            lastModifiedBy = in.readInt();
        }
        productCode = in.readString();
        productDesc = in.readString();
        statusName = in.readString();
        dealerName = in.readString();
        retailerName = in.readString();
        lastModifiedByName = in.readString();
        customer_name = in.readString();
        image_url = in.readString();
    }

    public static final Creator<PlaceOrderResultObject> CREATOR = new Creator<PlaceOrderResultObject>() {
        @Override
        public PlaceOrderResultObject createFromParcel(Parcel in) {
            return new PlaceOrderResultObject(in);
        }

        @Override
        public PlaceOrderResultObject[] newArray(int size) {
            return new PlaceOrderResultObject[size];
        }
    };

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }


    public Integer getDemandOrderId() {
        return demandOrderId;
    }

    public void setDemandOrderId(Integer demandOrderId) {
        this.demandOrderId = demandOrderId;
    }

    public Integer  getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(String receivedOn) {
        this.receivedOn = receivedOn;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    public void setLastModifiedByName(String lastModifiedByName) {
        this.lastModifiedByName = lastModifiedByName;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (demandOrderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(demandOrderId);
        }
        if (orderNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(orderNumber);
        }
        if (dealerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(dealerId);
        }
        if (retailerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(retailerId);
        }
        if (productId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(productId);
        }
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
        if (receivedQuantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(receivedQuantity);
        }
        if (statusId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(statusId);
        }
        dest.writeString(receivedOn);
        dest.writeString(lastModifiedOn);
        if (lastModifiedBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lastModifiedBy);
        }
        dest.writeString(productCode);
        dest.writeString(productDesc);
        dest.writeString(statusName);
        dest.writeString(dealerName);
        dest.writeString(retailerName);
        dest.writeString(lastModifiedByName);
        dest.writeString(customer_name);
        dest.writeString(image_url);
    }
    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

}