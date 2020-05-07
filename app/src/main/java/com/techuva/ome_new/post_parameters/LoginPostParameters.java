package com.techuva.ome_new.post_parameters;

public class LoginPostParameters {

    private String mobileno;
    private String otp;
    private String grant_type;


    public LoginPostParameters(String mobileno, String otp, String grant_type) {
        this.mobileno = mobileno;
        this.otp = otp;
        this.grant_type = grant_type;
    }


    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }





}
