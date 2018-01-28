package com.shock.aasaanjobassignment.page.city.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.shock.aasaanjobassignment.Constants;
import com.shock.aasaanjobassignment.R;
import com.shock.aasaanjobassignment.di.App;
import com.shock.aasaanjobassignment.page.AppBaseActivity;
import com.shock.aasaanjobassignment.page.city.di.CityModule;
import com.shock.aasaanjobassignment.page.city.model.CitiesResponse;
import com.shock.aasaanjobassignment.page.city.model.City;
import com.shock.aasaanjobassignment.page.city.presenter.IMainActivityPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppBaseActivity implements IMainActivityView, SearchView.OnQueryTextListener {


    @Inject
    IMainActivityPresenter mainActivityPresenter;

    @BindView(R.id.rv_city)
    RecyclerView recyclerView;

    @BindView(R.id.sv_city)
    SearchView searchView;

    private Unbinder unbinder;
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initDI();
        //showProgressDailog(false);
        mainActivityPresenter.getCitiesFromDb(0);
    }

    private void initDI() {
        App.get().getAppComponent().plus(new CityModule(this)).inject(this);
    }

    @Override
    public void setData(List<City> cities) {
        if (cities != null && cities.size() > 0) {
            if (cityAdapter == null) {
                cityAdapter = new CityAdapter(cities, mainActivityPresenter, this);
                recyclerView.setAdapter(cityAdapter);
                setupSearchView();
            } else {
                cityAdapter.addCities(cities);
            }
        } else {
            if (cityAdapter != null) {
                cityAdapter.setHasMoreData(false);
            }
        }
    }

    @Override
    public void showToastMsg(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("Search Here");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        unbinder = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (cityAdapter != null) {
            cityAdapter.filter(newText);
            return true;
        }
        return false;
    }
}
