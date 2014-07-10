package com.facebook_service.auth.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook_service.auth.model.Auth_Model;
import com.facebook_service.auth.model.events.Auth_Model_Event;
import com.facebook_service.auth.presenter.events.Auth_Presenter_Event;
import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.gdkompanie.gdos.mvp.Presenter;

public class Auth_Presenter extends Presenter {
  private Auth_Model model;
  
  public Auth_Presenter(Auth_Model model) {
    super();
    this.model = model;
    register_events();
  }
  
  private void register_events() {
    add_event_listener(Auth_Presenter_Event.LOGIN, new Event_Handler() {
			@Override
			public void dispatch(Event event) {
			  model.login();
			}
		});
    
    add_event_listener(Auth_Presenter_Event.LOGOUT, new Event_Handler() {
			@Override
			public void dispatch(Event event) {
			  model.logout();
			}
		});
    
    add_event_listener(Auth_Model_Event.SESSION_IS_CLOSED, new Event_Handler() {
			@Override
			public void dispatch(Event event) {
			  Auth_Presenter_Event occur_event = new Auth_Presenter_Event(Auth_Presenter_Event.SESSION_IS_CLOSED);
			  dispatch_event(occur_event);
			}
		});
    
    add_event_listener(Auth_Model_Event.SESSION_IS_OPENED, new Event_Handler() {
			@Override
			public void dispatch(Event event) {
			  Auth_Presenter_Event occur_event = new Auth_Presenter_Event(Auth_Presenter_Event.SESSION_IS_OPENED);
			  dispatch_event(occur_event);
			}
    });
  }
  
  public void start() {
    model.start();
  }
  
  public void stop() {
    model.stop();
  }
  
  public void dispatch_activity_result(int requestCode, int resultCode, Intent data) {
    model.pass_activity_result(requestCode, resultCode, data);
  }
  
  public static Auth_Presenter create(Activity activity, Bundle saved_instance_state) {
    Auth_Model model = new Auth_Model(activity, saved_instance_state);
    Auth_Presenter result = new Auth_Presenter(model);
    return result;
  }

}
