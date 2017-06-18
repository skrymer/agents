package com.skrymer.profiler;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

/**
 * Created by skrymer on 4/06/17.
 */
public class AgentListener implements AgentBuilder.Listener {
  @Override
  public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {}

  @Override
  public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {}

  @Override
  public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
    System.out.println("!!!! DOH - ERROR WHILE INJECTING TRACING !!!!");
    System.out.println("Error: " + throwable);
  }

  @Override
  public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {}
}