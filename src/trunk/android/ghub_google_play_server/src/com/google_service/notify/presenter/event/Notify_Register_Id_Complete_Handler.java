package com.google_service.notify.presenter.event;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google_service.notify.presenter.Notify_Presenter;

public class Notify_Register_Id_Complete_Handler extends Event_Handler {
  private Notify_Presenter presenter;
  
  public Notify_Register_Id_Complete_Handler(Notify_Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void dispatch(Event event) {
    occur_register_id_complete_event(event.get_data());
  }
  
  private void occur_register_id_complete_event(Object event_data) {
    Notify_Presenter_Event game_event = new Notify_Presenter_Event(Notify_Presenter_Event.REGISTER_ID_COMPLETE);
    game_event.set_data(event_data);
    presenter.dispatch_event(game_event);
  }
}
