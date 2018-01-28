package com.shock.aasaanjobassignment.page.city.presenter;

import com.shock.aasaanjobassignment.page.city.interactor.IMainActivityInteractor;
import com.shock.aasaanjobassignment.page.city.model.CitiesResponse;
import com.shock.aasaanjobassignment.page.city.model.City;
import com.shock.aasaanjobassignment.page.city.view.IMainActivityView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by shahid shaikh on 26-01-2018.
 */

public class MainActivityPresenter implements IMainActivityPresenter, IMainActivityPresenterListener {

    IMainActivityView mainActivityView;
    IMainActivityInteractor mainActivityInteractor;

    @Inject()
    public MainActivityPresenter(IMainActivityView mainActivityView, IMainActivityInteractor mainActivityInteractor) {
        this.mainActivityView = mainActivityView;
        this.mainActivityInteractor = mainActivityInteractor;
        this.mainActivityInteractor.setPresenterListener(this);
    }

    @Override
    public void getCitiesFromDb(int offset) {
        mainActivityInteractor.getCitiesFromDb(offset);
    }

    public void getCitiesFromApi(int offset){
        mainActivityInteractor.getCitiesFromApi(offset);
    }

    @Override
    public void onCitiesResultFromDb(List<City> cityList, int offset) {
        if (cityList != null && cityList.size() > 0) {
            mainActivityView.setData(cityList);
        } else {
            mainActivityInteractor.getCitiesFromApi(offset);
        }
    }

    @Override
    public void onCitiesResultFromApi(CitiesResponse citiesResponse) {
        mainActivityView.setData(citiesResponse.getCities());
        mainActivityInteractor.insertCities(citiesResponse.getCities());
    }

    @Override
    public void onError(String errorMsg) {
        mainActivityView.showToastMsg(errorMsg);
    }

    @Override
    public void showMsg(String msg) {

    }
}
