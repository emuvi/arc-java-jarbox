package pin.jarbox.wzd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WzdLang {

  public static Boolean isWin() {
    String OS = System.getProperty("os.name").toLowerCase();
    return (OS.contains("win"));
}

public static Boolean isNix() {
    String OS = System.getProperty("os.name").toLowerCase();
    return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0);
}

public static Boolean isMac() {
    String OS = System.getProperty("os.name").toLowerCase();
    return (OS.contains("mac"));
}

public static Boolean isSol() {
    String OS = System.getProperty("os.name").toLowerCase();
    return (OS.contains("sunos"));
}

public static Class<?> getPrimitive(Class<?> ofClazz) {
  if (ofClazz == null) {
      return null;
  }
  if (ofClazz.equals(Boolean.class)) {
      return boolean.class;
  } else if (ofClazz.equals(Byte.class)) {
      return byte.class;
  } else if (ofClazz.equals(Character.class)) {
      return char.class;
  } else if (ofClazz.equals(Double.class)) {
      return double.class;
  } else if (ofClazz.equals(Float.class)) {
      return float.class;
  } else if (ofClazz.equals(Integer.class)) {
      return int.class;
  } else if (ofClazz.equals(Long.class)) {
      return long.class;
  } else if (ofClazz.equals(Short.class)) {
      return short.class;
  } else if (ofClazz.equals(Void.class)) {
      return void.class;
  } else {
      return ofClazz;
  }
}

public static Class<?> getFromPrimitive(Class<?> clazz) {
  if (clazz == null) {
      return null;
  }
  if (clazz.equals(boolean.class)) {
      return Boolean.class;
  } else if (clazz.equals(byte.class)) {
      return Byte.class;
  } else if (clazz.equals(char.class)) {
      return Character.class;
  } else if (clazz.equals(double.class)) {
      return Double.class;
  } else if (clazz.equals(float.class)) {
      return Float.class;
  } else if (clazz.equals(int.class)) {
      return Integer.class;
  } else if (clazz.equals(long.class)) {
      return Long.class;
  } else if (clazz.equals(short.class)) {
      return Short.class;
  } else if (clazz.equals(void.class)) {
      return Void.class;
  } else {
      return clazz;
  }
}

public static Boolean isPrimitive(Class<?> clazz) {
  if (clazz == null) {
      return false;
  }
  if (clazz.equals(boolean.class)) {
      return true;
  } else if (clazz.equals(byte.class)) {
      return true;
  } else if (clazz.equals(char.class)) {
      return true;
  } else if (clazz.equals(double.class)) {
      return true;
  } else if (clazz.equals(float.class)) {
      return true;
  } else if (clazz.equals(int.class)) {
      return true;
  } else if (clazz.equals(long.class)) {
      return true;
  } else if (clazz.equals(short.class)) {
      return true;
  } else if (clazz.equals(void.class)) {
      return true;
  } else {
      return false;
  }
}

  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

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
