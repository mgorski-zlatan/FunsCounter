package com.foursee.funscounter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.foursee.funscounter.BaseApplication;
import com.foursee.funscounter.R;
import com.foursee.funscounter.model.Event;
import com.foursee.funscounter.network.rest.NetworkService;
import com.foursee.funscounter.service.EventUpdateServiceController;
import com.foursee.funscounter.ui.view.StadiumView;
import com.octo.android.robospice.SpiceManager;

public class ContentFragment extends Fragment {

	private static final String EVENT_ID = "event_id";

	public static ContentFragment create(int eventId) {
		Bundle bundle = new Bundle();
		bundle.putInt(EVENT_ID, eventId);
		ContentFragment fragment = new ContentFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@InjectView(R.id.stadium)
	StadiumView stadium;

	private EventUpdateServiceController serviceController;
	private final SpiceManager spiceManager = new SpiceManager(NetworkService.class);
	private final Event event = new Event();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_fragment, container, false);
		ButterKnife.inject(this, view);
		setHasOptionsMenu(true);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		spiceManager.start(getActivity());
		startAutoUpdate();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.reset(this);
		spiceManager.shouldStop();
		serviceController.stopService();
	}

	private void startAutoUpdate() {
		((BaseApplication) getActivity().getApplication()).setCurrentEvent(event);
		stadium.setModel(event);

		serviceController = new EventUpdateServiceController(getActivity());
		int eventId = getArguments().getInt(EVENT_ID);
		serviceController.startService(eventId);
	}
}