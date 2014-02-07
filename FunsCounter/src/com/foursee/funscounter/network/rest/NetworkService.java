package com.foursee.funscounter.network.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.converter.GsonConverter;

public class NetworkService extends RetrofitGsonSpiceService {

	private final static String BASE_URL = "http://89.78.24.3:1024"; // "http://192.168.50.100:1024";

	@Override
	public void onCreate() {
		super.onCreate();
		addRetrofitInterface(IStadionQueries.class);
	}

	@Override
	protected Builder createRestAdapterBuilder() {
		return new RestAdapter.Builder() //
				.setServer(BASE_URL) //
				.setConverter(new GsonConverter(new GsonBuilder() //
						.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE) //
						.setDateFormat("yyyy-MM-dd HH:mm:ss") //
						.create() //
						)) //
				.setLogLevel(RestAdapter.LogLevel.BASIC);
	}

	@Override
	protected String getServerUrl() {
		return BASE_URL;
	}
}