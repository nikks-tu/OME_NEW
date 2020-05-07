package com.techuva.ome_new.post_parameters;

public class MyProfilePostParameters {


    private  String userId;
    private String pagePerCount;
    private String pageNumber;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public MyProfilePostParameters(String userId, String pagePerCount, String pageNumber) {
        this.userId = userId;
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
    }

}
