package com.shock.aasaanjobassignment.di.component;

import com.shock.aasaanjobassignment.di.module.AppDbModule;
import com.shock.aasaanjobassignment.di.module.AppModule;
import com.shock.aasaanjobassignment.di.module.NetworkModule;
import com.shock.aasaanjobassignment.di.module.ServiceModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by shahid on 10/8/16.
 */
@Singleton
@Component (modules = {AppModule.class, NetworkModule.class, ServiceModel.class, AppDbModule.class})
public interface AppComponent {


}
