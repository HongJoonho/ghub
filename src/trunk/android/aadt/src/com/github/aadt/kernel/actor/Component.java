package com.github.aadt.kernel.actor;
import com.github.aadt.kernel.core.Kernel;
import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Dispatcher;
import com.github.aadt.kernel.util.Logger;

public class Component extends Event_Dispatcher {
  private Actor parent_actor;

  public void set_parent(Actor actor) {
    parent_actor = actor;
  }

  public void dispatch_event(Event event) {
    event.set_receive_target(parent_actor);
    super.dispatch_event(event);
  }
  
  public static Component find_component(String path, String class_name) {
    Actor actor = Kernel.get_instance().find(path);
    if (actor.is_null())
      return new Null_Component();
    return actor.get_component_by_name(class_name);
  }

  public boolean is_null() {
    return false; 
  }
}