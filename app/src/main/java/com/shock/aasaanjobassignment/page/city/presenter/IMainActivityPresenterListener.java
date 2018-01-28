package com.shock.aasaanjobassignment.page.city.presenter;

import com.shock.aasaanjobassignment.page.city.model.CitiesResponse;
import com.shock.aasaanjobassignment.page.city.model.City;

import java.util.List;

/**
 * Created by shahid shaikh on 26-01-2018.
 */

public interface IMainActivityPresenterListener {

    void onCitiesResultFromApi(CitiesResponse citiesResponse);

    void onCitiesResultFromDb(List<City> cityList,int offset);

    void onError(String errorMsg);

}
