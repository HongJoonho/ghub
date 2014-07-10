package com.gdkompanie.gdos.mvp;

import android.content.Intent;

import com.gdkompanie.gdos.actor.Actor;
abstract public class Presenter extends Actor {
  /**
   *  Presenter 시작 함수 
   */
  abstract public void start();
  
  /**
   * Presenter 중지 함수
   */
  abstract public void stop();
  
  /**
   * Activty Result 리턴값을 처리
   */
  abstract public void dispatch_activity_result(int requestCode, int resultCode, Intent data);
}
