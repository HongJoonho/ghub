package com.github.aadt.kernel.core;

import java.util.HashMap;
import java.util.Map;
import com.github.aadt.kernel.actor.Actor;
import com.github.aadt.kernel.actor.Null_Actor;

public class Kernel {
  private Map<String, Actor> actors;
  
  public Kernel() {
    actors = new HashMap<String, Actor>();
  }
  
  public Actor new_object(String path) {
    Actor result = new Actor(path);
    actors.put(path, result);
    return result;
  }
  
  public void delete_object(String path) {
    actors.remove(path);
  }
  
  public Actor find(String path) {
    if (actors.containsKey(path))
      return actors.get(path);
    return new Null_Actor();
  }
  
  private static Kernel instance = null;
  
  public static Kernel get_instance() {
   if (null == instance) 
     instance = new Kernel();
   return instance;
  }
}
