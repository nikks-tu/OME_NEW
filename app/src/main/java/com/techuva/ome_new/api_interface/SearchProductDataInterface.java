package com.techuva.ome_new.api_interface;

import com.google.gson.JsonElement;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.SearchProductMainObject;
import com.techuva.ome_new.post_parameters.SearchProductPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface SearchProductDataInterface {

    @POST(Constants.GetAllProducts)
      Call<SearchProductMainObject> getStringScalar(@Header("authUser") int headerValue, @Body SearchProductPostParameters postParameter);


    @POST(Constants.GetAllProducts)
     Call<SearchProductMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body SearchProductPostParameters postParameter);

    @POST(Constants.GetAllProductsForShopper)
    Call<SearchProductMainObject> getAllProductForShopper(@Header("authUser") int headerValue, @Body SearchProductPostParameters postParameter);

    @POST(Constants.GetAllProductsForShopper)
    Call<SearchProductMainObject> getAllProductForShopperWithSession(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body SearchProductPostParameters postParameter);

    @POST(Constants.GetCompanyProducts)
    Call<SearchProductMainObject> getAllProductForConsumer(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body SearchProductPostParameters postParameter);

    @POST(Constants.SearchCompany)
    Call<JsonElement> getCompanyList(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body SearchProductPostParameters postParameter);

}
