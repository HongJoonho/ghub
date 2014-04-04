package com.google_service.billing.presenter.event;

import java.util.Hashtable;
import android.content.Intent;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.gdkompanie.gdos.util.Logger;
import com.google_service.billing.view.Billing_View;
import com.google_service.billing.model.Billing_Model;

public class Billing_Activity_Result_Handler extends Event_Handler {
  private Billing_Model model;
  private Billing_View view;

  public Billing_Activity_Result_Handler(Billing_View view, Billing_Model model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void dispatch(Event event) {
    Hashtable<String, Object> event_data = (Hashtable<String, Object>) event.get_data();
    int request_code = (Integer) event_data.get("request_code");
    int result_code = (Integer) event_data.get("result_code");
    Intent intent_data = null;
    if (event_data.containsKey("intent_data"))
      intent_data = (Intent) event_data.get("intent_data");
    model.activity_result(request_code, result_code, intent_data);
  }

}
