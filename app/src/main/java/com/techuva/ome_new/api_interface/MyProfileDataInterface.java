package com.techuva.ome_new.api_interface;

import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.MyProfileMainObject;
import com.techuva.ome_new.post_parameters.MyProfilePostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MyProfileDataInterface {

    @POST(Constants.GettingMyProfile)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<MyProfileMainObject> getStringScalar(@Header("authUser") int headerValue, @Body MyProfilePostParameters postParameter);

    @POST(Constants.GettingMyProfile)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<MyProfileMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body MyProfilePostParameters postParameter);

}
