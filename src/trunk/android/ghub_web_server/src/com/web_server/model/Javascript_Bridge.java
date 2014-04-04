package com.web_server.model;

import java.lang.annotation.Annotation;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

public class Javascript_Bridge {
  
  private Activity activity;
  
  public Javascript_Bridge(Activity activty) {
    this.activity = activty;
  }
  
  @android.webkit.JavascriptInterface
  public void close_window() {
    activity.finish();
  }
}
