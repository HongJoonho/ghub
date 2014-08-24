package com.github.aadt.plugins.keyboard.events;

import java.util.HashMap;
import java.util.Map;

import com.github.aadt.kernel.event.Event;

public class Keyboard_Resize extends Event {
  public static final String RESIZE = "resize";
  
  public int layout_height;

  public Keyboard_Resize(int layout_height) {
   super(RESIZE); 
   this.layout_height = layout_height;
  }
  
  @Override
  public Object get_data() {
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("layout_height", layout_height);
    return data;
  }
}
