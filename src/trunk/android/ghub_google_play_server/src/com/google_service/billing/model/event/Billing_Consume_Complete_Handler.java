package com.google_service.billing.model.event;

import java.util.Dictionary;
import android.app.Activity;

import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Handler;
import com.github.aadt.kernel.util.Logger;
import com.google_service.billing.util.IabResult;
import com.google_service.billing.util.Purchase;
import com.google_service.billing.model.Billing_Model;

public class Billing_Consume_Complete_Handler extends Event_Handler {
  private Activity activity;
  private Billing_Model model;

  public Billing_Consume_Complete_Handler(Activity activity, Billing_Model model) {
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
    Logger.debug("Consumption finished. Purchase: " + purchase + ", result: " + result);

    // We know this is the "gas" sku because it's the only one we consume,
    // so we don't check which sku was consumed. If you have more than one
    // sku, you probably should check...
    if (result.isSuccess()) {
      // successfully consumed, so we apply the effects of the item in our
      // game world's logic, which in our case means filling the gas tank a bit
      Logger.debug("Consumption successful. Provisioning.");
    } else {
      Logger.complain(activity, "Error while consuming: " + result);
    }
    Logger.debug("End consumption flow.");
  }

}
