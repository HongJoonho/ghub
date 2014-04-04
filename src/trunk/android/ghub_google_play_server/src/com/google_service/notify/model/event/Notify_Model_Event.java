package com.google_service.notify.model.event;

import com.gdkompanie.gdos.event.Event;

public class Notify_Model_Event extends Event {
  public static String START_COMPLETE = "start_complete";
  public static String REGISTER_ID_COMPLETE = "register_id_complete";
  
  public Notify_Model_Event(String event_name) {
    super(event_name);
  }
}
