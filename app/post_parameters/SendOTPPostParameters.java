package com.example.ordermadeeasy.post_parameters;

public class SendOTPPostParameters {

    private String mobileno;

    public SendOTPPostParameters(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }


}
