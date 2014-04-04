package com.tstore_service.billing.presenter.event;

import java.util.Hashtable;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.tstore_service.billing.model.dto.Purchase_Complete_DTO;
import com.tstore_service.billing.presenter.Billing_Presenter;

public class Billing_Consume_Complete_Handler extends Event_Handler {
  private Billing_Presenter presenter;
  private Purchase_Complete_DTO purchase;

  public Billing_Consume_Complete_Handler(Billing_Presenter presenter, Purchase_Complete_DTO purchase) {
    this.presenter = presenter;
    this.purchase = purchase;
  }

  @Override
  public void dispatch(Event event) {
    Hashtable<String, Object> data = (Hashtable<String, Object>) event.get_data();
    Boolean is_success = (Boolean)data.get("is_success");
    if (is_success)
      occur_success_event();
    else
      occur_fail_event();
  }
  
  private void occur_success_event() {
    Billing_Presenter_Event event = new Billing_Presenter_Event(Billing_Presenter_Event.CONSUME_COMPLETE);
    event.set_data(create_success_data());
    presenter.dispatch_event(event);
  }
  
  private Hashtable<String, Object> create_success_data() {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", true);
    result.put("result", purchase);
    return result;
  }
  
  private void occur_fail_event() {
    Billing_Presenter_Event event = new Billing_Presenter_Event(Billing_Presenter_Event.CONSUME_COMPLETE);
    event.set_data(create_fail_data());
    presenter.dispatch_event(event);
  }
  
  private Hashtable<String, Object> create_fail_data() {
    Hashtable<String, Object> result = new Hashtable<String, Object>();
    result.put("is_success", false);
    return result;
  }
}
