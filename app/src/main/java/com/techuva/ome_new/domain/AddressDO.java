package com.techuva.ome_new.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressDO implements Parcelable{


    public String city="";
    public String houseNo="";
    public String addressType="";
    public String address="";
    public String landmark="";
    public String zipcode="";
    public int cityId;
    public String addressId="";
    public String homeAddress="";
    public String officeAddress="";
    public String otherAddress="";
    public int key;
    public int addressNo;
    public String customerId;
    public String errorMessage="";
    public String opstatus="";
    public String custDelAddress="";
    public int pincodeId;
    public int custDetId;
    public String laneDesc="";
    public int addrTypeId;


    public AddressDO()
    {

    }

    protected AddressDO(Parcel in) {
        city = in.readString();
        houseNo = in.readString();
        addressType = in.readString();
        address = in.readString();
        landmark = in.readString();
        zipcode = in.readString();
        cityId = in.readInt();
        addressId = in.readString();
        homeAddress = in.readString();
        officeAddress = in.readString();
        otherAddress = in.readString();
        key = in.readInt();
        addressNo = in.readInt();
        customerId = in.readString();
        errorMessage = in.readString();
        opstatus = in.readString();
        custDelAddress = in.readString();
        pincodeId = in.readInt();
        custDetId = in.readInt();
        laneDesc = in.readString();
        addrTypeId = in.readInt();
    }

    public static final Creator<AddressDO> CREATOR = new Creator<AddressDO>() {
        @Override
        public AddressDO createFromParcel(Parcel in) {
            return new AddressDO(in);
        }

        @Override
        public AddressDO[] newArray(int size) {
            return new AddressDO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(houseNo);
        dest.writeString(addressType);
        dest.writeString(address);
        dest.writeString(landmark);
        dest.writeString(zipcode);
        dest.writeInt(cityId);
        dest.writeString(addressId);
        dest.writeString(homeAddress);
        dest.writeString(officeAddress);
        dest.writeString(otherAddress);
        dest.writeInt(key);
        dest.writeInt(addressNo);
        dest.writeString(customerId);
        dest.writeString(errorMessage);
        dest.writeString(opstatus);
        dest.writeString(custDelAddress);
        dest.writeInt(pincodeId);
        dest.writeInt(custDetId);
        dest.writeString(laneDesc);
        dest.writeInt(addrTypeId);
    }
}

