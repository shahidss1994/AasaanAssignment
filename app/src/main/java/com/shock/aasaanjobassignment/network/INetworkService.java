package com.shock.aasaanjobassignment.network;

import com.shock.aasaanjobassignment.Constants;
import com.shock.aasaanjobassignment.page.city.model.CitiesResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by shahid on 25/1/18.
 */

public interface INetworkService {

    @GET(Constants.PATH_URL)
    Observable<CitiesResponse> getCities(@QueryMap Map<String,String> queryMap);


}
