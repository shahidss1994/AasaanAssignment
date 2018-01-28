package com.shock.aasaanjobassignment.page.city.di;

import com.shock.aasaanjobassignment.page.city.view.MainActivity;

import dagger.Subcomponent;

/**
 * Created by shahid shaikh on 26-01-2018.
 */

@Subcomponent(modules = {CityModule.class})
public interface ICityComponent {

    void inject(MainActivity mainActivity);

}
