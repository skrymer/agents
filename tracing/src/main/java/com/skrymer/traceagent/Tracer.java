package com.skrymer.traceagent;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Tracer - traces class invocations
 */
public class Tracer extends Observable {
  private static Tracer tracer = new Tracer();
  private static final Map<String, ObjectTrace> traceInfoMap;
  private static final ExecutorService executor;

  static {
    traceInfoMap = new HashMap<>();
    executor = Executors.newFixedThreadPool(5);
  }

  private Tracer() {}

  public static Tracer getTracer() {
    return tracer;
  }

  /**
   * Would it make sense to call this asynchronously?
   * This would cause issues in multi threaded env as each thread has to wait
   *
   * @param object
   * @param invocation
   * @param signature
   */
  public void trace(Object object, Invocation invocation, String signature) {
    trace(object, invocation, signature, null);
  }

  public void trace(Object object, Invocation invocation, String signature, Object... args) {
    synchronized (this) {
      ObjectTrace objectTrace = traceInfoMap.get(object.getClass().getName());

      if (objectTrace == null) {
        traceInfoMap.put(object.getClass().getName(), new ObjectTrace(object.getClass().getName(),
            new InvocationTrace(Thread.currentThread().getId(), signature, invocation, LocalDateTime.now(), args))
        );
      } else {
        objectTrace.addTraceInvocation(new InvocationTrace(Thread.currentThread().getId(), signature, invocation, LocalDateTime.now(), args));
      }
    }

    //Don't wait for observers to do there thing
    executor.submit(() -> {
      setChanged();
      notifyObservers(traceInfoMap.get(object.getClass().getName()));
      clearChanged();
    });
  }

  public Map<String, ObjectTrace> getTraceInfoMap() {
    return Collections.unmodifiableMap(this.traceInfoMap);
  }

//-----------------------
// Inner classes
//-----------------------

  /**
   * Contains tracing info for a object
   */
  public static final class ObjectTrace {
    private final String className;
    private final List<InvocationTrace> invocationTraces;

    public ObjectTrace(String className, InvocationTrace invocationTrace) {
      this.className = className;
      this.invocationTraces = new ArrayList<>();
      this.invocationTraces.add(invocationTrace);
    }

    public void addTraceInvocation(InvocationTrace invocationTrace) {
      this.invocationTraces.add(invocationTrace);
    }

    public List<InvocationTrace> getInvocationTraces() {
      return invocationTraces;
    }

    @Override
    public boolean equals(Object obj) {
      return EqualsBuilder.reflectionEquals(this, obj, "invocationTraces");
    }

    @Override
    public int hashCode() {
      return className.hashCode();
    }

    @Override
    public String toString() {
      return className;
    }
  }

  /**
   *  Trace info on a invocation - constructor or method
   */
  protected static final class InvocationTrace {
    private long threadId;
    private String signature;
    private Invocation invocation;
    private LocalDateTime invoked;
    private Object[] args;

    public InvocationTrace(long threadId, String signature, Invocation invocation, LocalDateTime invoked, Object[] args) {
      this.threadId = threadId;
      this.signature = signature;
      this.invocation = invocation;
      this.invoked = invoked;
      this.args = args;
    }

    @Override
    public String toString() {
      final StringBuilder toStringBuilder = new StringBuilder()
          .append("Invocation: ").append(invocation).append("\n")
          .append("Signature: ").append(signature).append("\n")
          .append("Invoked: ").append(invoked).append("\n")
          .append("Thread id: ").append(threadId).append("\n");

      if(args != null && args.length > 0) {
          toStringBuilder.append("Args:").append("\n");

        Arrays.stream(args).forEach(o -> toStringBuilder.append("\t").append(o).append("\n"));
      }

      return toStringBuilder.toString();
    }
  }
}
