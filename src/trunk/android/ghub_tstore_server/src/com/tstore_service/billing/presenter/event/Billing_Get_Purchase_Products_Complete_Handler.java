package com.tstore_service.billing.presenter.event;

import java.util.Hashtable;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.tstore_service.billing.presenter.Billing_Presenter;

public class Billing_Get_Purchase_Products_Complete_Handler extends Event_Handler {
  private Billing_Presenter presenter;

  public Billing_Get_Purchase_Products_Complete_Handler(Billing_Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void dispatch(Event event) {
    occur_presenter_event(event.get_data());
  }
  
  private void occur_presenter_event(Object data) {
    Billing_Presenter_Event event = new Billing_Presenter_Event(Billing_Presenter_Event.GET_PURCHASE_PRODUCTS_COMPLETE);
    event.set_data(data);
    presenter.dispatch_event(event);
  }
}
