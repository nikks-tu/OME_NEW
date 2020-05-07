package com.techuva.ome_new.api_interface;

import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.CityListMainObject;
import com.techuva.ome_new.post_parameters.GetCityPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface CityListDataInterface {

    @POST(Constants.SelectCityList)
    Call<CityListMainObject> getStringScalar(@Header("authUser") int headerValue, @Body GetCityPostParameters postParameter);

    @POST(Constants.SelectCityList)
    Call<CityListMainObject> getStringScalar(@Header("authUser") int headerValue, @Header("authorization") String authorization, @Body GetCityPostParameters postParameter);

}
