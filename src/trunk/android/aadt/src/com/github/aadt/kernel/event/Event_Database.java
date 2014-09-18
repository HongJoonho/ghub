package com.github.aadt.kernel.event;

import java.util.HashMap;

public class Event_Database {
  private HashMap<String, String> class_names;
  
  public Event_Database() {
    class_names = new HashMap<String, String>();
  }
  
  public String get_class_name(String event_name) {
    return class_names.get(event_name);
  }
  
  public void add_event(String event_name, String class_name) {
    class_names.put(event_name, class_name);
  }
  
  private static Event_Database instance = null;
  
  public static Event_Database get_instance() {
    if (null == instance)
      instance = new Event_Database();
    return instance;
  }
}
