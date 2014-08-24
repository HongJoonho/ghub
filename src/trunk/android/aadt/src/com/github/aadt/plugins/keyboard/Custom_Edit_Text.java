package com.github.aadt.plugins.keyboard;

import android.view.KeyEvent;
import android.view.inputmethod.*;
import android.widget.EditText;
import android.content.Context;
import android.util.AttributeSet;

public class Custom_Edit_Text extends EditText {
  private EditTextImeBackListener mOnImeBack;

  public Custom_Edit_Text(Context context) {
    super(context);
  }

  public Custom_Edit_Text(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public Custom_Edit_Text(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public boolean onKeyPreIme(int keyCode, KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
      if (mOnImeBack != null) {
        mOnImeBack.onImeBack(this, this.getText().toString());
      }
    }
    return super.onKeyPreIme(keyCode, event);
  }

  public void setOnEditTextImeBackListener(EditTextImeBackListener listener) {
    mOnImeBack = listener;
  }

  public interface EditTextImeBackListener {
    public abstract void onImeBack(Custom_Edit_Text ctrl, String text);
  }
}
