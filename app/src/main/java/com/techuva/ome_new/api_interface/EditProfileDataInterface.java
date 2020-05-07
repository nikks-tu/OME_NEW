package com.techuva.ome_new.api_interface;

import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.EditProfileMainObject;
import com.techuva.ome_new.post_parameters.EditProfilePostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface EditProfileDataInterface {

    @POST(Constants.UpdateMyProfile)
    Call<EditProfileMainObject> getStringScalar(@Header("authUser") int headerValue, @Body EditProfilePostParameters postParameter);

    @POST(Constants.UpdateMyProfile)
    Call<EditProfileMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body EditProfilePostParameters postParameter);

}
