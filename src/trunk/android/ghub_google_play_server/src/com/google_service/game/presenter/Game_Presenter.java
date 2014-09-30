package com.google_service.game.presenter;

import java.util.Dictionary;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Intent;

import com.github.aadt.kernel.mvp.Presenter;
import com.google_service.game.presenter.event.Enter_Achivement_Handler;
import com.google_service.game.presenter.event.Enter_Leaderboard_Handler;
import com.google_service.game.presenter.event.Game_Presenter_Event;
import com.google_service.game.presenter.event.Game_Sign_In_Failed_Handler;
import com.google_service.game.presenter.event.Game_Sign_In_Handler;
import com.google_service.game.presenter.event.Game_Sign_In_Successed_Handler;
import com.google_service.game.presenter.event.Game_Sign_Out_Handler;
import com.google_service.game.presenter.event.Game_Stop_Handler;
import com.google_service.game.presenter.event.Submit_Score_Handler;
import com.google_service.game.presenter.event.Unlock_Achivement_Handler;
import com.google_service.game.model.Game_Model;
import com.google_service.game.model.event.Game_Model_Event;
import com.google_service.game.presenter.event.Game_Start_Handler;
import com.google_service.game.view.Game_View;

public class Game_Presenter extends Presenter {  
  private Game_View view;
  private Game_Model model;
  
  public Game_Presenter(Game_View view, Game_Model model) {
    this.view = view;
    this.model = model;
    register_events();
  }
  
  private void register_events() {
    add_event_listener(Game_Presenter_Event.START, new Game_Start_Handler(model, this));
    add_event_listener(Game_Presenter_Event.STOP, new Game_Stop_Handler(model));
    add_event_listener(Game_Presenter_Event.SIGN_IN, new Game_Sign_In_Handler(model));
    add_event_listener(Game_Presenter_Event.SIGN_OUT, new Game_Sign_Out_Handler(model));
    add_event_listener(Game_Presenter_Event.UNLOCK_ACHIVEMENT, new Unlock_Achivement_Handler(model, view));
    add_event_listener(Game_Presenter_Event.SUBMIT_SCORE, new Submit_Score_Handler(model, view));
    add_event_listener(Game_Presenter_Event.ENTER_ACHIVEMENT, new Enter_Achivement_Handler(model, view));
    add_event_listener(Game_Presenter_Event.ENTER_LEADERBOARD, new Enter_Leaderboard_Handler(model, view));
    
    model.add_event_listener(Game_Model_Event.SIGN_IN_SUCCESSED, new Game_Sign_In_Successed_Handler(this));
    model.add_event_listener(Game_Model_Event.SIGN_IN_FAILED, new Game_Sign_In_Failed_Handler(this));
  }
  
  public void start() { 
    Game_Presenter_Event event = new Game_Presenter_Event(Game_Presenter_Event.START);
    dispatch_event(event);
  }
  
  public void stop() {
    Game_Presenter_Event event = new Game_Presenter_Event(Game_Presenter_Event.STOP);
    dispatch_event(event);
  }
  
  public boolean is_signed() {
    return model.isSignedIn();
  }
  
  public void sign_in() {
    Game_Presenter_Event event = new Game_Presenter_Event(Game_Presenter_Event.SIGN_IN);
    dispatch_event(event);
  }
  
  public void sign_out() {
    Game_Presenter_Event event = new Game_Presenter_Event(Game_Presenter_Event.SIGN_OUT);
    dispatch_event(event);
  }
  
  public void unlock_achivemnt(String achivement_id) {
    Game_Presenter_Event event = new Game_Presenter_Event(Game_Presenter_Event.UNLOCK_ACHIVEMENT);
    event.set_data(create_unlock_achivement_event_data(achivement_id));
    dispatch_event(event);
  }

  private Dictionary<String, Object> create_unlock_achivement_event_data(String achivement_id) {
    Dictionary<String, Object> result = new Hashtable<String, Object>();
    result.put("achivement_id", achivement_id);
    return result;
  }
  
  public void submit_score(String leaderboard_id, int score) {
    Game_Presenter_Event event = new Game_Presenter_Event(Game_Presenter_Event.SUBMIT_SCORE);
    event.set_data(create_submit_score_event_data(leaderboard_id, score));
    dispatch_event(event);
  }
  
  private Dictionary<String, Object> create_submit_score_event_data(String leaderboard_id, int score) {
    Dictionary<String, Object> result = new Hashtable<String, Object>();
    result.put("leaderboard_id", leaderboard_id);
    result.put("score", score);
    return result;
  }
  
  public void enter_achivement() {
    Game_Presenter_Event event = new Game_Presenter_Event(Game_Presenter_Event.ENTER_ACHIVEMENT);
    dispatch_event(event);
  }
  
  public void enter_leaderboard(String leaderboard_id) {
    Game_Presenter_Event event = new Game_Presenter_Event(Game_Presenter_Event.ENTER_LEADERBOARD);
    event.set_data(create_enter_leaderboard_event_data(leaderboard_id));
    dispatch_event(event);
  }
  
  private Dictionary<String, Object> create_enter_leaderboard_event_data(String leaderboard_id) {
    Dictionary<String, Object> result = new Hashtable<String, Object>();
    result.put("leaderboard_id", leaderboard_id);
    return result;
  }
  
  public void on_activity_result(int request_code, int result_code, Intent data) {
    model.on_activity_result(request_code, result_code, data);
  }
}
