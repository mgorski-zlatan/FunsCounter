package com.foursee.funscounter.network.rest;

import com.foursee.funscounter.model.Event;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

public interface IStadionQueries {

	@POST("/getEvents.php")
	Event.List getAllEvents(@Header("Authorization") String auth);

	@FormUrlEncoded
	@POST("/getEventData.php")
	Event getEventData(@Header("Authorization") String auth, @Field("eventID") int eventId);

	@POST("/auth.php")
	Void auth(@Header("Authorization") String auth);
}