package com.github.aadt.plugins.activity.events;

import org.json.simple.JSONValue;
import org.json.simple.JSONObject;

import com.github.aadt.kernel.event.Event;

public class Direct_Event extends Event {
  private Event send_event;
  
  public Direct_Event() {
    super("direct_event");
  }
  
  public void set_event(Event send_event) {
    this.send_event = send_event;
  }

  public String convert_json() {
    JSONObject result = new JSONObject();
    result.put("name", get_name()); 
    result.put("send_name", send_event.get_name());
    result.put("send_data", send_event.get_data());
    return result.toJSONString();
  }
}
