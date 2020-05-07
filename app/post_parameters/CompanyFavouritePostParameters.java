package com.example.ordermadeeasy.post_parameters;

public class CompanyFavouritePostParameters {

    public String userId;
    public String companyId;
    public boolean status;
    public String pagePerCount;
    public String pageNumber;

    public CompanyFavouritePostParameters(String userId, String pagePerCount, String pageNumber) {
        this.userId = userId;
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
    }

    public CompanyFavouritePostParameters(String userId, String companyId, boolean status) {
        this.userId = userId;
        this.companyId = companyId;
        this.status = status;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPagePerCount() {
        return pagePerCount;
    }

    public void setPagePerCount(String pagePerCount) {
        this.pagePerCount = pagePerCount;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }



}
