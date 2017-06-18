package com.skrymer.test;

import com.skrymer.traceagent.Tracer;
import com.skrymer.test.somepackage.Bar;
import com.skrymer.test.somepackage.Foo;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main for testing
 */
public class Main {

  public static void main(String[] args) {
    System.out.println("Hello from main!");

    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
      System.out.println("Hello from thread");

      Foo foo = new Foo("Test test");
      foo.someMethod();
      foo.someOtherMethod("Sonni", "Brian", "Nielsen");

      new Bar().doSomething("other test class");
      System.out.println("Print traced classes:");
      Tracer.getTracer().getTraceInfoMap().values()
          .stream()
          .forEach(System.out::println);

    },1, 10, TimeUnit.SECONDS);
  }
}
