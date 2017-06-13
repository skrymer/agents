package com.skrymer.test.tobeinstrumented;

/**
 * Created by skrymer on 6/01/17.
 */
public class SomeOtherClass {
  public void methodOne(){
    try {
      Thread.sleep((long) (Math.random() * 100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void methodTwo(){
    try {
      Thread.sleep((long) (Math.random() * 100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void methodThree(){
    try {
      Thread.sleep((long) (Math.random() * 100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
