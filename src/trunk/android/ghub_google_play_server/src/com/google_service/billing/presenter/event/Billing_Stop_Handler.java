package com.google_service.billing.presenter.event;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google_service.billing.model.Billing_Model;

public class Billing_Stop_Handler extends Event_Handler {
  private Billing_Model model;

  public Billing_Stop_Handler(Billing_Model model) {
    this.model = model;
  }

  @Override
  public void dispatch(Event event) {
    model.stop();
  }

}
