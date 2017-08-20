package com.skrymer.profiler.agent;

import com.skrymer.profiler.Profiler;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Inspects method calls
 */
public class MethodInterceptor {


  @RuntimeType
  public static Object intercept(@This Object thiz,
                                 @Origin Method method,
                                 @SuperCall Callable<?> zuper) throws Exception {
    long durationMillis = System.currentTimeMillis();
    try {
      return zuper.call();
    } finally {
      durationMillis = System.currentTimeMillis() - durationMillis;
      Profiler.instance().methodInvocation(thiz.getClass().getName(), method.getName(), durationMillis);
    }
  }
}
