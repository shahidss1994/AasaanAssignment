package com.shock.aasaanjobassignment.di.module;

import com.shock.aasaanjobassignment.Constants;
import com.shock.aasaanjobassignment.db.AppDbHelper;
import com.shock.aasaanjobassignment.db.IDbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shahid on 25/01/18.
 */

@Module
public class AppDbModule {

    @Provides
    String provideDatabaseName() {
        return Constants.DB_NAME;
    }

    @Provides
    @Singleton
    IDbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

}
