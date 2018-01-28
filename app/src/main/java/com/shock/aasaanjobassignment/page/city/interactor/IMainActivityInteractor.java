package com.shock.aasaanjobassignment.page.city.interactor;

import com.shock.aasaanjobassignment.page.city.model.City;
import com.shock.aasaanjobassignment.page.city.presenter.IMainActivityPresenterListener;

import java.util.List;

/**
 * Created by shahid shaikh on 26-01-2018.
 */

public interface IMainActivityInteractor {

    void setPresenterListener(IMainActivityPresenterListener mainActivityPresenterListener);

    void getCitiesFromDb(int offset);

    void getCitiesFromApi(int from);

    void getCity(String name);

    void insertCities(List<City> cityList);

}
