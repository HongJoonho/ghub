package com.github.aadt.plugins.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View.*;
import android.view.inputmethod.*;

import com.github.aadt.kernel.actor.Actor;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView.*;

import com.github.aadt.kernel.util.*;
import android.view.*;

import com.unity3d.player.UnityPlayer;
import android.widget.*;
import com.github.aadt.R;
import android.text.*;

public class Keyboard extends Actor {
  private Activity activity;
  private EditText text;
  private UnityPlayer unity_player;

  public Keyboard(Activity arg_activity, UnityPlayer unit_player) {
    this.activity = arg_activity;
    this.unity_player = unit_player;
  }

  public void start() {
    RelativeLayout.LayoutParams localLayoutParams;
    localLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    localLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    text = new EditText(activity);
    // text.setLayoutParams(localLayoutParams);
    text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    // text.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    /*
     * text.setInputType(InputType.TYPE_NULL); text.setOnFocusChangeListener(new
     * OnFocusChangeListener() {
     * 
     * @Override public void onFocusChange(View arg0, boolean arg1) { Logger.debug("FOCUS CHANGE");
     * InputMethodManager inputMgr = (InputMethodManager) activity
     * .getSystemService(Context.INPUT_METHOD_SERVICE);
     * text.setInputType(InputType.TYPE_CLASS_TEXT); text.requestFocus();
     * inputMgr.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT); } });
     */
    /*
     * text.setOnEditorActionListener(new OnEditorActionListener() {
     * 
     * @Override public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) { if (arg1 ==
     * EditorInfo.IME_ACTION_DONE) { } return false; } }); text.addTextChangedListener(new
     * TextWatcher() {
     * 
     * @Override public void onTextChanged(CharSequence s, int start, int before, int count) { //
     * TODO Auto-generated method stub Logger.debug("onTextChanged"); }
     * 
     * @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { //
     * TODO Auto-generated method stub Logger.debug("beforeTextChanged");
     * 
     * }
     * 
     * @Override public void afterTextChanged(Editable s) { // TODO Auto-generated method stub
     * Logger.debug("afterTextChanged"); } });
     * 
     * view.getViewTreeObserver().addOnGlobalLayoutListener( new
     * ViewTreeObserver.OnGlobalLayoutListener() {
     * 
     * @Override public void onGlobalLayout() { // TODO Auto-generated method stub int h1 = int h1 =
     * view.getRootView().getHeight(); int h2 = view.getHeight(); Logger.debug("height1 is " + h1);
     * Logger.debug("height2 is " + h2); } });
     */
    // add_linear_layout();
  }

  public void show() {
    Logger.debug("Keyboard::show");
    add_relative_layout();
  }

  private void add_relative_layout() {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        RelativeLayout layout = new RelativeLayout(UnityPlayer.currentActivity
            .getApplicationContext());
        RelativeLayout.LayoutParams params;
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        //layout.setBackgroundColor(Color.BLUE);

        RelativeLayout localRelativeLayout = new RelativeLayout(UnityPlayer.currentActivity
            .getApplicationContext());
        // localRelativeLayout.setGravity(Gravity.BOTTOM);
        RelativeLayout.LayoutParams localLayoutParams;
        localLayoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        // localLayoutParams.height = 1000;
        //localLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        localLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        localRelativeLayout.setLayoutParams(localLayoutParams);
        localRelativeLayout.setBackgroundColor(Color.RED);
        localRelativeLayout.addView(text);
        
        layout.addView(localRelativeLayout);

        UnityPlayer.currentActivity.addContentView(layout, params);
      }
    });
  }

  private void add_linear_layout() {
    UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        LinearLayout localLinearLayout = new LinearLayout(UnityPlayer.currentActivity
            .getApplicationContext());
        localLinearLayout.setOrientation(LinearLayout.VERTICAL);
        localLinearLayout.setGravity(Gravity.BOTTOM);
        LinearLayout.LayoutParams localLayoutParams;
        localLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
        // localLinearLayout.setBackgroundColor(Color.RED);
        localLinearLayout.addView(text);
        UnityPlayer.currentActivity.addContentView(localLinearLayout, localLayoutParams);
      }
    });
  }

  public void hide() {
  }
}
