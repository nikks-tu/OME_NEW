package com.techuva.ome_new.api_interface;

import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.StateListMainObject;
import com.techuva.ome_new.post_parameters.GetStatesPostParameters;

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
