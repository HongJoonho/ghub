package com.github.aadt.kernel.actor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Dispatcher;
import com.github.aadt.kernel.util.Logger;
import com.github.aadt.kernel.actor.Component;
import com.github.aadt.kernel.actor.errors.Actor_Error;

public class Actor extends Event_Dispatcher {
  private List<Component> components;
  private String path;

  public Actor(String path) {
    components = new ArrayList<Component>();
    this.path = path;
  }

  public String get_path() {
    return path; 
  }

  /*
	public <T extends Component> Component add_component(Class<T> component_type) {
	  try {
	    T result = component_type.newInstance();
	    components.add(result);
	    return result;
	  }
	  catch (IllegalAccessException e) {
	    Logger.debug(e.getMessage());
	  }
	  catch (InstantiationException e) {
	    Logger.debug(e.getMessage());
	  }
	  return new Null_Component();
	}
   */
  public <T extends Component> T add_component(Class<T> component_class) throws Actor_Error {
    try {
      T result = component_class.newInstance();
      result.set_parent(this);
      components.add(result);
      return result;
    }
    catch (IllegalAccessException e) {
      Logger.debug(e.getMessage());
    }
    catch (InstantiationException e) {
      Logger.debug(e.getMessage());
    }
    throw new Actor_Error("add component failed.");
  }

  public <T extends Component> Component get_component(Class<T> component_type) {
    for (Iterator<Component> it = components.iterator(); it.hasNext();) {
      Component item = it.next();
      if (component_type.equals(item.getClass()))
        return item;
    }
    return new Null_Component();
  }
  
  public Component get_component_by_name(String class_name) {
    for (Iterator<Component> it = components.iterator(); it.hasNext();) {
      Component item = it.next();
      if (item.getClass().getName().equals(class_name))
        return item;
    }
    return new Null_Component();
  }

  public void dispatch_event(Event event) {
    event.set_receive_target(this);
    super.dispatch_event(event);
  }
  
  public boolean is_null() {
    return false;
  }
}
