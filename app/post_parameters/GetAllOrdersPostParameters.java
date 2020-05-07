package com.example.ordermadeeasy.post_parameters;

public class GetAllOrdersPostParameters {
    private int retailer_id;
    private int createBy;
    private String Status;
    private String pagePerCount;
    private String pageNumber;

    public GetAllOrdersPostParameters( String companyId,  String pageNumber,String pagePerCount, String searchKey,String fromDate, String toDate) {
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.searchKey = searchKey;
        this.companyId = companyId;
    }

    private String fromDate;
    private String toDate;

    public String getStatusFor() {
        return statusFor;
    }

    public void setStatusFor(String statusFor) {
        this.statusFor = statusFor;
    }

    public GetAllOrdersPostParameters(String statusFor) {
        this.statusFor = statusFor;
    }

    private String statusFor;


    public GetAllOrdersPostParameters(String pagePerCount, String pageNumber, String searchKey, String userRole, int userId) {
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
        this.searchKey = searchKey;
        this.userRole = userRole;
        this.userId = userId;
    }

    private String searchKey;
    private String userRole;
    private int userId;
    private String companyId;

    public GetAllOrdersPostParameters(String companyId , String pageNumber, String pagePerCount, String searchKey) {
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
        this.searchKey = searchKey;
        this.companyId = companyId;
    }

    public GetAllOrdersPostParameters(String userRole, int userId) {
        this.userRole = userRole;
        this.userId = userId;
    }

    public GetAllOrdersPostParameters(String pagePerCount, String pageNumber, String searchKey) {
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
        this.searchKey = searchKey;
    }

    public GetAllOrdersPostParameters(int createBy, String status, String pagePerCount, String pageNumber) {
        this.createBy = createBy;
        this.Status = status;
        this.pagePerCount = pagePerCount;
        this.pageNumber = pageNumber;
    }


    public int getRetailer_id() {
        return retailer_id;
    }

    public void setRetailer_id(int retailer_id) {
        this.retailer_id = retailer_id;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }


}
