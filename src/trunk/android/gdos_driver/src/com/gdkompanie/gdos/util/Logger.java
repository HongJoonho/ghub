package com.gdkompanie.gdos.util;

import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.DialogInterface;

public class Logger {
  static final String TAG = "gdos_driver";
  static AlertDialog.Builder bld;

  public static void debug(String message) {
    Log.d(TAG, message);
  }

  public static void complain(Activity activity, String message) {
    Log.e(TAG, "**** App Error: " + message);
    alert(activity, "Error: " + message);
  }

  public static void alert(Activity activity, String message) {
    bld = new AlertDialog.Builder(activity);
    bld.setMessage(message);
    bld.setNeutralButton("OK", null);
    bld.setCancelable(false);
    Log.d(TAG, "Showing alert dialog: " + message);
    
    activity.runOnUiThread(new Runnable() {
      public void run() {
        bld.create().show();
      }
    });
  }
  
  public static void alert_with_listener(Activity activity, String message, DialogInterface.OnClickListener on_click_listener) {
    bld = new AlertDialog.Builder(activity);
    bld.setMessage(message);
    bld.setNeutralButton("OK", on_click_listener);
    bld.setCancelable(false);
    Log.d(TAG, "Showing alert dialog: " + message);
    
    activity.runOnUiThread(new Runnable() {
      public void run() {
        bld.create().show();
      }
    });
  }
}
