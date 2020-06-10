package com.skrymer.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Simple agent printing out loaded classes
 */
public class PreMain {

  public static void premain(String agentArgument, Instrumentation instrumentation) {
    //Add a transformer to implement your instrumentation
    instrumentation.addTransformer(new MyTransformer());
  }

  /**
   * Simple transformer that prints loaded classes
   */
  public static class MyTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

      System.out.println("Loaded class: " + className);

      //Use byte code lib to change bytes - asm, bytebuddy....
      return classfileBuffer;
    }
  }
}
