package com.shock.aasaanjobassignment.page.city.view;

import com.shock.aasaanjobassignment.page.city.model.CitiesResponse;
import com.shock.aasaanjobassignment.page.city.model.City;

import java.util.List;

/**
 * Created by shahid shaikh on 26-01-2018.
 */

public interface IMainActivityView {

    void setData(List<City> cities);

    void showToastMsg(String msg);
}
