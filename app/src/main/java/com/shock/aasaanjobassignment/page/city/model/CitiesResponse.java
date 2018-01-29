package com.shock.aasaanjobassignment.page.city.model;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

import okhttp3.HttpUrl;

@Generated("com.robohorse.robopojogenerator")
public class CitiesResponse {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("objects")
    private List<City> cities;

    public CitiesResponse(List<City> cities){
        this.cities = cities;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<City> getCities() {
        return cities;
    }
}