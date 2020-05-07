package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.StateListMainObject;
import com.example.ordermadeeasy.post_parameters.GetStatesPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface StateListDataInterface {

    @POST(Constants.SelectStateList)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);

    Call<StateListMainObject> getStringScalar(@Header("authUser") int headerValue, @Body GetStatesPostParameters postParameter);

    @POST(Constants.SelectStateList)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);

    Call<StateListMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetStatesPostParameters postParameter);

}
