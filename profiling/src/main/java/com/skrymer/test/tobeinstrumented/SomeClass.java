package com.skrymer.test.tobeinstrumented;

public class SomeClass {

  public SomeClass(){

  }

  public SomeClass(String someString){
    this();
  }

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
