package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddProductToCartInterface {

    @POST(Constants.AddProductToCartForShopper)
    Call<JsonElement> addProductToShopperCart(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonArray postParameter);

}
