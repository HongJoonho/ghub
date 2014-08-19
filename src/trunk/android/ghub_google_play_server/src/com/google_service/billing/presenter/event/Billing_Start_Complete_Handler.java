package com.google_service.billing.presenter.event;

import java.util.Dictionary;

import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Handler;
import com.github.aadt.kernel.util.Logger;
import com.google_service.billing.presenter.Billing_Presenter;
import com.google_service.billing.util.IabResult;
import com.google_service.billing.model.Billing_Model;
import com.google_service.billing.model.event.Billing_Model_Event;

public class Billing_Start_Complete_Handler extends Event_Handler {
  private Billing_Presenter presenter;

  public Billing_Start_Complete_Handler(Billing_Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void dispatch(Event event) {
    Billing_Model_Event model_event = (Billing_Model_Event) event;
    IabResult result = (IabResult) model_event.get_data();
    if (result.isSuccess()) {
      Billing_Presenter_Event complete_event = new Billing_Presenter_Event(
          Billing_Presenter_Event.START_COMPLETE);
      presenter.dispatch_event(complete_event);
    }
  }
}
