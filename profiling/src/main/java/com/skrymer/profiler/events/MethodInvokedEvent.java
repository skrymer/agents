package com.skrymer.profiler.events;

/**
 * Method invoked event
 */
public class MethodInvokedEvent extends Event {
  private String className;
  private String methodName;
  private int invocationCount;
  private MethodCallDuration methodCallDuration;

  public MethodInvokedEvent(String className, String methodName, int invocationCount, long durationMillis){
    this.className = className;
    this.methodName = methodName;
    this.invocationCount = invocationCount;
    this.methodCallDuration = new MethodCallDuration(durationMillis);
  }

  public String getClassName() {
    return className;
  }

  public String getMethodName() {
    return methodName;
  }

  @Override
  public String toString() {
    return "Method: " + methodName + " in class: " + className + " was invoked.";
  }

  public MethodCallDuration getMethodCallDuration() {
    return this.methodCallDuration;
  }

  public int getInvocationCount() {
    return invocationCount;
  }

  @Override
  public String getDescription() {
    return "Received event: " + this.getMethodName() + " was invoked on class" + this.getClassName() + "\n";
  }

  public static class MethodCallDuration {
    private long durationMillis;

    public MethodCallDuration(long durationMillis) {
      this.durationMillis = durationMillis;
    }

    @Override
    public String toString() {
      return "Avg duration: " + durationMillis;
    }

    public long getDuration() {
      return durationMillis;
    }
  }
}
