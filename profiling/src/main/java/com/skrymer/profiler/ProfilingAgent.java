package com.skrymer.profiler;

import com.skrymer.profiler.ui.ProfilerUI;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.isDefaultConstructor;
import static net.bytebuddy.matcher.ElementMatchers.nameContainsIgnoreCase;

public class ProfilingAgent {

  public static void premain(String agentArgument, Instrumentation instrumentation){
    new AgentBuilder.Default()
        .with(new AgentListener())
        .type(nameContainsIgnoreCase(getPackageToBeProfiled()))
        .transform((builder, typeDescription, classLoader, javaModule) -> builder
            .constructor(isDefaultConstructor())
            .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.to(ConstructorInterceptor.class)))
            .method(any())
            .intercept(MethodDelegation.to(MethodInterceptor.class)
                .andThen(SuperMethodCall.INSTANCE)
            )
        ).installOn(instrumentation);

    ProfilerUI.show();
    System.out.println("--------------------------------------------------------------");
    System.out.println("ProfilingAgent has been enabled - may the profiling be with you!!");
    System.out.println("Package to be profiled: " + getPackageToBeProfiled());
    System.out.println("--------------------------------------------------------------");
  }

  public static String getPackageToBeProfiled(){
    String packageToBeProfiled = System.getProperty("packageToBeProfiled");

    if(packageToBeProfiled == null || packageToBeProfiled.isEmpty()){
      throw new IllegalArgumentException("System property packageToBeProfiled has to be defined!!");
    }

    return packageToBeProfiled;
  }
}
