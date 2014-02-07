package com.foursee.funscounter.network.listeners;

import android.util.Log;

import com.foursee.funscounter.model.Event;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class EventDataRequestListener implements RequestListener<Event> {
	private final Event event;

	public EventDataRequestListener(Event event) {
		this.event = event;
	}

	@Override
	public void onRequestFailure(SpiceException e) {
		Log.d("event_Service", "onRequestFailure");
	}

	@Override
	public void onRequestSuccess(Event result) {
		Log.d("event_Service", "onRequestSuccess");
		event.refresh(result);
	}
}