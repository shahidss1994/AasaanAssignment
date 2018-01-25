package com.shock.aasaanjobassignment.page.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CitiesResponse {

	@SerializedName("meta")
	private Meta meta;

	@SerializedName("objects")
	private List<City> objects;

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	public void setObjects(List<City> objects){
		this.objects = objects;
	}

	public List<City> getObjects(){
		return objects;
	}
}