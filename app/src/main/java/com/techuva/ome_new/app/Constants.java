package com.techuva.ome_new.app;

/**
 * Created by user on 6/29/2019.
 */
public interface Constants {
    //Production URL
    //String LIVE_BASE_URL = "http://182.18.177.27:8283/";
    //Test URL
    String LIVE_BASE_URL = "http://182.18.177.27:8182/";
    String LIVE_BASE_URL_New = "http://182.18.177.27:8182/OME/";
    //UAT URL
    /*String LIVE_BASE_URL = "http://182.18.177.27:8384/";
    String LIVE_BASE_URL_New = "http://182.18.177.27:8384/OME/";*/
    //Production
    /*String LIVE_BASE_URL = "https://www.omeventuresapp.com/";
    String LIVE_BASE_URL_New = "https://www.omeventuresapp.com/OME/";*/

    String Ref_Type = "MOBILE";
    String DeviceID = "DeviceID";
    String CompanyID = "CompanyID";
    String UserID = "UserID";
    String UserType = "UserType";
    String UserName = "UserName";
    String UserMailId ="UserMailId";
    String AppVersion = "0.5";
    String FontVersion = "0";
    String GetAllProducts = "gettingProductMasterForRetailer";
    String GetAllProductsForShopper = "ProductMaster/getProductMasterForShopper";
    String AddProductToCartForShopper = "ProductCart/addShopperProductsToCart";
    String GetProductsFromCartForShopper = "ProductCart/getIncartShopperProducts";
    String GetCompanyProducts = "ProductMaster/getProductsForSelectedCompany";
    String PlaceShoppersOrder = "DO/placeOrderByShopper";
    String SearchCompany = "CompanyMaster/selectCompanyOnSearch";
    String AddConsumerCart = "ProductCart/addProductToCart";
    String GetAllOrderCountSupplier = "getAllOrdersOfSupplier";
    String GetAllOrderCountForSupplierConsumer = "getOrdersOfConsumersForSupplier";
    String OrderStatusForConsumer ="getOrdersOfConsumerForDashboard";

    String GetAllOrderCountForSupplierShopper = "getOrdersOfShoppersForSupplier";
    String OrderStatusForShopper = "getOrdersOfShopperForDashboard";
    String SendOTP = "Consumer/getOTP";
    String SaveDeviceToken = "Mobile/saveMobileToken";
    String ConsumerSignUp = "Consumer/signUp";
    String ConsumerCartItems = "ProductCart/getProductFromCartDetails";
    String ConumerPlaceOrder = "OD/placeOrderByConsumer";
    String ConsumerAllOrders = "OrderPlaceConsumerData/getConsumerOrders";
    String ValidateOTPForConsumer = "Consumer/validateOTP";
    String DeleteProductFromCart = "ProductCart/deleteCartItems";
    String DeleteProductFromShopperCart = "ProductCart/deleteCartItemsOfShopper";
    String OrderUpdateForConsumer = "OD/updateOrder";
    String OrderUpdateForShopper= "DO/updateOrder";
    String CancelOrderForShopper= "DO/cancelDemandOrders";
    String GetAllConsumerOrdersForSupplier = "OrderPlaceConsumerData/getConsumerOrdersForSupplier";
    String SupplierConsumerHistory = "OrderPlaceConsumerData/getOrderHistoryOfConForSupp";
    String GetAllShopperOrdersForSupplier = "SupCompany/getShopperOrdersForSupplier";
    String AddCompanyAsFavourite ="ConsumerCompFav/updateCompanyFavourite";
    String GetFavouriteCompanyList ="CompanyMaster/getFavouriteCompaniesList";
    String ModifyShopperCartItem ="ProductCart/updateDOCartItem";
    String ModifyConsumerCartItem ="ProductCart/modifiedProductToCart";
    String GetAdvertisement ="Advertisement/selectImagesForDashBoard";
    String VersionCheckInfo = "Mobile/VersionCheckInfo";
    String RetailerID = "RetailerId";
    String LoginID = "LoginID";
    String Password = "Password";
    String GrantType= "password";
    String AccessToken ="AccessToken";
    String RefreshToken ="RefreshToken";
    String AuthorizationKey = "Basic dGVjaHV2YS1jbGllbnQ6c2VjcmV0";
    String IsLoggedIn = "IsLoggedIn";
    String IsHomeSelected = "IsHomeSelected";
    String IsDefaultDeviceSaved = "IsDefaultDeviceSaved";
    String NULL_STRING = "null";
    String DEVICE_IN_USE = "";
    String COMPANY_ID = "COMPANY_ID";
    String LoginData = "login";
    String ForgetPassword = "forgotPassword";
    String SearchProduct = "";
    String PlaceOrder ="insertDemandOrder";
    String GetAllOrders = "SupCompany/mobileGetOrderHistoryOfShopper";
    String SupplierShopperHistory ="SupCompany/getOrderHistoryOfShopperForSupplier";
    String GetAllDealers = "selectDealersForRetailer";
    String GetAllOrderStatuses = "StatusMaster/getStatus";
    String GettingMyProfile = "gettingUserMaster";
    String UpdateMyProfile = "updateUserPortfolio";
    String SelectCityList = "City/gettingCitesList";
    String SelectStateList = "selectStateMaster";
    String GettingProductStatusHistory ="gettingCartKeyStatusDetails";
    String UpdateOrder ="updateDemandOrder";
    String UpdateConsumerOrder ="DO/receiveOrder";
    String UpdateSupplierConsumerOrder ="OD/updateOrder";
    String CancelConsumerOrders ="DO/cancelOrder";
    String ShopperUpdateOrder ="DO/receiveOrder";
    String ChangePassword ="changePassword";
    String GetImagesDashboard = "selectImgForDashBoardRetailer";
    String SearchKey = "Search";
    String RetailerCompanyID = "RetailerCompanyID";
    String IsFirstDownload="IsFirstDownload";
    String SingleAccount="SingleAccount";
    String UserRoles="UserRoles";
    String CartSize="CartSize";
    String TotalCartValue="TotalCartValue";
    String SelectedCompany="SelectedCompany";
    String Consumer = "CONSU";
    String Supplier = "SUPPL";
    String Shopper = "SHOPP";
    String Admin = "ADMIN";
    String SuperAdmin = "SADMIN";
    String OTC = "OTCMP";
    String Reject = "REJ";
    String PoID = "PoID";
    String Accept = "ACPT";
    String NotAvailable = "NAV";
    String TempMobile = "TempMobile";
    String IsRoleSelected = "IsRoleSelected";
    String PendingOrderCount = "PendingOrderCount";
    String Dispatched = "DISP";
    String CompanyTypeBoth = "B2B-B2C";
    String CompanyBtoB = "B2B";
    String CompanyBtoC = "B2C";
    String CompanyType = "CompanyType";
    String Cancelled = "Cancelled";
    String Cancel = "Cancel";
    String CompanyTypeCode = "CompanyTypeCode";
    String IsFromFavourites = "false";
    String Device_Token = "Device_Token";
}
