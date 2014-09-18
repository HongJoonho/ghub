package com.github.aadt.kernel.actor;

public class Null_Actor extends Actor {
  public Null_Actor() {
    super("/null");
  }
  
  public boolean is_null() {
    return true;
  }
}
