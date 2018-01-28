package com.shock.aasaanjobassignment.page.city.presenter;

/**
 * Created by shahid shaikh on 26-01-2018.
 */

public interface IMainActivityPresenter {

    void getCitiesFromDb(int offset);

    void getCitiesFromApi(int offset);

    void showMsg(String msg);

}
