package com.shock.aasaanjobassignment.page.city.interactor;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.shock.aasaanjobassignment.Constants;
import com.shock.aasaanjobassignment.db.IDbHelper;
import com.shock.aasaanjobassignment.network.INetworkService;
import com.shock.aasaanjobassignment.page.city.model.CitiesResponse;
import com.shock.aasaanjobassignment.page.city.model.City;
import com.shock.aasaanjobassignment.page.city.presenter.IMainActivityPresenterListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by shahid shaikh on 26-01-2018.
 */

public class MainActivityInteractor implements IMainActivityInteractor {

    private final String TAG = MainActivityInteractor.class.getSimpleName();

    private IMainActivityPresenterListener mainActivityPresenterListener;
    private IDbHelper dbHelper;

    @Inject
    INetworkService networkService;

    @Inject
    public MainActivityInteractor(IDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @VisibleForTesting
    public MainActivityInteractor(IDbHelper dbHelper, INetworkService networkService, IMainActivityPresenterListener mainActivityPresenterListener) {
        this.dbHelper = dbHelper;
        this.networkService = networkService;
        this.mainActivityPresenterListener = mainActivityPresenterListener;
    }

    @Override
    public void setPresenterListener(IMainActivityPresenterListener mainActivityPresenterListener) {
        this.mainActivityPresenterListener = mainActivityPresenterListener;
    }

    @Override
    public void getCitiesFromDb(final int offset) {
        dbHelper.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<City>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<City> cities) {
                mainActivityPresenterListener.onCitiesResultFromDb(cities, offset);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mainActivityPresenterListener.onError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getCitiesFromApi(final int offset) {

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(Constants.KEY_OFFSET, String.valueOf(offset));
        queryMap.put(Constants.KEY_LIMIT, String.valueOf(Constants.LIMIT));

        networkService.getCities(queryMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CitiesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CitiesResponse citiesResponse) {
                        mainActivityPresenterListener.onCitiesResultFromApi(citiesResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mainActivityPresenterListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void getCity(String name) {

    }

    @Override
    public void insertCities(List<City> cityList) {
        dbHelper.insertCities(cityList).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    Log.d(TAG, "Successfully Data Added");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
