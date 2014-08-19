package com.google_service.game.presenter.event;

import com.github.aadt.kernel.event.Event;
import com.github.aadt.kernel.event.Event_Handler;
import com.google_service.game.presenter.Game_Presenter;

public class Game_Sign_In_Failed_Handler extends Event_Handler {
  private Game_Presenter presenter;
  
  public Game_Sign_In_Failed_Handler(Game_Presenter presenter) {
    super();
    this.presenter = presenter;
  }
  
  @Override
  public void dispatch(Event event) {
    Game_Presenter_Event game_event = new Game_Presenter_Event(Game_Presenter_Event.SIGN_IN_FAILED);
    presenter.dispatch_event(game_event);
  }

}
