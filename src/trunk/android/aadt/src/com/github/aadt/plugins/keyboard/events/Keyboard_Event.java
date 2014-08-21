package com.github.aadt.plugins.keyboard.events;

import com.github.aadt.kernel.event.Event;

public class Keyboard_Event extends Event {
  public static final String SHOW = "show";
  public static final String START_SHOW = "start_show";
  public static final String HIDE = "hide";
  public static final String START_HIDE = "start_hide";
  
  public Keyboard_Event(String name) {
    super(name);
  }
}
