package com.shock.aasaanjobassignment.page.city.view;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shock.aasaanjobassignment.Constants;
import com.shock.aasaanjobassignment.R;
import com.shock.aasaanjobassignment.page.city.model.City;
import com.shock.aasaanjobassignment.page.city.presenter.IMainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shahid shaikh on 27-01-2018.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private final String TAG = CityAdapter.class.getSimpleName();

    private List<City> cities, filterCity;
    private IMainActivityPresenter mainActivityPresenter;
    private IMainActivityView mainActivityView;
    private boolean hasMoreData = true;

    public CityAdapter(List<City> cities, IMainActivityPresenter mainActivityPresenter, IMainActivityView mainActivityView) {
        this.mainActivityPresenter = mainActivityPresenter;
        this.cities = cities;
        this.filterCity = new ArrayList<>(cities);
        this.mainActivityView = mainActivityView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final City city = filterCity.get(position);
        holder.cityName.setText(city.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityView.showToastMsg(city.getName());
            }
        });
        if (position == cities.size() - 1) {
            if (hasMoreData) {
                mainActivityPresenter.getCitiesFromApi(getOffSet());
            }
        }
    }

    private int getOffSet() {
        int offset;
        if (cities.size() % Constants.LIMIT == 0) {
            offset = cities.size();
        } else {
            int round = cities.size() / 10;
            offset = (round * 10) + 10;
        }
        Log.d(TAG, "CityAdapter OffSet =>" + offset);
        return offset;
    }

    @Override
    public int getItemCount() {
        return filterCity.size();
    }

    public void addCities(List<City> cities) {
        if (cities.size() < 10 || cities.get(cities.size() - 1).getCityId().equals(this.cities.get(this.cities.size() - 1).getCityId())) {
            hasMoreData = false;
        }
        this.cities.addAll(cities);
        this.filterCity.addAll(cities);
        notifyDataSetChanged();
    }

    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
    }

    public void filter(final String filterText) {
        filterCity.clear();
        if (TextUtils.isEmpty(filterText)) {
            filterCity.addAll(cities);
            notifyDataSetChanged();
        } else {
            Observable.just(cities)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Function<List<City>, Observable<City>>() {
                        @Override
                        public Observable<City> apply(List<City> cityList) throws Exception {
                            return Observable.fromIterable(cityList);
                        }
                    })
                    .filter(new Predicate<City>() {
                        @Override
                        public boolean test(City city) throws Exception {
                            return city.getName().toLowerCase().contains(filterText.toLowerCase());
                        }
                    })
                    .subscribe(new Observer<City>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(City city) {
                            filterCity.add(city);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            notifyDataSetChanged();
                        }
                    });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.city_name)
        AppCompatTextView cityName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
