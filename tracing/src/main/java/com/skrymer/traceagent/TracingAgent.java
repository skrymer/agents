package com.skrymer.traceagent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.nameContainsIgnoreCase;

public class TracingAgent {

  /**
   * Java agent entry point were instrumentation can be added
   *
   * @param agentArgument
   * @param instrumentation
   */
  public static void premain(String agentArgument, Instrumentation instrumentation){
    new AgentBuilder.Default()
        .with(new AgentListener())
        .type(nameContainsIgnoreCase(getPackageToBeTraced()))
        .transform(new AgentBuilder.Transformer() {
          @Override
          public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                  TypeDescription typeDescription,
                                                  ClassLoader classLoader,
                                                  JavaModule javaModule) {
            return builder
                .constructor(any())
                .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.to(Interceptor.class)))
                .method(any())
                .intercept(MethodDelegation.to(Interceptor.class).andThen(SuperMethodCall.INSTANCE)
                );
          }
        }).installOn(instrumentation);

    System.out.println("--------------------------------------------------------------");
    System.out.println("TracingAgent has been enabled - may the tracing be with you!!");
    System.out.println("Package to be traced: " + getPackageToBeTraced());
    System.out.println("--------------------------------------------------------------");

    TraceGui.showGui();
  }

//------------------
// private methods
//------------------

  private static String getPackageToBeTraced() {
    String packageToBeTraced = System.getProperty("packageToBeTraced");

    if(packageToBeTraced == null || packageToBeTraced.isEmpty()){
      throw new IllegalArgumentException("System property packageToBeTraced has to be defined!!");
    }
    return packageToBeTraced;
  }
}
