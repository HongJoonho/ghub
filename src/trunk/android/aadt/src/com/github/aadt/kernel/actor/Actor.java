package com.github.aadt.kernel.actor;

import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Dispatcher;
import com.github.aadt.kernel.event.Event_Handler;

public class Actor {
	private Event_Dispatcher dispatcher;

	public Actor() {
		dispatcher = new Event_Dispatcher();
	}

	public void add_event_listener(String event_name, Event_Handler handler) {
		dispatcher.add_event_listener(event_name, handler);
	}

	public void remove_event_listener(String event_name) {
		dispatcher.remove_event_listener(event_name);
	}

	public void dispatch_event(Event event) {
		event.set_receive_target(this);
		dispatcher.dispatch_event(event);
	}
}
