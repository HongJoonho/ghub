package com.google_service.game.presenter.event;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google_service.game.model.Game_Model;
import com.google_service.game.presenter.Game_Presenter;

public class Game_Start_Handler extends Event_Handler {
  private Game_Model model;
  private Game_Presenter presenter;
  
  public Game_Start_Handler(Game_Model model, Game_Presenter presenter) {
    super();
    this.model = model;
    this.presenter = presenter;
  }
  
  @Override
  public void dispatch(Event event) {
    // TODO Auto-generated method stub
    model.start();
  }

}
