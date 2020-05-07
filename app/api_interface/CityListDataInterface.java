package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.CityListMainObject;
import com.example.ordermadeeasy.post_parameters.GetCityPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface CityListDataInterface {

    @POST(Constants.SelectCityList)
    Call<CityListMainObject> getStringScalar(@Header("authUser") int headerValue, @Body GetCityPostParameters postParameter);

    @POST(Constants.SelectCityList)
    Call<CityListMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetCityPostParameters postParameter);

}
