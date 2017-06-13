package com.skrymer.test.tobeinstrumented.otherpackage;

/**
 * Created by skrymer on 6/01/17.
 */
public class SomeAwesomeClass {
  public void methodOne(){
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void methodTwo(){
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void methodThree(){
    try {
      Thread.sleep(49);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
