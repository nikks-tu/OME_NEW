package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.PlaceOrderMainObject;
import com.example.ordermadeeasy.post_parameters.GetCartConsumerPostParameter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface PlaceOrderDataInterface {

    @Headers("Content-Type: application/json")
    @POST(Constants.PlaceOrder)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<PlaceOrderMainObject> getStringScalar(@Header("authUser") int headerValue, @Body JsonArray postParameter);

    @Headers("Content-Type: application/json")
    @POST(Constants.PlaceShoppersOrder)
    Call<JsonElement> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonObject postParameter);

    @Headers("Content-Type: application/json")
    @POST(Constants.ConumerPlaceOrder)
    Call<JsonElement> placeConsumerOrder(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonObject postParameter);

    @Headers("Content-Type: application/json")
    @POST(Constants.AddConsumerCart)
    Call<JsonElement>  addConsumerProductToCart(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonArray postParameter);

    @Headers("Content-Type: application/json")
    @POST(Constants.DeleteProductFromCart)
    Call<JsonElement>  deleteConsumerProductFromCart(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonObject postParameter);

    @Headers("Content-Type: application/json")
    @POST(Constants.DeleteProductFromShopperCart)
    Call<JsonElement>  deleteShopperProductFromCart(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonObject postParameter);

    @Headers("Content-Type: application/json")
    @POST(Constants.ModifyShopperCartItem)
    Call<JsonElement>  updateShopperProductInCart(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonArray postParameter);

    @Headers("Content-Type: application/json")
    @POST(Constants.ModifyConsumerCartItem)
    Call<JsonElement>  updateConsumerProductInCart(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonArray postParameter);

    @POST(Constants.ConsumerCartItems)
    Call<JsonElement>  getConsumerProductToCart(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetCartConsumerPostParameter postParameter);

    @POST(Constants.GetProductsFromCartForShopper)
    Call<JsonElement>  getShopperProductToCart(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetCartConsumerPostParameter postParameter);


    @POST(Constants.OrderStatusForConsumer)
    Call<JsonElement>  getOrderStatusesForConsumer(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetCartConsumerPostParameter postParameter);

    @POST(Constants.OrderStatusForShopper)
    Call<JsonElement>  getOrderStatusesForShopper(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetCartConsumerPostParameter postParameter);


}
