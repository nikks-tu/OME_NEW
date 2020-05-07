package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.DashboardImgMainObject;
import com.example.ordermadeeasy.post_parameters.GetDashboardImgPostParameters;
import com.example.ordermadeeasy.post_parameters.MyProfilePostParameters;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DashboardImagesDataInterface {

    @POST(Constants.GetImagesDashboard)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<DashboardImgMainObject> getStringScalar(@Header("authUser") int headerValue, @Body MyProfilePostParameters postParameter);

    @POST(Constants.GetImagesDashboard)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<DashboardImgMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetDashboardImgPostParameters postParameter);

    @POST(Constants.GetAdvertisement)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<JsonElement> getAdvertisements(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetDashboardImgPostParameters postParameter);

}
