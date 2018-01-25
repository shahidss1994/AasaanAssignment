package com.shock.aasaanjobassignment.di;

import android.app.Application;

import com.shock.aasaanjobassignment.di.component.AppComponent;
import com.shock.aasaanjobassignment.di.component.DaggerAppComponent;
import com.shock.aasaanjobassignment.di.module.AppModule;
import com.shock.aasaanjobassignment.di.module.NetworkModule;

/**
 * Created by shahid on 25/1/18.
 */
public class App extends Application {

    private static AppComponent appComponent;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initAppComponent();
    }

    public void initAppComponent() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(app)).networkModule(new NetworkModule(app)).build();
    }

    public static App get() {
        return app;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
