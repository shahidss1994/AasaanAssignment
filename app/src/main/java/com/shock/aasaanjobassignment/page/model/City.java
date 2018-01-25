package com.shock.aasaanjobassignment.page.model;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "City")
public class City {

    @Id (autoincrement = true)
    private Long id;

    @Property
    @SerializedName ("name")
    private String name;

    @Property (nameInDb = "city_id")
    @SerializedName ("id")
    private Long cityId;

    @SerializedName ("slug")
    private String slug;

    @Generated(hash = 1195418065)
    public City(Long id, String name, Long cityId, String slug) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
        this.slug = slug;
    }

    @Generated(hash = 750791287)
    public City() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCityId() {
        return cityId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }
}