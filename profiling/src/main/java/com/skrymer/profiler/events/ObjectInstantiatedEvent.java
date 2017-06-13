package com.skrymer.profiler.events;

/**
 * Created by skrymer on 8/01/17.
 */
public class ObjectInstantiatedEvent extends Event {
  private String className;
  private int newCount;

  public ObjectInstantiatedEvent(String className, int newCount){
    this.className = className;
    this.newCount = newCount;
  }

  public String getClassName() {
    return className;
  }

  public int getNewCount() {
    return newCount;
  }

  @Override
  public String toString() {
    return "Class " + className;
  }

  @Override
  public String getDescription() {
    return "Received event: " + this.getClassName() + " was instantiated" + "\n";
  }
}
