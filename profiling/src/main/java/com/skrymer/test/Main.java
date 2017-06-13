package com.skrymer.test;

import com.skrymer.test.tobeinstrumented.SomeClass;
import com.skrymer.test.tobeinstrumented.SomeOtherClass;
import com.skrymer.test.tobeinstrumented.otherpackage.SomeAwesomeClass;
import com.skrymer.profiler.Profiler;

import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by skrymer on 30/12/16.
 */
public class Main {
  public static void main(String[] args) {
    Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
      SomeClass someClass = new SomeClass("Sonni");
      someClass.methodOne();
      someClass.methodTwo();
      someClass.methodThree();
      someClass.methodOne();

      SomeClass someClass2 = new SomeClass();
      someClass2.methodOne();
      someClass2.methodTwo();
      someClass2.methodThree();
      someClass2.methodOne();

      SomeOtherClass someOtherClass = new SomeOtherClass();
      someOtherClass.methodOne();
      someOtherClass.methodTwo();
      someOtherClass.methodThree();

      SomeAwesomeClass someAwesomeClass = new SomeAwesomeClass();
      someAwesomeClass.methodOne();
      someAwesomeClass.methodTwo();
      someAwesomeClass.methodThree();
    },0, 10, TimeUnit.SECONDS);

  }
}
