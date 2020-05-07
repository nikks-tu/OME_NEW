package com.techuva.ome_new.api_interface;

import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.ProductStatusMainObject;
import com.techuva.ome_new.post_parameters.ProductStatusPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ProductStatusDataInterface {

    @POST(Constants.GettingProductStatusHistory)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<ProductStatusMainObject> getStringScalar(@Header("authUser") int headerValue, @Body ProductStatusPostParameters postParameter);

    @POST(Constants.GettingProductStatusHistory)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<ProductStatusMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body ProductStatusPostParameters postParameter);

}
