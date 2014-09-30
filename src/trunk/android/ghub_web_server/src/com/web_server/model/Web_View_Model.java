package com.web_server.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.github.aadt.kernel.mvp.Model;
import com.web_server.Web_Activity;

public class Web_View_Model extends Model {
  public Web_View_Model(String path) {
    super(path);
  }
  
  public void start(Activity activity, String url) {
    Intent intent = new Intent(activity, Web_Activity.class);    
    intent.putExtras(create_bundle(url));
    activity.startActivity(intent);  
  }
  
  private Bundle create_bundle(String url) {
    Bundle data = new Bundle();
    data.putString("url", url);
    return data;
  }
}
