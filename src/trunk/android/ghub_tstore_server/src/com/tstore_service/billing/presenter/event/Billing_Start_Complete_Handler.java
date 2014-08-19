package com.tstore_service.billing.presenter.event;

import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Handler;
import com.tstore_service.billing.presenter.Billing_Presenter;

public class Billing_Start_Complete_Handler extends Event_Handler {
  private Billing_Presenter presenter;
  
  public Billing_Start_Complete_Handler(Billing_Presenter presenter) {
    this.presenter = presenter;
  }
  
  @Override
  public void dispatch(Event event) {
    occur_presenter_event();
  }
  
  private void occur_presenter_event() {
    Billing_Presenter_Event event = new Billing_Presenter_Event(Billing_Presenter_Event.START_COMPLETE);
    presenter.dispatch_event(event);
  }

}
