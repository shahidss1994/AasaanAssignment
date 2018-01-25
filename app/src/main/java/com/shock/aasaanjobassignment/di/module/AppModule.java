package com.shock.aasaanjobassignment.di.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.shock.aasaanjobassignment.di.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shahid on 25/1/18.
 */
@Module
public class AppModule {

    App app;

    public AppModule(App app){
        this.app = app;
    }

    @Provides
    @Singleton
    protected Application provideApplication(){
        return app;
    }

    @Provides
    @Singleton
    protected Context provideContext(){
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    protected Resources provideResources(){
        return app.getResources();
    }

}
