package com.facebook_service.auth.model.events;

import com.github.aadt.kernel.event.Event;

public class Auth_Model_Event extends Event {
  public static final String SESSION_IS_OPENED = "session_is_opened";
  public static final String SESSION_IS_CLOSED = "session_is_closed";

  public Auth_Model_Event(String event_name) {
    super(event_name);
  }
}

