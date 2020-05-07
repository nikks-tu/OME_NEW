package com.techuva.ome_new.post_parameters;

public class ChangePasswordPostParameters {

    private String email;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordPostParameters(String userId, String oldPassword, String newPassword) {
        this.email = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUserId() {
        return email;
    }

    public void setUserId(String userId) {
        this.email = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }



}
