package com.google_service.billing.presenter.event;

import java.util.Dictionary;

import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Handler;
import com.google_service.billing.presenter.Billing_Presenter;
import com.google_service.billing.model.Billing_Model;
import com.google_service.billing.model.event.Billing_Model_Event;

public class Billing_Start_Handler extends Event_Handler {
  private Billing_Presenter presenter;
  private Billing_Model model;

  public Billing_Start_Handler(Billing_Model model, Billing_Presenter presenter) {
    this.model = model;
    this.presenter = presenter;
  }

  @Override
  public void dispatch(Event event) {
    Dictionary<String, Object> event_data = (Dictionary<String, Object>) event.get_data();
    String license_key = (String) event_data.get("license_key");
    model.remove_event_listener(Billing_Model_Event.CONSUME_COMPLETE);
    model.add_event_listener(Billing_Model_Event.CONSUME_COMPLETE, new Billing_Consume_Complete_Handler(presenter));
    model.add_event_listener(Billing_Model_Event.START_COMPLETE, new Billing_Start_Complete_Handler(presenter));
    model.start(license_key);
  }

}
