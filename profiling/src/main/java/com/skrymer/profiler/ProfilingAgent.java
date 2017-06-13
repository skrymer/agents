package com.skrymer.profiler;

import com.skrymer.profiler.ui.ProfilerUI;
import javassist.*;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class ProfilingAgent implements ClassFileTransformer {
  public static final String NO_ARG_CONSTRUCTOR_SIGNATURE = "()V";

  /**
   * Java agent entry point were instrumentation can be added
   *
   * @param agentArgument
   * @param instrumentation
   */
  public static void premain(String agentArgument, Instrumentation instrumentation){
    instrumentation.addTransformer(new ProfilingAgent());
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

  public byte[] transform(ClassLoader loader,
                          String className,
                          Class<?> classBeingRedefined,
                          ProtectionDomain protectionDomain,
                          byte[] classfileBuffer) throws IllegalClassFormatException {

    if(className.contains(getPackageToBeProfiled())){
      ClassPool pool = ClassPool.getDefault();
      CtClass clazz = null;

      try {
        pool.importPackage("com.skrymer.profiler");
        clazz = pool.makeClass(new ByteArrayInputStream(classfileBuffer));

        addProfilingToConstructors(clazz);
        addProfilingToMethods(clazz);

        return clazz.toBytecode();
      } catch (Exception e) {
        e.printStackTrace();
      }
      finally {
        if (clazz != null) {
          clazz.detach();
        }
      }
    }

    return classfileBuffer;
  }

  private void addProfilingToConstructors(CtClass clazz) throws Exception {
    for(CtConstructor constructor : clazz.getConstructors()){
      if(NO_ARG_CONSTRUCTOR_SIGNATURE.equals(constructor.getSignature())) {
        constructor.insertBeforeBody("Profiler.instance().classCreated(" + "\"" + clazz.getName() + "\"" + ");");
      }
    }
  }

  private void addProfilingToMethods(CtClass clazz) throws Exception {
    for(CtMethod method : clazz.getDeclaredMethods()){
      if(!method.isEmpty()) {
        method.addLocalVariable("duration", CtClass.longType);
        method.insertBefore("duration = System.currentTimeMillis();");
        method.insertAfter("duration = System.currentTimeMillis() - duration;");
        method.insertAfter("Profiler.instance().methodInvocation(" + "\"" + clazz.getName() + "\"" + ", " + "\"" + method.getName() + "\"," + " duration);");
      }
    }
  }
}
