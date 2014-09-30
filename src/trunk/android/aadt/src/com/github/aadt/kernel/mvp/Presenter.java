package com.github.aadt.kernel.mvp;

import android.content.Intent;

import com.github.aadt.kernel.actor.Component;

public class Presenter extends Component {
  /**
   *  Presenter 시작 함수 
   */
  public void start() {
  }
  
  /**
   * Presenter 중지 함수
   */
  public void stop() {
  }
  
  /**
   * Activty Result 리턴값을 처리
   */
  public void dispatch_activity_result(int requestCode, int resultCode, Intent data) {
    
  }
}
