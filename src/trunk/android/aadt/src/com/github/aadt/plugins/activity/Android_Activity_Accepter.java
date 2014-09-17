package com.github.aadt.plugins.activity;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;

import android.app.NativeActivity;
import android.util.Log;

import com.github.aadt.kernel.actor.Actor;
import com.github.aadt.kernel.event.Direct_Event;
import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Dispatcher;
import com.github.aadt.kernel.event.Event_Handler;
import com.unity3d.player.UnityPlayer;

public class Android_Activity_Accepter extends Actor {
  private String connecter_path = ""; // connect패킷을 요청하면 Connecter의 위치를 알 수 있다.

  public void connect(String connecter_path) {
    this.connecter_path = connecter_path;
  }

  /**
   * Unity 객체에게 받은 이벤트를 처리
   * @param event_data
   */
  public void recv(String event_data) {
    Log.d("FT", "Android_Activity_Accepeter::dispatche_event->" + event_data);
    try {
      JSONObject json_data = (JSONObject)JSONValue.parse(event_data);
      String type = (String)json_data.get("type");
      String name = (String)json_data.get("name");
      Object data = json_data.get("data");
      Event send_event = new Event(name);
      send_event.set_data(data);
      dispatch_event(send_event);
    }
    catch (Exception e) {
      Log.d("FT", e.getMessage());
    }
  }

  /**
   * 연결된 Unity 객체에게 이벤트를 전송 
   * @param send_event
   */
  public void send(Event send_event) {
    Direct_Event direct_event = new Direct_Event();
    direct_event.set_event(send_event);
    Log.d("FT", "connecter path is " + connecter_path);
    UnityPlayer.UnitySendMessage(connecter_path, "recv", direct_event.convert_json());
  }

  public void send(String to_object_path, Event send_event) {
    String event_data = "";
    // Path_Event event = new Path_Event(send_event.get_name());
    // event.set_to_object_path(to_object_path);
    // event.set_data(send_event.get_data());
    // event_data = event.convert_json();
    //
    // 특정 위치의 객체에게 이벤트 전달. Andoird_Activity_Connecter가 이를 받고,
    // 이벤트에 포함된 object_path에게 Signal_Event 형태로 가공하여 전달한다.
    UnityPlayer.UnitySendMessage(connecter_path, "recv", event_data);
  }
  
  public static Android_Activity_Accepter instance = null;
  
  public static Android_Activity_Accepter get_instance() {
    if (null == instance)
      instance = new Android_Activity_Accepter();
    return instance;
  }
}
