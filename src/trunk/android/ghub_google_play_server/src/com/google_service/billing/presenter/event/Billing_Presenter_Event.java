package com.google_service.billing.presenter.event;

import com.gdkompanie.gdos.event.Event;

public class Billing_Presenter_Event extends Event {
  public static final String START = "start";
  public static final String START_COMPLETE = "start_complete";
  public static final String PURCHASE = "purchase";
  public static final String PURCHASE_COMPLETE = "purchase_complete";
  public static final String CONSUME = "consume";
  public static final String CONSUME_COMPLETE = "consume_complete";
  public static final String STOP = "stop";
  public static final String ACTIVITY_RESULT = "activity_result";
  public static final String GET_INVENTORY = "get_inventory";
  public static final String GET_INVENTORY_COMPLETE = "get_inventory_complete"; 

  public Billing_Presenter_Event(String event_name) {
    super(event_name);
  }
}
