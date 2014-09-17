package com.github.aadt.kernel.actor.events;

import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import com.github.aadt.kernel.event.Event;

public class Proxy_Event extends Event {
  public static final String PROXY_EVENT = "proxy_event";
    
  private Event send_event;
  
  public Proxy_Event() {
    super(PROXY_EVENT);
  }
  
  public void set_event(Event send_event) {
    this.send_event = send_event;
  }

  public String convert_json() {
    JSONObject result = new JSONObject();
    result.put("type", getClass().getName());
    result.put("name", get_name());
    result.put("data", get_data());
    result.put("target_type", send_event.getClass().getName());
    result.put("target_name", send_event.get_name());
    result.put("target_data", send_event.get_data());
    return result.toJSONString();
  }
}