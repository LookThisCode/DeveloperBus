package com.devbus.controller;

import java.util.ArrayList;
import java.util.List;
import com.devbus.data.Event;

public class Events {
	
	List<Event> events;

	public Events() {
		super();
	}

	public void fillList() {
		this.events = new ArrayList<Event>();
		int i = 0;
		this.events.add(new Event("09-11-2013", "Description"));
		this.events.add(new Event("02-11-2013", "Description"));
		this.events.add(new Event("15-10-2013", "Description"));
		this.events.add(new Event("08-10-2013", "Description"));		
	}

	public List<Event> getEvents() {
		return events;
	}

}
