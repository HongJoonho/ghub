package com.google_service.billing.presenter.event;

import java.util.Dictionary;

import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Handler;
import com.google_service.billing.model.Billing_Model;
import com.google_service.billing.model.event.Billing_Model_Event;
import com.google_service.billing.presenter.Billing_Presenter;
import com.google_service.billing.view.Billing_View;

public class Billing_Purchase_Handler extends Event_Handler {
  private Billing_Model model;
  private Billing_Presenter presenter;
  private Billing_View view;

  public Billing_Purchase_Handler(Billing_View view, Billing_Model model, Billing_Presenter presenter) {
    this.view = view;
    this.model = model;
    this.presenter = presenter;
  }

  @Override
  public void dispatch(Event event) {
    Dictionary<String, Object> event_data = (Dictionary<String, Object>) event.get_data();
    String product_id = (String) event_data.get("product_id");
    model.remove_event_listener(Billing_Model_Event.CONSUME_COMPLETE);
    model.add_event_listener(Billing_Model_Event.CONSUME_COMPLETE, new Billing_Consume_Complete_Handler(presenter));
    model.remove_event_listener(Billing_Model_Event.PURCHASE_COMPLETE);
    model.add_event_listener(Billing_Model_Event.PURCHASE_COMPLETE, new Billing_Purchase_Complete_Handler(presenter, view));
    model.purchase(product_id);
  }

}
