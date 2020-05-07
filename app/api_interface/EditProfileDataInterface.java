package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.EditProfileMainObject;
import com.example.ordermadeeasy.object_models.MyProfileMainObject;
import com.example.ordermadeeasy.post_parameters.EditProfilePostParameters;
import com.example.ordermadeeasy.post_parameters.MyProfilePostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface EditProfileDataInterface {

    @POST(Constants.UpdateMyProfile)
    Call<EditProfileMainObject> getStringScalar(@Header("authUser") int headerValue, @Body EditProfilePostParameters postParameter);

    @POST(Constants.UpdateMyProfile)
    Call<EditProfileMainObject> getStringScalar(@Header("authUser") int headerValue,@Header("authorization") String authorization, @Body EditProfilePostParameters postParameter);

}
