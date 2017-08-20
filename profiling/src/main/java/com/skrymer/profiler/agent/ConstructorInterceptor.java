package com.skrymer.profiler.agent;

import com.skrymer.profiler.Profiler;
import net.bytebuddy.implementation.bind.annotation.This;

/**
 * Created by skrymer on 14/06/17.
 */
public class ConstructorInterceptor {
  public static void construct(@This Object thiz){
    Profiler.instance().classCreated(thiz.getClass().getName());
  }

}
