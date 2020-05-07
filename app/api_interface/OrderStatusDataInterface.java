package com.example.ordermadeeasy.api_interface;

import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.object_models.OrderStatusMainObject;
import com.example.ordermadeeasy.post_parameters.GetAllOrdersPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface OrderStatusDataInterface {

    @POST(Constants.GetAllOrderStatuses)
    Call<OrderStatusMainObject> getStringScalar(@Header("authUser") int headerValue);

    @POST(Constants.GetAllOrderStatuses)
    Call<OrderStatusMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization,
                                                @Body GetAllOrdersPostParameters postParameters);

}
