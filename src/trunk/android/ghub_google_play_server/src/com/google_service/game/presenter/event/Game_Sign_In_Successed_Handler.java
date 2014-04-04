package com.google_service.game.presenter.event;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google_service.game.presenter.Game_Presenter;

public class Game_Sign_In_Successed_Handler extends Event_Handler {
  private Game_Presenter presenter;
  
  public Game_Sign_In_Successed_Handler(Game_Presenter presenter) {
    super();
    this.presenter = presenter;
  }
  
  @Override
  public void dispatch(Event event) {
    Game_Presenter_Event game_event = new Game_Presenter_Event(Game_Presenter_Event.SIGN_IN_SUCCESSED);
    presenter.dispatch_event(game_event);
  }

}
