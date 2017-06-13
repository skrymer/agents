package com.skrymer.commonagent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.Transformer;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.utility.JavaModule;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * Add toString, equals and hashCode methods to classes with annotation {@link Common}
 */
public class Commons {
  public static void premain(String agentArgument, Instrumentation instrumentation) {
    new AgentBuilder.Default()
        .type(isAnnotatedWith(Common.class))
        .transform((builder, typeDescription, classLoader, module) ->
            builder
                .method(named("toString"))
                .intercept(MethodDelegation.to(ToStringInterceptor.class))
                .method(named("equals"))
                .intercept(MethodDelegation.to(EqualsInterceptor.class))
                .method(named("hashCode"))
                .intercept(MethodDelegation.to(HashCodeInterceptor.class))
        ).installOn(instrumentation);
  }

  public static class ToStringInterceptor {
    public static String intecept(@This Object thiz){
      return ToStringBuilder.reflectionToString(thiz, ToStringStyle.SIMPLE_STYLE);
    }
  }

  public static class EqualsInterceptor {
    public static boolean intecept(@This Object thiz, @AllArguments Object[] args){
      return EqualsBuilder.reflectionEquals(thiz, args[0]);
    }
  }

  public static class HashCodeInterceptor {
    public static int intecept(@This Object thiz){
      return HashCodeBuilder.reflectionHashCode(thiz);
    }
  }
}
