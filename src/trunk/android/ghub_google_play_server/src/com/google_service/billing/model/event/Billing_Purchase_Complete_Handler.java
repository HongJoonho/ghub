package com.google_service.billing.model.event;

import java.util.Dictionary;
import android.app.Activity;
import android.content.res.Resources;

import com.google_service.R;
import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.gdkompanie.gdos.util.Logger;
import com.google_service.billing.util.IabHelper;
import com.google_service.billing.util.IabResult;
import com.google_service.billing.util.Purchase;
import com.google_service.billing.model.Billing_Model;
import com.google_service.billing.presenter.event.Billing_Presenter_Event;

public class Billing_Purchase_Complete_Handler extends Event_Handler {
  private Activity activity;
  private Billing_Model model;

  public Billing_Purchase_Complete_Handler(Activity activity, Billing_Model model) {
    this.activity = activity;
    this.model = model;
  }

  @Override
  public void dispatch(Event event) {
    Dictionary<String, Object> data = (Dictionary<String, Object>) event.get_data();
    IabResult result = (IabResult) data.get("result");
    Purchase purchase = null;
    if (null != data.get("purchase"))
      purchase = (Purchase) data.get("purchase");
    Logger.debug("Purchase finished: " + result + ", purchase: " + purchase);
    if (result.isFailure()) {
      return;
    }
    Logger.debug("Purchase successful.");
  }

}
