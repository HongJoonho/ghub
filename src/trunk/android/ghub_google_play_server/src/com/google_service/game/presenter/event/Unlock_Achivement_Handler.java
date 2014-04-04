package com.google_service.game.presenter.event;

import java.util.Dictionary;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.gdkompanie.gdos.util.Logger;
import com.google_service.billing.util.IabResult;
import com.google_service.game.model.Game_Model;
import com.google_service.game.view.Game_View;

public class Unlock_Achivement_Handler extends Event_Handler {
  private Game_Model model;
  private Game_View view;
  
  public Unlock_Achivement_Handler(Game_Model model, Game_View view) {
    super();
    this.model = model;
    this.view = view;
  }
  
  @Override
  public void dispatch(Event event) {
    Dictionary<String, Object> data = (Dictionary<String, Object>) event.get_data();
    String achivement_id = (String) data.get("achivement_id");
    if (!model.isSignedIn()) {
      view.display_alert("SignedIn is false");
      return;
    }
    model.getGamesClient().unlockAchievement(achivement_id);
  }

}
