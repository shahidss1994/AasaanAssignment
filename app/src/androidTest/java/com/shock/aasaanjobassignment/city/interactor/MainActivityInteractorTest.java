package com.shock.aasaanjobassignment.city.interactor;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.shock.aasaanjobassignment.db.IDbHelper;
import com.shock.aasaanjobassignment.network.INetworkService;
import com.shock.aasaanjobassignment.page.city.interactor.MainActivityInteractor;
import com.shock.aasaanjobassignment.page.city.model.City;
import com.shock.aasaanjobassignment.page.city.presenter.IMainActivityPresenterListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import rx.Scheduler;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shahid shaikh on 28-01-2018.
 */

public class MainActivityInteractorTest {

    private MainActivityInteractor mainActivityInteractor;

    @Mock
    private IMainActivityPresenterListener mainActivityPresenterListener;

    @Mock
    private IDbHelper dbHelper;

    @Mock
    private INetworkService networkService;
    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();

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

    private void mockDbCities() {
        List<City> cities = mockCities();
        Observable<List<City>> observableCities = Observable.just(cities);
        when(dbHelper.getCities()).thenReturn(observableCities);
    }

    private void mockDbEmptyCitiesNotNullButEmpty() {
        List<City> cities = new ArrayList<>();
        Observable<List<City>> observableCities = Observable.just(cities);
        when(dbHelper.getCities()).thenReturn(observableCities);
    }

    private void mockDbEmptyCitiesNull() {
        List<City> cities = null;
        Observable<List<City>> observableCities = Observable.just(cities);
        when(dbHelper.getCities()).thenReturn(observableCities);
    }

    @Test
    public void getCitiesFromDb() {
        mockDbCities();
        mainActivityInteractor.getCitiesFromDb(0);
        verify(mainActivityPresenterListener).onCitiesResultFromDb(any(List.class), 0);
    }

    @Test
    public void getCitiesFromDbNotNullButEmpty() {
        mockDbEmptyCitiesNotNullButEmpty();
        mainActivityInteractor.getCitiesFromDb(0);
        verify(mainActivityPresenterListener).onCitiesResultFromDb(any(List.class), 0);
    }

    @Test
    public void getCitiesFromDbNull() {
        mockDbEmptyCitiesNull();
        mainActivityInteractor.getCitiesFromDb(0);
        verify(mainActivityPresenterListener).onCitiesResultFromDb(null, 0);
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
    public void tearDown(){
        RxAndroidPlugins.reset();
        RxJavaHooks.reset();
    }

}
