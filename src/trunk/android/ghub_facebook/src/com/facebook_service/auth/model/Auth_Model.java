package com.facebook_service.auth.model;

import java.util.Dictionary;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;
import android.widget.SeekBar;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook_service.auth.model.events.Auth_Model_Event;
import com.github.aadt.kernel.mvp.Model;

/**
 * @class Auth_Model
 * 
 * @brief  페이스북 세션을 관리하는 객체
 * @author Lee Hyeon-gi
 */
public class Auth_Model extends Model {
  private Activity activity;
  private Bundle saved_instance_state;
  private Session.StatusCallback status_callback = new SessionStatusCallback();
  private Session session;
  private Boolean session_is_open = false;

  private class SessionStatusCallback implements Session.StatusCallback {
    @Override
    public void call(Session session, SessionState state, Exception exception) {
      Log.d("FT", "SeesionStatusCallback");
      update_status();
    }
  }

  public Auth_Model(Activity activity, Bundle saved_instance_state) {
    this.activity = activity;
    this.saved_instance_state = saved_instance_state;
    Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
    create_or_reset_session();
  }

  private void create_or_reset_session() {
    Session session = Session.getActiveSession();
    if (session == null) {
      if (saved_instance_state != null) {
        session = Session.restoreSession(activity, null, status_callback, saved_instance_state);
      }
      if (session == null) {
        session = new Session(activity);
      }
      Session.setActiveSession(session); 
    }
    this.session = Session.getActiveSession();
    session_is_open = session.isOpened();
  }
 
  /**
   * Auth 객체 시작  
   */
  public void start() {
    Session.getActiveSession().addCallback(status_callback);
  }

  /**
   * Auth 객체 종료
   */
  public void stop() {
    Session.getActiveSession().removeCallback(status_callback);
  }

  private void update_status() {
    session = Session.getActiveSession();
    if (session.isOpened()) {
      Log.d("FT", "session is opened: token:" + session.getAccessToken());
      if (false == session_is_open) {
        session_is_open = true;
        occur_session_opened_event();
      }
    }
    else {
      Log.d("FT", "session is not opened: token:" + session.getAccessToken());
      if (true == session_is_open) {
        session_is_open = false;
        occur_session_closed_event();
      }
    }
  }
  
  private void occur_session_opened_event() {
    Auth_Model_Event event = new Auth_Model_Event(Auth_Model_Event.SESSION_IS_OPENED);
    Dictionary<String, Object> data = new Hashtable<String, Object>();
    data.put("access_token", session.getAccessToken());
    event.set_data(data);
    dispatch_event(event);
    Log.d("FT", "session is opened event");
  }
  
  private void occur_session_closed_event() {
    Auth_Model_Event event = new Auth_Model_Event(Auth_Model_Event.SESSION_IS_CLOSED);
    Dictionary<String, Object> data = new Hashtable<String, Object>();
    data.put("access_token", session.getAccessToken());
    event.set_data(data);
    dispatch_event(event);
    Log.d("FT", "session is closed event");
  }

  /**
   * 페이스북 계정 로그인을 요청한다. 로그인 토큰이 있다면 세션을 읽고 토큰이 없다면 
   * 이미 설치된 페이스북 앱이나 웹뷰를 띄워서 페이스북 로그인을 요청한다.
   */
  public void login() {
    if (session.isClosed()) {
      Log.d("FT", "Session is closed");
      create_or_reset_session();
    }
    if (session.isOpened()) {
      Log.d("FT", "Already session is logined");
      return;
    }
    if (has_token())
      read_session();
    else
      open_session();
  }
  
  public void logout() {
    if (!session.isClosed()) {
      session.closeAndClearTokenInformation();
      session_is_open = false;
    }
  }

  private void read_session() {
    session.openForRead(new Session.OpenRequest(activity).setCallback(status_callback));
  }

  private void open_session() {
    if (!session.isOpened() && !session.isClosed()) {
      session.openForRead(new Session.OpenRequest(activity).setCallback(status_callback));
    } else {
      Session.openActiveSession(activity, true, status_callback);
    }
  }

  private Boolean has_token() {
    Session session = Session.getActiveSession();
    return session.getState().equals(SessionState.CREATED_TOKEN_LOADED);
  } 

  public void pass_activity_result(int requestCode, int resultCode, Intent data) {
    Session.getActiveSession().onActivityResult(activity, requestCode, resultCode, data);
  }

}
