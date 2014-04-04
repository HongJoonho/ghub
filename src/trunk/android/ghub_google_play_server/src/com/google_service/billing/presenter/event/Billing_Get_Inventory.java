package com.google_service.billing.presenter.event;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google_service.billing.model.Billing_Model;
import com.google_service.billing.model.event.Billing_Model_Event;
import com.google_service.billing.presenter.Billing_Presenter;

public class Billing_Get_Inventory extends Event_Handler {
  private Billing_Model model;
  private Billing_Presenter presenter;

  public Billing_Get_Inventory(Billing_Model model, Billing_Presenter presenter) {
    this.model = model;
    this.presenter = presenter;
  }
  
  @Override
  public void dispatch(Event event) {
    model.add_event_listener(Billing_Model_Event.GET_INVENTORY_COMPLETE, new Billing_Get_Inventory_Complete_Handler(presenter));
    model.get_inventory_product();
  }

}
