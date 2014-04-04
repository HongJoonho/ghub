package com.google_service.billing.presenter.event;

import java.util.Dictionary;
import java.util.Hashtable;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google_service.billing.presenter.Billing_Presenter;
import com.google_service.billing.util.IabResult;
import com.google_service.billing.util.Purchase;

public class Billing_Consume_Complete_Handler extends Event_Handler {
  private Billing_Presenter presenter;

  public Billing_Consume_Complete_Handler(Billing_Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void dispatch(Event event) {
    Dictionary<String, Object> data = (Dictionary<String, Object>) event.get_data();
    IabResult result = (IabResult) data.get("result");
    Purchase purchase = null;
    if (null != data.get("purchase"))
      purchase = (Purchase) data.get("purchase");
    if (result.isSuccess()) {
      Billing_Presenter_Event complete_event = new Billing_Presenter_Event(
          Billing_Presenter_Event.CONSUME_COMPLETE);
      complete_event.set_data(create_complete_data(purchase));
      presenter.dispatch_event(complete_event);
    }
  }

  private Dictionary<String, Object> create_complete_data(Purchase purchase) {
    Dictionary<String, Object> result = new Hashtable<String, Object>();
    result.put("purchase", purchase);
    return result;
  }

}
