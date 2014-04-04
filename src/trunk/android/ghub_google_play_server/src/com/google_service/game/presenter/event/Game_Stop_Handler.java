package com.google_service.game.presenter.event;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google.android.gms.internal.gm;
import com.google_service.game.model.Game_Model;

public class Game_Stop_Handler extends Event_Handler {
  private Game_Model model;
  
  public Game_Stop_Handler(Game_Model model) {
    super();
    this.model = model;
  }
  @Override
  public void dispatch(Event event) {
    // TODO Auto-generated method stub
    model.stop();
  }

}
