package com.shock.aasaanjobassignment.di.module;

import com.shock.aasaanjobassignment.network.INetworkService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by shahid on 25/1/18.
 */
@Module
public class ServiceModel {

    @Provides
    INetworkService provideLoginService(Retrofit retrofit){
        return retrofit.create(INetworkService.class);
    }

}
