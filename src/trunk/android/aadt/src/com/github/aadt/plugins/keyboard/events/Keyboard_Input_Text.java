package com.github.aadt.plugins.keyboard.events;

import com.github.aadt.kernel.event.Event;

public class Keyboard_Input_Text extends Event {
  public static final String RECV_INPUT_TEXT = "recv_input_text";
  
  public String text = "";
  
  public Keyboard_Input_Text(String text) {
    super(RECV_INPUT_TEXT);
    this.text = text;
  }
}
