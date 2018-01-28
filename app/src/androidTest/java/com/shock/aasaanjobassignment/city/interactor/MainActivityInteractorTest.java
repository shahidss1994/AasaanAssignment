package com.shock.aasaanjobassignment.city.interactor;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.shock.aasaanjobassignment.db.IDbHelper;
import com.shock.aasaanjobassignment.network.INetworkService;
import com.shock.aasaanjobassignment.page.city.interactor.MainActivityInteractor;
import com.shock.aasaanjobassignment.page.city.model.CitiesResponse;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

import io.reactivex.android.plugins.RxAndroidPlugins;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import rx.Scheduler;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * Created by shahid shaikh on 28-01-2018.
 */

public class MainActivityInteractorTest {

    private MainActivityInteractor mainActivityInteractor;

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

        mainActivityInteractor = new MainActivityInteractor(dbHelper, networkService);
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

    private void testResponseFromApi(int offset){
        MockWebServer mockWebServer = new MockWebServer();
        MockResponse mockResponse = new MockResponse();
        String response = null;
        try {
            InputStream is = context.getAssets().open("cityResponse");
            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            response = new String(buffer, "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        mockResponse.setBody(response);
        mockWebServer.enqueue(mockResponse);
        try {
            mockWebServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
