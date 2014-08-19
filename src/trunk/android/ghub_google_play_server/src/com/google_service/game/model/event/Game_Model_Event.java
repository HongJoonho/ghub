package com.google_service.game.model.event;

import com.github.aadt.kernel.event.Event;

public class Game_Model_Event extends Event {
  public static final String SIGN_IN_SUCCESSED = "sign_in_successed";
  public static final String SIGN_IN_FAILED = "sign_in_failed";

  public Game_Model_Event(String event_name) {
    super(event_name);
  }
}
