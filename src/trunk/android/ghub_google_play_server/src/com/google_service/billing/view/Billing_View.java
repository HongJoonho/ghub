package com.google_service.billing.view;

import com.github.aadt.kernel.mvp.View;
import com.github.aadt.kernel.util.Logger;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;

public class Billing_View extends View {
  private Activity activity;

  public Billing_View(Activity activity) {
    this.activity = activity;
  }

  public void display_alert(String message) {
    Logger.alert(activity, message);
  }
  
  public void display_alert_with_listener(String message, DialogInterface.OnClickListener on_click_listener) {
    Logger.alert_with_listener(activity, message, on_click_listener);
  }
  
  public Resources get_resources() {
    return activity.getResources();
  }
  
  public void close() {
    activity.finish();
  }
}
