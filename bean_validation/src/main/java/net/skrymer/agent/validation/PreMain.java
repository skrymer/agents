package net.skrymer.agent.validation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.Arrays;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * Bean validation agent
 */
public class PreMain {

  public static void premain(String agentArgument, Instrumentation instrumentation) {
    System.out.println("Hello from premain");

    new AgentBuilder.Default()
        .type(ElementMatchers.nameStartsWith("net.skrymer"))
        .transform((builder, typeDescription, classLoader, module) ->
            builder
                .method(ElementMatchers.isAnnotatedWith(Validate.class))
                .intercept(MethodDelegation.to(MethodInterceptor.class))
        )
        .installOn(instrumentation);
  }

  static class MethodInterceptor {
    static void validate(@This Object thiz, @Origin Method method, @AllArguments Object[] args) throws NoSuchMethodException {
      System.out.println(String.format("Validating %s method: %s args: %s", thiz, method, Arrays.toString(args)));
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      Validator validator = factory.getValidator();
      var violations = validator.forExecutables().validateParameters(thiz, method, args);
      violations.forEach(System.out::println);
    }
  }
}
