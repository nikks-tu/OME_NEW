package com.example.ordermadeeasy.post_parameters;

public class GetCartConsumerPostParameter {
    String ordered_by;
    String  pagePerCount;
    String searchKey;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userRole;

    public GetCartConsumerPostParameter(String userRole, String userId) {
        this.userRole = userRole;
        this.userId = userId;
    }

    String userId;

    public GetCartConsumerPostParameter(String ordered_by, String pageNumber, String pagePerCount, String searchKey) {
        this.ordered_by = ordered_by;
        this.pageNumber = pageNumber;
        this.pagePerCount = pagePerCount;
        this.searchKey = searchKey;
    }

    String pageNumber;

    public String getOrdered_by() {
        return ordered_by;
    }

    public void setOrdered_by(String ordered_by) {
        this.ordered_by = ordered_by;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPagePerCount() {
        return pagePerCount;
    }

    public void setPagePerCount(String pagePerCount) {
        this.pagePerCount = pagePerCount;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }



}
