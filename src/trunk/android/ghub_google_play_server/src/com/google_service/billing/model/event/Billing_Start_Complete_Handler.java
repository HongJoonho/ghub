package com.google_service.billing.model.event;

import android.app.Activity;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.gdkompanie.gdos.util.Logger;
import com.google_service.billing.util.IabResult;

import com.google_service.billing.model.Billing_Model;

public class Billing_Start_Complete_Handler extends Event_Handler {
  static final String TAG = "google_service";

  private Billing_Model model;
  private Activity activity;

  public Billing_Start_Complete_Handler(Activity activity, Billing_Model model) {
    this.activity = activity;
    this.model = model;
  }

  @Override
  public void dispatch(Event event) {
    Billing_Model_Event model_event = (Billing_Model_Event) event;
    IabResult result = (IabResult) model_event.get_data();
    Logger.debug("Setup finished.");
    if (!result.isSuccess()) {
      // Oh noes, there was a problem.
      Logger.complain(activity, "Problem setting up in-app billing: " + result);
      return;
    }
  }
}
