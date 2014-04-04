package com.google_service.notify.presenter;

import com.gdkompanie.gdos.mvp.Presenter;
import com.google_service.notify.model.Notify_Model;
import com.google_service.notify.model.event.Notify_Model_Event;
import com.google_service.notify.presenter.event.Notify_Register_Id_Complete_Handler;
import com.google_service.notify.presenter.event.Notify_Start_Complete_Handler;

public class Notify_Presenter extends Presenter {
  private Notify_Model model;
  
  public Notify_Presenter(Notify_Model model) {
    this.model = model;
  }
  
  public void start(String sender_id) {
    model.add_event_listener(Notify_Model_Event.START_COMPLETE, new Notify_Start_Complete_Handler(this));
    model.add_event_listener(Notify_Model_Event.REGISTER_ID_COMPLETE, new Notify_Register_Id_Complete_Handler(this));
    model.start(sender_id);
  }
}
