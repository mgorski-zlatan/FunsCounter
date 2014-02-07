package com.foursee.funscounter.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.foursee.funscounter.R;
import com.foursee.funscounter.model.Event;
import com.foursee.funscounter.model.Event.OnEventChangeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StadiumView extends LinearLayout implements OnEventChangeListener {

	@InjectView(R.id.teams)
	TextView teams;
	@InjectView(R.id.type)
	TextView type;
	@InjectView(R.id.date)
	TextView date;
	@InjectView(R.id.stand)
	TextView stand;
	@InjectView(R.id.funs)
	TextView funs;
	@InjectView(R.id.north_stand)
	ImageView northStand;
	@InjectView(R.id.east_stand)
	ImageView eastStand;
	@InjectView(R.id.south_stand)
	ImageView southStand;
	@InjectView(R.id.away_stand)
	ImageView awayStand;
	@InjectView(R.id.west_stand)
	ImageView westStand;
	@InjectView(R.id.pitch)
	ImageView pitch;

	private final SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm\tdd.MM.yyyy");
	private Event event;
	private Stand currentStand = Stand.Stadium;

	public StadiumView(Context context) {
		super(context);
		init(context);
	}

	public StadiumView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public StadiumView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.stadium_view, this, true);
		ButterKnife.inject(this, this);
	}

	public void setModel(Event event) {
		this.event = event;
		event.registerListener(this);
	}

	@Override
	public void onEventChange(ArrayList<String> chageGates) {
		teams.setText(event.getDescription());
		date.setText(dateFormater.format(event.getDate()));
		type.setText(event.getType());

		fillWithData(currentStand);
		highlightStand(chageGates);
	}

	@OnClick(R.id.pitch)
	void onPitchClick() {
		fillWithData(Stand.Stadium);
	}

	@OnClick(R.id.north_stand)
	void onNorthStandClick() {
		fillWithData(Stand.NorthStand);
		northStand.setImageResource(R.drawable.north_stand_selector);
	}

	@OnClick(R.id.east_stand)
	void onEastStandClick() {
		fillWithData(Stand.EastStand);
		eastStand.setImageResource(R.drawable.east_stand_selector);
	}

	@OnClick(R.id.south_stand)
	void onSouthStandClick() {
		fillWithData(Stand.SouthStand);
		southStand.setImageResource(R.drawable.south_stand_selector);
	}

	@OnClick(R.id.away_stand)
	void onAwayStandClick() {
		fillWithData(Stand.AwayStand);
		awayStand.setImageResource(R.drawable.away_stand_selector);
	}

	@OnClick(R.id.west_stand)
	void onWestStandClick() {
		fillWithData(Stand.WestStand);
		westStand.setImageResource(R.drawable.west_stand_selector);
	}

	private void fillWithData(Stand standToFill) {
		currentStand = standToFill;
		if (event != null) {
			stand.setText(standToFill.name);
			String amountString;
			if (standToFill == Stand.Stadium) {
				amountString = event.operations.getAmount() + " / " + event.operations.getCapacity();
			} else {
				amountString = event.operations.getAmount(standToFill.name) + " / " + event.operations.getCapacity(standToFill.name);
			}
			funs.setText(amountString);
		}
	}

	private void highlightStand(ArrayList<String> chageGates) {
		for (String standName : chageGates) {
			switch (standName) {
			case "North Stand":
				northStand.setImageResource(R.drawable.north_stand_alert);
				break;
			case "East Stand":
				eastStand.setImageResource(R.drawable.east_stand_alert);
				break;
			case "South Stand":
				southStand.setImageResource(R.drawable.south_stand_alert);
				break;
			case "Away Stand":
				awayStand.setImageResource(R.drawable.away_alert);
				break;
			case "West Stand":
				westStand.setImageResource(R.drawable.west_stand_alert);
				break;
			}
		}
	}

	private enum Stand {
		Stadium("Stadium"), //
		NorthStand("North Stand"), //
		EastStand("East Stand"), //
		SouthStand("South Stand"), //
		AwayStand("Away Stand"), //
		WestStand("West Stand");

		public final String name;

		private Stand(String name) {
			this.name = name;
		}
	}
}