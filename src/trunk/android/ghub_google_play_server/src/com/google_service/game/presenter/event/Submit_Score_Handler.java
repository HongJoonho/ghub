package com.google_service.game.presenter.event;

import java.util.Dictionary;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google_service.game.model.Game_Model;
import com.google_service.game.view.Game_View;

public class Submit_Score_Handler extends Event_Handler {
  private Game_Model model;
  private Game_View view;
  private String leaderboard_id;
  private int score;
  
  public Submit_Score_Handler(Game_Model model, Game_View view) {
    super();
    this.model = model;
    this.view = view;
  }
  
  @Override
  public void dispatch(Event event) {
    Dictionary<String, Object> data = (Dictionary<String, Object>) event.get_data();
    leaderboard_id = (String) data.get("leaderboard_id");
    score = (Integer)data.get("score");
    if (!model.isSignedIn()) {
      view.display_alert("SignedIn is false");
      return;
    }
    view.run_on_ui(new Runnable() {
      public void run() {
        model.getGamesClient().submitScore(leaderboard_id, score);
      }
    });
  }
}
