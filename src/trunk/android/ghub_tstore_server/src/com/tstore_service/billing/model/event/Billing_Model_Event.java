package com.tstore_service.billing.model.event;

import com.github.aadt.kernel.event.Event;

public class Billing_Model_Event extends Event {
  static public final String START_COMPLETE = "start_complete";
  static public final String PURCHASE_COMPLETE = "purchase_complete";
  static public final String CONSUME_COMPLETE = "consume_complete";
  static public final String GET_PURCHASE_PRODUCTS_COMPLETE = "get_purchase_products_complete";
  
  public Billing_Model_Event(String event_name) {
    super(event_name);
  }
}
