package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.AllOrdersMainObject;
import com.example.ordermadeeasy.object_models.SupplierConsumerHistoryMainObject;
import com.example.ordermadeeasy.object_models.SupplierConsumerOrderMainObject;
import com.example.ordermadeeasy.post_parameters.GetAllOrdersPostParameters;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface GetAllOrdersDataInterface {

    @POST(Constants.GetAllOrders)
    Call<AllOrdersMainObject> getStringScalar(@Header("authUser") int headerValue, @Body GetAllOrdersPostParameters postParameter);


    @POST(Constants.GetAllOrders)
    Call<JsonElement> callWithSession(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetAllOrdersPostParameters postParameter);


    @POST(Constants.ConsumerAllOrders)
    Call<JsonElement> getAllConsumerOrders(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetAllOrdersPostParameters postParameter);


    @POST(Constants.GetAllConsumerOrdersForSupplier)
    Call<SupplierConsumerOrderMainObject> getAllConsumerOrdersForSupplier(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetAllOrdersPostParameters postParameter);

    @POST(Constants.SupplierConsumerHistory)
    Call<SupplierConsumerHistoryMainObject> getSupplierConsumerHistory(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetAllOrdersPostParameters postParameter);

    @POST(Constants.GetAllShopperOrdersForSupplier)
    Call<JsonElement> getAllShopperOrdersForSupplier(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetAllOrdersPostParameters postParameter);

    @POST(Constants.SupplierShopperHistory)
    Call<JsonElement> getSupplierShopperHistory(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetAllOrdersPostParameters postParameter);

}
