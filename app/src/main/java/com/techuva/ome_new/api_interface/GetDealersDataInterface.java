package com.techuva.ome_new.api_interface;


import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.GetDealerMainObject;
import com.techuva.ome_new.post_parameters.GetDealerPostParameters;

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
