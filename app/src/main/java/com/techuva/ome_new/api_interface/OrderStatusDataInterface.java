package com.techuva.ome_new.api_interface;

import com.google.gson.JsonElement;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.OrderStatusMainObject;
import com.techuva.ome_new.post_parameters.GetAllOrdersPostParameters;
import com.techuva.ome_new.post_parameters.VersionCheckInfoPostParameters;

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

    @POST(Constants.VersionCheckInfo)
    Call<JsonElement> checkForVersionCheck(@Body VersionCheckInfoPostParameters postParameters);
}
