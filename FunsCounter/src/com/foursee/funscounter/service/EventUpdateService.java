package com.foursee.funscounter.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.foursee.funscounter.BaseApplication;
import com.foursee.funscounter.model.Auth;
import com.foursee.funscounter.model.Event;
import com.foursee.funscounter.network.listeners.EventDataRequestListener;
import com.foursee.funscounter.network.rest.NetworkService;
import com.foursee.funscounter.network.rest.request.EventDataSpiceRequest;
import com.octo.android.robospice.SpiceManager;

import java.util.Timer;
import java.util.TimerTask;

public class EventUpdateService extends Service {

	private static final String TAG = "event_Service";

	public static final String EVENT_ID = "event_id";

	private final SpiceManager spiceManager = new SpiceManager(NetworkService.class);
	private Auth auth;
	private int eventId;
	private Event event;

	private Timer timer;

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();
		spiceManager.start(this);
		auth = ((BaseApplication) getApplication()).getAuth();
		event = ((BaseApplication) getApplication()).getCurrentEvent();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		eventId = intent.getIntExtra(EVENT_ID, -1);
		stopTimer();

		timer = new Timer();
		int interval = 5000;
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				updateEvent();
			}
		}, 0, interval);

		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void updateEvent() {
		Log.d(TAG, "updateEvent");
		spiceManager.execute(new EventDataSpiceRequest(auth, eventId), new EventDataRequestListener(event));
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		spiceManager.shouldStop();
		stopTimer();
		super.onDestroy();
	}
}