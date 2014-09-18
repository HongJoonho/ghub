package com.github.aadt.kernel.actor.accepters;

import java.lang.reflect.Constructor;

import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import android.util.Log;
import com.github.aadt.kernel.actor.Actor;
import com.github.aadt.kernel.actor.Component;
import com.github.aadt.kernel.actor.events.Proxy_Event;
import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.util.Logger;
import com.unity3d.player.UnityPlayer;

public class Android_Accepter extends Component {
  private String connecter_path = ""; // connect패킷을 요청하면 Connecter의 위치를 알 수 있다.
  
  public Android_Accepter() {
    super();
  }

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
     Proxy_Event proxy_event = (Proxy_Event)Event.deserialzie(event_data); 
     Logger.debug(Event.serialize(proxy_event));
     Logger.debug("recv completed");
     dispatch_event(proxy_event);
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
    Proxy_Event proxy_event = new Proxy_Event();
    proxy_event.set_event(send_event);
    Log.d("FT", "connecter path is " + connecter_path);
    UnityPlayer.UnitySendMessage(connecter_path, "recv", Event.serialize(proxy_event));
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
}
