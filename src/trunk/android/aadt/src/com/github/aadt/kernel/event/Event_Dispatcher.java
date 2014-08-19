package com.github.aadt.kernel.event;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Event_Dispatcher {
	private Dictionary<String, List<Event_Handler>> events;

	public Event_Dispatcher() {
		events = new Hashtable<String, List<Event_Handler>>();
	}

	public void add_event_listener(String name, Event_Handler handler) {
		List<Event_Handler> handlers = events.get(name);
		if (null == handlers) {
			handlers = new ArrayList<Event_Handler>();
			events.put(name, handlers);
		}
		handlers.add(handler);
	}

	public void remove_event_listener(String name) {
		events.remove(name);
	}

	public void dispatch_event(Event event) {
		List<Event_Handler> handlers = events.get(event.get_name());
		if (null != handlers) {
			for(Iterator<Event_Handler> iter = handlers.iterator(); iter.hasNext();) {
				Event_Handler handler = iter.next();
				handler.dispatch(event);
			}
		}
	}
}
