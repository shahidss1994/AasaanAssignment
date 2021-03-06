package com.shock.aasaanjobassignment.interactor;

import com.shock.aasaanjobassignment.Constants;
import com.shock.aasaanjobassignment.db.IDbHelper;
import com.shock.aasaanjobassignment.network.INetworkService;
import com.shock.aasaanjobassignment.page.city.interactor.MainActivityInteractor;
import com.shock.aasaanjobassignment.page.city.model.CitiesResponse;
import com.shock.aasaanjobassignment.page.city.model.City;
import com.shock.aasaanjobassignment.page.city.presenter.IMainActivityPresenterListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import rx.Scheduler;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shahid shaikh on 28-01-2018.
 */

public class MainActivityInteractorTest {

    private MainActivityInteractor mainActivityInteractor;

    private int OFFSET = 0;

    @Mock
    private IMainActivityPresenterListener mainActivityPresenterListener;

    @Mock
    private IDbHelper dbHelper;

    @Mock
    private INetworkService networkService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        setupRxSchedulers();

        mainActivityInteractor = new MainActivityInteractor(dbHelper, networkService, mainActivityPresenterListener);
    }

    private void setupRxSchedulers() {
        RxJavaHooks.reset();
        RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });
        RxAndroidPlugins.reset();
        RxAndroidPlugins.initMainThreadScheduler(new Callable<io.reactivex.Scheduler>() {
            @Override
            public io.reactivex.Scheduler call() throws Exception {
                return io.reactivex.schedulers.Schedulers.io();
            }
        });
    }

    @Test
    public void getCitiesFromDb() {
        mockDbCities();
        mainActivityInteractor.getCitiesFromDb(OFFSET);
        verify(mainActivityPresenterListener).onCitiesResultFromDb(any(List.class), OFFSET);
    }

    @Test
    public void getCitiesFromDbNotNullButEmpty() {
        mockDbEmptyCitiesButNotNull();
        mainActivityInteractor.getCitiesFromDb(OFFSET);
        verify(mainActivityPresenterListener).onCitiesResultFromDb(any(List.class), OFFSET);
    }

    @Test
    public void getCitiesFromDbNull() {
        mockDbCitiesNull();
        mainActivityInteractor.getCitiesFromDb(OFFSET);
        verify(mainActivityPresenterListener).onCitiesResultFromDb(null, OFFSET);
    }

    @Test
    public void getCitiesFromDbThrowsError() {
        mainActivityInteractor.getCitiesFromDb(OFFSET);
        verify(mainActivityPresenterListener).onError("ERR!!");
    }

    @Test
    public void getCitiesFromApi() {
        mockApiCities();
        mainActivityInteractor.getCitiesFromDb(OFFSET);
        verify(mainActivityPresenterListener).onCitiesResultFromApi(any(CitiesResponse.class));
    }

    @Test
    public void getCitiesFromApiNotNullButEmpty() {
        mockApiEmptyCitiesButNotNull();
        mainActivityInteractor.getCitiesFromDb(OFFSET);
        verify(mainActivityPresenterListener).onCitiesResultFromApi(any(CitiesResponse.class));
    }

    @Test
    public void getCitiesFromApiNull() {
        mockApiCitiesNull();
        mainActivityInteractor.getCitiesFromDb(OFFSET);
        verify(mainActivityPresenterListener).onCitiesResultFromApi(any(CitiesResponse.class));
    }

    @Test
    public void getCitiesFromApiThrowsError() {
        mainActivityInteractor.getCitiesFromApi(OFFSET);
        verify(mainActivityPresenterListener).onError("ERR!!");
    }

    private void mockDbCities() {
        List<City> cities = mockCities();
        Observable<List<City>> observableCities = Observable.just(cities);
        when(dbHelper.getCities()).thenReturn(observableCities);
    }

    private void mockDbEmptyCitiesButNotNull() {
        List<City> cities = new ArrayList<>();
        Observable<List<City>> observableCities = Observable.just(cities);
        when(dbHelper.getCities()).thenReturn(observableCities);
    }

    private void mockDbCitiesNull() {
        List<City> cities = null;
        Observable<List<City>> observableCities = Observable.just(cities);
        when(dbHelper.getCities()).thenReturn(observableCities);
    }

    private void mockApiCities() {
        List<City> cities = mockCities();
        CitiesResponse citiesResponse = new CitiesResponse(cities);
        Observable<CitiesResponse> observableCities = Observable.just(citiesResponse);
        when(networkService.getCities(getQueryMap())).thenReturn(observableCities);
    }

    private void mockApiEmptyCitiesButNotNull() {
        List<City> cities = new ArrayList<>();
        CitiesResponse citiesResponse = new CitiesResponse(cities);
        Observable<CitiesResponse> observableCities = Observable.just(citiesResponse);
        when(networkService.getCities(getQueryMap())).thenReturn(observableCities);
    }

    private void mockApiCitiesNull() {
        List<City> cities = null;
        CitiesResponse citiesResponse = new CitiesResponse(cities);
        Observable<CitiesResponse> observableCities = Observable.just(citiesResponse);
        when(networkService.getCities(getQueryMap())).thenReturn(observableCities);
    }

    private Map<String, String> getQueryMap() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(Constants.KEY_OFFSET, String.valueOf(OFFSET));
        queryMap.put(Constants.KEY_LIMIT, String.valueOf(Constants.LIMIT));
        return queryMap;
    }

    private List<City> mockCities() {
        List<City> cities = new ArrayList<>();
        cities.add(new City("mumbai", 1l, "mumbai"));
        cities.add(new City("New Delhi", 2l, "ew-delhi"));
        cities.add(new City("Pune", 3l, "pune"));
        cities.add(new City("Bengaluru", 4l, "bengaluru"));
        cities.add(new City("Navi Mumbai", 5l, "navi_mumbai"));
        cities.add(new City("Ahmedabad", 6l, "ahmedabad"));
        cities.add(new City("Kolkata", 7l, "kolkata"));
        cities.add(new City("Chennai", 8l, "chennai"));
        cities.add(new City("Hyderabad", 9l, "hyderabad"));
        cities.add(new City("Gurgaon", 10l, "gurgaon"));
        return cities;
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.reset();
        RxJavaHooks.reset();
    }

}
