package com.skrymer.profiler;

import com.skrymer.profiler.events.ObjectInstantiatedEvent;
import com.skrymer.profiler.events.MethodInvokedEvent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Count how many times a object is created
 *
 * Count method invocations and a average duration
 */
public class Profiler extends Observable {
  private static Profiler instance;

  private Map<String, ClassData> profiling;

  public synchronized static Profiler instance(){
    if(instance == null){
      instance = new Profiler();
    }

    return instance;
  }

  private Profiler(){
    profiling = new HashMap<>();
  }

  /**
   * Use when a class had been created
   *
   * @param className
   */
  public synchronized void classCreated(String className){
    if(profiling.get(className) == null){
      ClassData classData = new ClassData();
      profiling.put(className, classData);
    }else {
      profiling.get(className).incrementNewCount();
    }

    setChanged();
    notifyObservers(new ObjectInstantiatedEvent(className, profiling.get(className).getNewCount()));
  }

  /**
   * Use when a method has been invoked
   *
   * @param className
   * @param methodName
   * @param durationMillis
   */
  public synchronized void methodInvocation(String className, String methodName, long durationMillis){
    if(profiling.get(className) == null){
      ClassData classData = new ClassData();
      classData.addMethodInvocation(methodName, durationMillis);
      profiling.put(className, classData);
    }else {
      profiling.get(className).addMethodInvocation(methodName, durationMillis);
    }

    setChanged();
    notifyObservers(new MethodInvokedEvent(className, methodName, profiling.get(className).getMethodInvocationCount(methodName), durationMillis));
  }

  @Override
  public String toString() {
    return "" + profiling;
  }

  private static class ClassData {
    private Map<String, MethodData> methodInvocations;
    private int newCount;

    ClassData(){
      newCount = 1;
      methodInvocations = new HashMap<>();
    }

    void incrementNewCount(){
      newCount++;
    }

    void addMethodInvocation(String methodName, long duration) {
      if(methodInvocations.get(methodName) == null){
        methodInvocations.put(methodName, new MethodData(1, duration));
      }else{
        methodInvocations.get(methodName).invocation(duration);
      }
    }

    public int getNewCount() {
      return newCount;
    }

    @Override
    public String toString() {
      return "New count: " + newCount + " Method invocations: " + methodInvocations;
    }

    public int getMethodInvocationCount(String methodName) {
      return methodInvocations.get(methodName).invocationCount;
    }

    private static class MethodData{
      private int invocationCount;
      private List<Long> durations;

      MethodData(int invocationCount, long avgDuration){
        this.invocationCount = invocationCount;
        this.durations = new ArrayList<>();
        durations.add(avgDuration);
      }

      void invocation(long duration){
        invocationCount++;
        durations.add(duration);
      }

      @Override
      public String toString() {
        return "Invocations: " + invocationCount + " avg duration: " + durations.stream().collect(Collectors.averagingLong(Long::longValue));
      }
    }
  }
}
