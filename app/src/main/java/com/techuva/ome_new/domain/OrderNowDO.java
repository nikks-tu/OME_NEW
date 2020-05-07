package com.techuva.ome_new.domain;

import java.util.ArrayList;

public class OrderNowDO extends BaseDomain {
    public int srlnum = 0;
    public String customerId;
    public String itemId;
    public String pkgTpye_G_S_D;
    public String orderType= "";
    public String mealType= "";
    public String mealTime = "";
    public String pkgTpye_S_C = "";
    public String pkg_Desc = "";
    public String orderStatus = "NEW";
    public String paymentStatus = "False";
    public String paymentMode = "False";
    public String orderOriginId = "Android";
    public double oneWeekPrice;
    public double oneMonthPrice;
    public double threeMonthPrice;
    public double sixMonthPrice;
    public double oneYearPrice;
    public double cartValue=0.0;
    public double subTotal=0.0;
    public double totalAmount=0.0;


    public String pkg_Name = "";
    public String pkg_Image = "";
    public double pkg_Price;
    public String pkgId = "";
    public int mealCnt;
    public int quantity = 1;
    public int orderPkgQty;
    public int totalorderqty = 0;
    public String orderMessage = "";
    public String errorMessage = "";
    public int CustBillId;
    public int paymodId;
    public int key;
    public String status;
    public int custBillId;
    public String custDelAddress = "";
    public String loginMessage = "";
    public ArrayList<String> categories= new ArrayList<>();

}

