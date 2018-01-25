package com.shock.aasaanjobassignment.di.module;

import com.shock.aasaanjobassignment.db.AppDbHelper;
import com.shock.aasaanjobassignment.db.DbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shahid on 25/01/18.
 */

@Module
public class AppDbModule {

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

}
