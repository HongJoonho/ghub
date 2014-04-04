package com.google_service.billing.presenter.event;

import java.util.Dictionary;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google_service.billing.presenter.Billing_Presenter;
import com.google_service.billing.util.IabResult;
import com.google_service.billing.util.Purchase;

public class Billing_Get_Inventory_Complete_Handler extends Event_Handler {
  Billing_Presenter presenter;
  
  public Billing_Get_Inventory_Complete_Handler(Billing_Presenter preseeter) {
    this.presenter = preseeter;
  }

  @Override
  public void dispatch(Event event) {
    Billing_Presenter_Event complete_event = new Billing_Presenter_Event(Billing_Presenter_Event.GET_INVENTORY_COMPLETE);
    complete_event.set_data(event.get_data());
    presenter.dispatch_event(complete_event);
  }
}
