package com.google_service.notify.presenter.event;

import com.github.aadt.kernel.event.Event;

public class Notify_Presenter_Event extends Event {
  // 메시지 서비스 시작 완료
  public static String START_COMPLETE = "start_complete";
  // 구글 메시지 서버에 기기 아이디 등록 완료
  public static String REGISTER_ID_COMPLETE = "register_id_complete";
  
  public Notify_Presenter_Event(String event_name) {
    super(event_name);
  }
}
