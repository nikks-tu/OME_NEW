package com.techuva.ome_new.api_interface;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.UpdateOrderMainObject;
import com.techuva.ome_new.post_parameters.UpdateOrderPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface UpdateOrderDataInterface {

    @POST(Constants.UpdateOrder)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);

    Call<UpdateOrderMainObject> getStringScalar(@Header("authUser") int headerValue, @Body UpdateOrderPostParameters postParameter);

    @POST(Constants.UpdateOrder)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<UpdateOrderMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body UpdateOrderPostParameters postParameter);

    @POST(Constants.CancelConsumerOrders)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<JsonElement> cancelConsumerOrder(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonObject jsonObject);

    @POST(Constants.UpdateConsumerOrder)
    Call<JsonElement> updateConsumerOrder(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonObject jsonObject);

    @POST(Constants.UpdateSupplierConsumerOrder)
    Call<JsonElement> updateSuppllierConsumerOrder(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body JsonObject jsonObject);

    @POST(Constants.ShopperUpdateOrder)
    Call<JsonElement> updateShopperOrder(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body UpdateOrderPostParameters postParameter);

}
