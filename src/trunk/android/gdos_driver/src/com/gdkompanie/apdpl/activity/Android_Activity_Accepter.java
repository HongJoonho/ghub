package com.gdkompanie.apdpl.activity;

import android.app.Activity;

import com.gdkompanie.gdos.event.Event;
import com.unity3d.player.UnityPlayer;

public class Android_Activity_Accepter extends Activity {

	private String connecter_path = ""; // connect패킷을 요청하면 Connecter의 위치를 알 수 있다.
	
	public void dispatch_event(String connecter_event_data) {
		// Kernel을 통해 원하는 path의 actor에게 Event객체 형태로 변환하여 전달
		/*
		 *  {
		 *  "type":"activity_event",
		 *  "to_actor_path":"/presenters/auth",
		 *  "data": {}
		 *  }
		 */
	}
	
	public void send_event(Event send_event) {
		String event_data = "";
		// Direct_Event event = new Direct_Event(send_event.get_name());
		// event.set_to_object_path(to_object_path);
		// event.set_data(send_event.get_data());
		// event_data = event.convert_json();
		// 유니티쪽에 Andoird_Activity_Connecter는 dispatch_event 함수를 가지고 있다.
		UnityPlayer.UnitySendMessage(connecter_path, "dispatch_event", event_data);
	}
	
	public void send_event(String to_object_path, Event send_event) {
		String event_data = "";
		// Path_Event event = new Path_Event(send_event.get_name());
		// event.set_to_object_path(to_object_path);
		// event.set_data(send_event.get_data());
		// event_data = event.convert_json();
		//
		// 특정 위치의 객체에게 이벤트 전달. Andoird_Activity_Connecter가 이를 받고,
		// 이벤트에 포함된 object_path에게 Signal_Event 형태로 가공하여 전달한다.
		UnityPlayer.UnitySendMessage(connecter_path, "dispatch_event", event_data);
	}
}
