package com.github.aadt.plugins.keyboard;

import android.app.Activity;
import android.content.Context;
import android.view.View.*;
import android.view.inputmethod.*;

import com.github.aadt.kernel.actor.Actor;
import android.widget.EditText;
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

  public Keyboard(Activity arg_activity) {
    this.activity = arg_activity;
  }

  public void start() {
    RelativeLayout.LayoutParams localLayoutParams;
    localLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
    localLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    text = new EditText(activity);
    text.setLayoutParams(localLayoutParams);
    // text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    // text.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    text.setInputType(InputType.TYPE_NULL);
    text.setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View arg0, boolean arg1) {
        Logger.debug("FOCUS CHANGE");
        InputMethodManager inputMgr = (InputMethodManager) activity
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        /*
         * inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED,
         * InputMethodManager.HIDE_IMPLICIT_ONLY);
         */
        text.setInputType(InputType.TYPE_CLASS_TEXT);
        text.requestFocus();
        inputMgr.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT);
      }
    });
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
    register_text_view();
  }

  public void show() {
    Logger.debug("Keyboard::show");
  }

  private void register_text_view() {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        RelativeLayout localRelativeLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams localLayoutParams;
        localLayoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        localLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        localRelativeLayout.setLayoutParams(localLayoutParams);
        localRelativeLayout.addView(text);

        // activity.getWindow().addContentView(localRelativeLayout, localLayoutParams);
        activity.addContentView(localRelativeLayout, localLayoutParams);
        // activity.addContentView(text, localLayoutParams);
      }
    });
  }

  public void hide() {
  }
}
