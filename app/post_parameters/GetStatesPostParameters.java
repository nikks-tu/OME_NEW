package com.example.ordermadeeasy.post_parameters;

public class GetStatesPostParameters {
    private String searchKey;
    private String pagePerCount;
    private String pageNumber;

    public GetStatesPostParameters(String searchKey, String pagePerCount, String pageNumber) {
       this.searchKey = searchKey;
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
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
