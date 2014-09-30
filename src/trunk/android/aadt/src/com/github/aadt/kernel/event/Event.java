package com.github.aadt.kernel.event;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.github.aadt.kernel.actor.Actor;
import com.github.aadt.kernel.event.errors.Event_Deserialize_Error;

public class Event {
  private String name;
  private Object data;
  private Actor receive_target;

  public Event(String event_name) {
    this.name = event_name;
  }

  public Event(String name, Object data) {
    this.name = name;
    this.data = data;
  }

  public void set_data(Object data) {
    this.data = data;
  }

  public String get_name() {
    return name;
  }

  public Object get_data() {
    return data;
  }

  public void set_receive_target(Actor actor) {
    receive_target = actor;
  }

  public Actor get_receive_target() {
    return receive_target;
  }

  public static String serialize(Event event) {
    JSONObject result = new JSONObject();
    result.put("type", event.getClass().getName());
    result.put("name", event.get_name());
    result.put("data", event.get_data()); 
    return result.toJSONString();
  }

  public static Event deserialzie(String json_string) throws Event_Deserialize_Error {
    JSONObject json_data = (JSONObject)JSONValue.parse(json_string);
    String type = (String)json_data.get("type");
    String name = (String)json_data.get("name");
    Object data = json_data.get("data");
    try {
      String class_name = Event_Database.get_instance().get_class_name(type);
      Class<Event> class_type = (Class<Event>) Class.forName(class_name);
      Class<?>[] types = new Class[]{String.class, Object.class};
      Constructor<Event> constructor = class_type.getConstructor(types);
      return constructor.newInstance(new Object[]{name, data});   
    }
    catch (ClassNotFoundException e) {
      throw new Event_Deserialize_Error(e.getMessage());
    }
    catch (NoSuchMethodException e) {
      throw new Event_Deserialize_Error(e.getMessage());
    }
    catch (InvocationTargetException e) {
      throw new Event_Deserialize_Error(e.getMessage());
    }
    catch (IllegalAccessException e) {
      throw new Event_Deserialize_Error(e.getMessage());
    }
    catch (InstantiationException e) {
      throw new Event_Deserialize_Error(e.getMessage());
    }
  }
  
  public static Event deserialize_by_parameter(String type, String name, Object data) throws Event_Deserialize_Error{
    try {
      String class_name = Event_Database.get_instance().get_class_name(type);
      Class<Event> class_type = (Class<Event>) Class.forName(class_name);
      Class<?>[] types = new Class[]{String.class, Object.class};
      Constructor<Event> constructor = class_type.getConstructor(types);
      return constructor.newInstance(new Object[]{name, data});   
    }
    catch (ClassNotFoundException e) {
      throw new Event_Deserialize_Error(e.getMessage());
    }
    catch (NoSuchMethodException e) {
      throw new Event_Deserialize_Error(e.getMessage());
    }
    catch (InvocationTargetException e) {
      throw new Event_Deserialize_Error(e.getMessage());
    }
    catch (IllegalAccessException e) {
      throw new Event_Deserialize_Error(e.getMessage());
    }
    catch (InstantiationException e) {
      throw new Event_Deserialize_Error(e.getMessage());
    }
  }
}