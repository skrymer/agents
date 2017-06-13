package com.skrymer.traceagent;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Inspects method calls
 */
public class Interceptor {

  public static void construct(@This Object thiz,
                               @Origin Constructor<?> constructor,
                               @AllArguments Object[] args){
    Tracer.getTracer().trace(thiz, Invocation.CONSTRUCTOR, constructor.getName(), args);
  }

  public static void intercept(@This Object thiz,
                               @Origin Method method,
                               @AllArguments Object[] args){
    Tracer.getTracer().trace(thiz, Invocation.METHOD, method.getName(), args);
  }
}
