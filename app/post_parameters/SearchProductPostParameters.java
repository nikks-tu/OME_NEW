package com.example.ordermadeeasy.post_parameters;

public class SearchProductPostParameters {

    private int userId;
    private int companyId;
    private String searchKey;
    private String user_Id;
    private String pagePerCount;
    private String pageNumber;

    public SearchProductPostParameters(int userId, int companyId, String searchKey, String pagePerCount, String pageNumber) {
        this.userId = userId;
        this.companyId = companyId;
        this.searchKey = searchKey;
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
    }


    public SearchProductPostParameters(int userId, String searchKey, String pagePerCount, String pageNumber) {
        this.userId = userId;
        this.searchKey = searchKey;
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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
