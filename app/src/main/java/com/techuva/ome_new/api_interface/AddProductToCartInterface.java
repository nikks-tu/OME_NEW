package com.techuva.ome_new.api_interface;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.techuva.ome_new.app.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddProductToCartInterface {

    @POST(Constants.AddProductToCartForShopper)
    Call<JsonElement> addProductToShopperCart(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonArray postParameter);

}
