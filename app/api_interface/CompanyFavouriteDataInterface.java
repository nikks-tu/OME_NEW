package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.post_parameters.CompanyFavouritePostParameters;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface CompanyFavouriteDataInterface {

    @POST(Constants.AddCompanyAsFavourite)
    Call<JsonElement> getStringScalar(@Header("authUser") int headerValue,  @Header("authorization") String authorization, @Body CompanyFavouritePostParameters postParameter);

    @POST(Constants.GetFavouriteCompanyList)
    Call<JsonElement> getFavouriteCompanies(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body CompanyFavouritePostParameters postParameter);

}
