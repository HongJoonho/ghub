
package com.google_service.game.presenter.event;

import java.util.Dictionary;

import com.gdkompanie.gdos.event.Event;
import com.gdkompanie.gdos.event.Event_Handler;
import com.google_service.game.model.Game_Model;
import com.google_service.game.view.Game_View;

public class Enter_Leaderboard_Handler extends Event_Handler {
  // request codes we use when invoking an external activity
  final int RC_RESOLVE = 5000, RC_UNUSED = 5001;
  
  private Game_Model model;
  private Game_View view;
  
  public Enter_Leaderboard_Handler(Game_Model model, Game_View view) {
    super();  
    this.model = model;
    this.view = view;
  }
  
  @Override
  public void dispatch(Event event) {
    if (!model.isSignedIn()) {
      model.beginUserInitiatedSignIn();
      return;
    }
    Dictionary<String, Object> data = (Dictionary<String, Object>) event.get_data();
    String leaderboard_id = (String) data.get("leaderboard_id");
    view.start_activity(model.getGamesClient().getLeaderboardIntent(leaderboard_id), RC_UNUSED);
  }

}
