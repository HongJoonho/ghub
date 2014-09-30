package com.facebook_service.auth.presenter;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.facebook_service.auth.model.Auth_Model;
import com.facebook_service.auth.model.events.Auth_Model_Event;
import com.facebook_service.auth.presenter.events.Auth_Presenter_Event;
import com.github.aadt.kernel.core.Kernel;
import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Handler;
import com.github.aadt.kernel.util.Logger;
import com.github.aadt.kernel.actor.*;
import com.github.aadt.kernel.actor.errors.Actor_Error;

public class Auth_Presenter extends Component {
	private Auth_Model model;

	public void start(Auth_Model model) {
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
	  try {
	    Actor model_actor = Kernel.get_instance().new_object("/sys/models/auth");
	    Auth_Model model = model_actor.add_component(Auth_Model.class);
	    model.start(activity, saved_instance_state);
	    Actor presenter_actor = Kernel.get_instance().new_object("/sys/presenters/auth");
	    Auth_Presenter result = presenter_actor.add_component(Auth_Presenter.class);
	    result.start(model);
	    return result;
	  }
	  catch (Actor_Error e) {
	    Logger.debug(e.getMessage());
	  }
	  // @TODO FIX
	  return null;
	}

}
