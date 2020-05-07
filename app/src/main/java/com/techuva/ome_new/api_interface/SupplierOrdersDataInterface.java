package com.techuva.ome_new.api_interface;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.post_parameters.GetAllOrdersPostParameters;
import com.techuva.ome_new.post_parameters.SupplierConsumerOrderUpdatePostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SupplierOrdersDataInterface {

    @POST(Constants.GetAllOrderCountSupplier)
    Call<JsonElement> getSupplierAllOrders(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetAllOrdersPostParameters postParameter);

    @POST(Constants.GetAllOrderCountForSupplierShopper)
    Call<JsonElement> getShopperAllOrders(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetAllOrdersPostParameters postParameter);

    @POST(Constants.GetAllOrderCountForSupplierConsumer)
    Call<JsonElement> getConsumerAllOrders(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetAllOrdersPostParameters postParameter);

    @POST(Constants.OrderUpdateForConsumer)
    Call<JsonElement> updateConsumerOrders(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body SupplierConsumerOrderUpdatePostParameters postParameter);

    @POST(Constants.CancelOrderForShopper)
    Call<JsonElement> cancelShopperOrders(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonObject jsonObject);

    @POST(Constants.OrderUpdateForConsumer)
    Call<JsonElement> updateMultiConsumerOrders(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonObject postParameter);

    @POST(Constants.OrderUpdateForShopper)
    Call<JsonElement> updateMultiShopperOrders(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonObject postParameter);

}
