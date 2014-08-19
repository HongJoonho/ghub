package com.google_service.game.presenter.event;

import com.github.aadt.kernel.event.Event;

public class Game_Presenter_Event extends Event {
  public static final String START = "start";
  public static final String STOP = "stop";
  public static final String SIGN_IN = "sign_in";
  public static final String SIGN_IN_SUCCESSED = "sign_in_successed";
  public static final String SIGN_IN_FAILED = "sign_in_failed";
  public static final String SIGN_OUT = "sign_out";
  public static final String UNLOCK_ACHIVEMENT = "unlock_achivement";
  public static final String SUBMIT_SCORE = "submit_score";

  public static final String ENTER_ACHIVEMENT = "enter_achivement";
  public static final String ENTER_LEADERBOARD = "enter_leaderboard";

  public Game_Presenter_Event(String event_name) {
    super(event_name);
  }
}
