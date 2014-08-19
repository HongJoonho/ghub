package com.google_service.game.view;

import android.app.Activity;
import android.content.Intent;

import com.github.aadt.kernel.mvp.View;
import com.github.aadt.kernel.util.Logger;

public class Game_View extends View {
  private Activity activity;
  
  public Game_View(Activity activity) {
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
