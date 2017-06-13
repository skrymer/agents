package com.skrymer.commonagent;

import com.skrymer.commonagent.test.Bar;
import com.skrymer.commonagent.test.Foo;

/**
 * Main
 */
public class Main {
  public static void main(String[] args) {
    System.out.println("Hello from main");

    Foo foo = new Foo();
    System.out.println("foo.toString(): " + foo.toString());

    Bar bar1 = new Bar("aField", "bField");
    Bar bar2 = new Bar("aField", "bField");
    System.out.println("bar1.toString(): " + bar1.toString());
    System.out.println("bar2.toString(): " + bar2.toString());
    System.out.println("bar1 equals bar2: " + bar1.equals(bar2));
    System.out.println("bar1 hashcode: " + bar1.hashCode());
  }
}
