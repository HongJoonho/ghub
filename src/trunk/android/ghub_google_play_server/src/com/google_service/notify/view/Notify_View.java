package com.google_service.notify.view;

import android.app.Activity;
import android.content.Intent;

import com.github.aadt.kernel.mvp.View;
import com.github.aadt.kernel.util.Logger;

public class Notify_View extends View {
  private Activity activity;
  
  public Notify_View(Activity activity) {
    super();
    this.activity = activity;
  }
  
  public void display_alert(String message) {
    Logger.alert(activity, message);
  }
  
  public void start_activity(Intent intent, int request_code) {
    activity.startActivityForResult(intent, request_code);
  }
  
  public void run_on_ui(Runnable action) {
    activity.runOnUiThread(action);
  }
}
