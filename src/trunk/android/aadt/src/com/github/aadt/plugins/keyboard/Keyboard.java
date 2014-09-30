package com.github.aadt.plugins.keyboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.View.*;
import android.view.inputmethod.*;

import com.github.aadt.kernel.actor.Actor;
import com.github.aadt.kernel.actor.Component;
import com.github.aadt.kernel.event.Event;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView.*;

import com.github.aadt.kernel.util.*;
import com.github.aadt.plugins.keyboard.events.Keyboard_Event;
import com.github.aadt.plugins.keyboard.events.Keyboard_Input_Text;
import com.github.aadt.plugins.keyboard.events.Keyboard_Resize;

import android.view.*;

import com.unity3d.player.UnityPlayer;
import android.widget.*;
import com.github.aadt.R;
import android.text.*;
import android.text.method.KeyListener;

public class Keyboard extends Component {
  private Activity activity;
  private Dialog dialog = null;
  private int layout_height = 0;

  public void start(Activity activity) {
    this.activity = activity;
  }

  public void show() {
    Logger.debug("Keyboard::show");
    add_xml_layout();
  }

  private void add_xml_layout() {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.main_layout);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setOnShowListener(new Dialog.OnShowListener() {
          @Override
          public void onShow(DialogInterface dialog) {
            Logger.debug("DIALOG SHOW");
            occur_show_event();
          }
        });
        dialog.show();

        final Custom_Edit_Text edit = (Custom_Edit_Text) dialog.findViewById(R.id.customEditText1);
        edit.setOnEditTextImeBackListener(new Custom_Edit_Text.EditTextImeBackListener() {
          @Override
          public void onImeBack(Custom_Edit_Text ctrl, String text) {
            Logger.debug("IME BACK. " + text);
            hide();
          }
        });

        final Button send_button = (Button)dialog.findViewById(R.id.button1);
        send_button.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            String text = edit.getText().toString();
            occur_input_text_event(text);
            edit.setText("");
          }
        });

        final RelativeLayout relative_menu = (RelativeLayout) dialog.findViewById(R.id.layout_h);
        relative_menu.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
              @Override
              public void onGlobalLayout() {
                int height = relative_menu.getHeight();
                Logger.debug("layout height " + height);
                if (layout_height != height) {
                  layout_height = height;
                  occur_resize_event(height);
                }
              }
            });
        relative_menu.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            Logger.debug("layout clicked");
            hide();
          }
        });
      }
    });
  }

  public void hide() {
    if (null != dialog)
      dialog.dismiss();
    occur_hide_event();
  }

  private void occur_show_event() {
    Event event = new Keyboard_Event(Keyboard_Event.SHOW);
    dispatch_event(event);
  }

  private void occur_resize_event(int layout_height) {
    Event event = new Keyboard_Resize(layout_height);
    dispatch_event(event);
  }

  private void occur_hide_event() {
    Event event = new Keyboard_Event(Keyboard_Event.HIDE);
    dispatch_event(event);
  }
  
  private void occur_input_text_event(String text) {
    Event event = new Keyboard_Input_Text(text);
    dispatch_event(event);
  }
}
