package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.GetDealerMainObject;
import com.example.ordermadeeasy.object_models.SearchProductMainObject;
import com.example.ordermadeeasy.post_parameters.GetDealerPostParameters;
import com.example.ordermadeeasy.post_parameters.SearchProductPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface GetDealersDataInterface {

    @POST(Constants.GetAllDealers)
   Call<GetDealerMainObject> getStringScalar(@Header("authUser") int headerValue, @Body GetDealerPostParameters postParameter);

    @POST(Constants.GetAllDealers)
   Call<GetDealerMainObject> callWithSession(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetDealerPostParameters postParameter);

}
