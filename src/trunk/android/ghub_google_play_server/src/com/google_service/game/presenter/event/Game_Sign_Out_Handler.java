package com.google_service.game.presenter.event;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google_service.game.model.Game_Model;

public class Game_Sign_Out_Handler extends Event_Handler {
  private Game_Model model;
  
  public Game_Sign_Out_Handler(Game_Model model) {
    super();
    this.model = model;
  }
  
  @Override
  public void dispatch(Event event) {
    // TODO Auto-generated method stub
    model.signOut();
  }

}
