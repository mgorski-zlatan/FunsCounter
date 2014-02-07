package com.foursee.funscounter.model;

import com.foursee.funscounter.model.operation.EventOperations;

import java.util.ArrayList;
import java.util.Date;

public class Event {
	private int id;
	private String type;
	private String description;
	private Date date;
	private Gate.List gates = new Gate.List();

	private final ArrayList<OnEventChangeListener> listeners = new ArrayList<OnEventChangeListener>();
	public final EventOperations operations = new EventOperations(this);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Gate.List getGates() {
		return gates;
	}

	public void setGates(Gate.List gates) {
		this.gates = gates;
	}

	public void refresh(Event event) {
		id = event.getId();
		type = event.getType();
		description = event.getDescription();
		date = event.getDate();

		ArrayList<String> compareGates = operations.compareGates(event.getGates());
		gates = event.getGates();
		notifyListeners(compareGates);
	}

	private void notifyListeners(ArrayList<String> changeGates) {
		for (OnEventChangeListener listener : listeners) {
			listener.onEventChange(changeGates);
		}
	}

	public void registerListener(OnEventChangeListener listener) {
		listeners.add(listener);
	}

	public void unregisterListener(OnEventChangeListener listener) {
		listeners.remove(listener);
	}

	@SuppressWarnings("serial")
	public static class List extends ArrayList<Event> {
	}

	@SuppressWarnings("serial")
	public interface OnEventChangeListener {
		void onEventChange(ArrayList<String> changeGates);
	}
}