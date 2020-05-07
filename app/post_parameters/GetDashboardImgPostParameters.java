package com.example.ordermadeeasy.post_parameters;

public class GetDashboardImgPostParameters {


    private  String companyId;

    private String pagePerCount;
    private String pageNumber;

    private String role;


    public GetDashboardImgPostParameters(String companyId, String pagePerCount, String pageNumber, String role) {
        this.companyId = companyId;
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
        this.role = role;
    }

    public String getUserId() {
        return companyId;
    }

    public void setUserId(String userId) {
        this.companyId = userId;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
