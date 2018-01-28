/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.shock.aasaanjobassignment.db;

import com.shock.aasaanjobassignment.Constants;
import com.shock.aasaanjobassignment.page.city.model.City;
import com.shock.aasaanjobassignment.page.city.model.DaoMaster;
import com.shock.aasaanjobassignment.page.city.model.DaoSession;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by shahid on 25/1/18.
 */
public class AppDbHelper implements IDbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }


    @Override
    public Observable<Long> insertCity(final City city) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mDaoSession.getCityDao().insertOrReplace(city);
            }
        });
    }

    @Override
    public Observable<Boolean> insertCities(final List<City> cities) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getCityDao().insertOrReplaceInTx(cities);
                return true;
            }
        });
    }

    @Override
    public Observable<List<City>> getCities() {
        return Observable.fromCallable(new Callable<List<City>>() {
            @Override
            public List<City> call() throws Exception {
                return mDaoSession.getCityDao().loadAll();
            }
        });
    }

    @Override
    public Observable<List<City>> getCitiesFromTo(final int offset) {
        /*return Observable.rangeLong(from,to).map(new Function<Long, City>() {
            @Override
            public City apply(Long aLong) throws Exception {
                return mDaoSession.getCityDao().loadByRowId(aLong);
            }
        }).toList().toObservable()*/;
        return Observable.fromCallable(new Callable<List<City>>() {
            @Override
            public List<City> call() throws Exception {
                return mDaoSession.getCityDao().queryBuilder().offset(offset).limit(Constants.LIMIT).list();
            }
        });
    }

    @Override
    public Observable<City> getCity(final long cityId) {
        return Observable.fromCallable(new Callable<City>() {
            @Override
            public City call() throws Exception {
                return mDaoSession.getCityDao().loadByRowId(cityId);
            }
        });
    }
}
