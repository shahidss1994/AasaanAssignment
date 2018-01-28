package com.shock.aasaanjobassignment.page.city.di;

import com.shock.aasaanjobassignment.page.city.interactor.IMainActivityInteractor;
import com.shock.aasaanjobassignment.page.city.interactor.MainActivityInteractor;
import com.shock.aasaanjobassignment.page.city.presenter.IMainActivityPresenter;
import com.shock.aasaanjobassignment.page.city.presenter.MainActivityPresenter;
import com.shock.aasaanjobassignment.page.city.view.IMainActivityView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shahid shaikh on 26-01-2018.
 */
@Module
public class CityModule {

    public final IMainActivityView mainActivityView;

    public CityModule(IMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Provides
    IMainActivityView provideMainActivityView() {
        return mainActivityView;
    }

    @Provides
    IMainActivityInteractor provideMainActivityInteractor(MainActivityInteractor mainActivityInteractor) {
        return mainActivityInteractor;
    }

    @Provides
    IMainActivityPresenter provideMainActivityPresenter(MainActivityPresenter mainActivityPresenter) {
        return mainActivityPresenter;
    }

}
