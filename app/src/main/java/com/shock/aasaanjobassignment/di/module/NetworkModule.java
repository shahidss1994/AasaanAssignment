package com.shock.aasaanjobassignment.di.module;

import android.app.Application;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shock.aasaanjobassignment.Constants;
import com.shock.aasaanjobassignment.di.App;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shahid on 25/1/18.
 */
@Module
public class NetworkModule {

    private final String TAG = NetworkModule.class.getSimpleName();

    App app;

    public NetworkModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    @Named("RESTFUL")
    Cache provideCacheRestful(Application application) {
        int cacheSize = 15 * 1024 * 1024;   // 15 MB
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.create();
    }

    @Provides
    @Singleton
    @Named("RESTFUL")
    OkHttpClient provideOkhttpClientRestful(@Named("RESTFUL") Cache cache) {
        int timeout = 10;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS);
        builder.cache(cache);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.d(TAG, "Request -> " + request);
                Response response = chain.proceed(request);
                Log.d(TAG, "Response -> " + response);
                // if we want to grab a specific cookie or something.. place here..
                return response;
            }
        });
        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, @Named("RESTFUL") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
