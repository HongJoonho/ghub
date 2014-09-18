package com.github.aadt.kernel.actor.events;

import java.util.HashMap;

import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import com.github.aadt.kernel.event.Event;

public class Proxy_Event extends Event {
  public static final String PROXY_EVENT = "proxy_event";

  private Event send_event;

  public Proxy_Event() {
    super(PROXY_EVENT);
  }
  
  public Proxy_Event(String name, Object data) {
    super(name);
    set_data(data);
  }

  public void set_event(Event send_event) {
    this.send_event = send_event;
    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("target_type", send_event.getClass().getName());
    data.put("target_name", send_event.get_name());
    data.put("target_data", send_event.get_data());
    set_data(data);
  }
  
  public String get_target_name() {
    HashMap<String, Object> data = (HashMap<String, Object>)get_data();
    return (String)data.get("target_name");
  }
  
  public String get_target_type() {
    HashMap<String, Object> data = (HashMap<String, Object>)get_data();
    return (String)data.get("target_type");
  }
  
  public Object get_target_data() {
    HashMap<String, Object> data = (HashMap<String, Object>)get_data();
    return data.get("target_data");
  }
}