package pin.jarbox.wzd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WzdBin {

  @SafeVarargs
  public static <T> List<T> wait(Future<T>... futures) throws Exception {
    List<T> result = new ArrayList<>();
    for (Future<T> future : futures) {
      result.add(future.get());
    }
    return result;
  }

  public static void delay(int millis, Runnable runnable) {
    new Thread("WzdBin Delay") {
      @Override
      public void run() {
        try {
          sleep(millis);
          runnable.run();
        } catch (Exception e) {
          WzdLog.treat(e);
        }
      }
    }.start();
  }

  @SafeVarargs
  public static <T> T getFirstNonNull(Future<T>... futures) throws Exception {
    while (true) {
      boolean anyWorking = false;
      for (Future<T> future : futures) {
        if (future.isDone()) {
          if (future.get() != null) {
            return future.get();
          }
        } else {
          anyWorking = true;
        }
      }
      if (!anyWorking) {
        break;
      }
    }
    return null;
  }

  private static ExecutorService executor;

  public static ExecutorService getExecutor() {
    if (executor == null) {
      executor = Executors.newCachedThreadPool();
    }
    return executor;
  }

  public static Future<?> submit(Runnable runnable) {
    return getExecutor().submit(runnable);
  }

  public static <T> Future<T> submit(Callable<T> callable) {
    return getExecutor().submit(callable);
  }

}
