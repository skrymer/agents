package com.skrymer.test.somepackage;

/**
 * Class for well testing
 */
public class Foo {

  private String arg;

  public Foo(){
  }

  public Foo(String arg){
    this.arg = arg;
  }

  public void someMethod(){
    int val = 1+1;
  }

  public void oneParam(int param){
    int answer = 42;
  }

  public String someOtherMethod(String s, String ... sa){
    return "";
  }
}
