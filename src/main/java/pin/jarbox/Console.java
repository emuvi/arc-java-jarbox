package pin.jarbox;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Console extends PrintStream {

  public static final Console instance = new Console();

  private static final List<String> arguments = new ArrayList<>();

  private static final long startedIn = System.currentTimeMillis();

  public static void start(String[] withArguments) {
    arguments.clear();
    if (withArguments != null) {
      for (String argument : withArguments) {
        arguments.add(argument);
      }
    }
    instance.putOnSystem();
    System.out.println("Console started in " + new Date(startedIn).toString());
  }

  public static boolean hasArgument(String argument) {
    return arguments.contains(argument);
  }

  public static boolean hasArgument(String argument, String orArgument) {
    return arguments.contains(argument) || arguments.contains(orArgument);
  }

  public static String getArgumentValue(String argument, String defaultValue) {
    return getArgumentValue(argument, null, defaultValue);
  }

  public static String getArgumentValue(String argument, String orArgument,
      String defaultValue) {
    for (int i = 0; i < arguments.size(); i++) {
      String arg = arguments.get(i);
      String argName = arg;
      String argValue = i < arguments.size() - 1 ? arguments.get(i + 1) : "";
      if (!argName.startsWith("\"")) {
        int equals = argName.indexOf("=");
        if (equals > -1) {
          argName = arg.substring(0, equals);
          argValue = arg.substring(equals + 1);
        }
      }
      if (argName.equals(argument) || argName.equals(orArgument)) {
        return Utils.getFromDoubleQuotes(argValue);
      }
    }
    return defaultValue;
  }

  public static String[] getArguments() {
    return arguments.toArray(new String[arguments.size()]);
  }

  private static String getBaseName(final String fileName) {
    if (fileName == null) {
      return null;
    }
    final int dot = fileName.lastIndexOf(".");
    if (dot > -1) {
      return fileName.substring(0, dot);
    } else {
      return fileName;
    }
  }

  public static String getOrigin() {
    StringBuilder builder = new StringBuilder();
    builder.append("[ ");
    builder.append(System.currentTimeMillis() - startedIn);
    builder.append(" on ");
    builder.append(Thread.currentThread().getName());
    builder.append(" ] { ");
    boolean first = true;
    StackTraceElement elements[] = Thread.currentThread().getStackTrace();
    for (int i = elements.length - 1; i >= 0; i--) {
      StackTraceElement element = elements[i];
      if (element.getClassName().startsWith("pin.") &&
          !element.getClassName().equals("pin.jarbox.Console")) {
        if (first) {
          first = false;
        } else {
          builder.append(", ");
        }
        builder.append(getBaseName(element.getFileName()));
        builder.append("[");
        builder.append(element.getLineNumber());
        builder.append("](");
        builder.append(element.getMethodName());
        builder.append(")");
          }
    }
    builder.append(" }");
    return builder.toString();
  }

  public static String getOrigin(Throwable ofError) {
    StringBuilder builder = new StringBuilder();
    builder.append("[ ");
    builder.append(System.currentTimeMillis() - startedIn);
    builder.append(" on ");
    builder.append(Thread.currentThread().getName());
    builder.append(" ] { ");
    boolean first = true;
    StackTraceElement elements[] = ofError.getStackTrace();
    for (int i = elements.length - 1; i >= 0; i--) {
      StackTraceElement element = elements[i];
      if (element.getClassName().startsWith("pin.") &&
          !element.getClassName().equals("pin.jarbox.Console")) {
        if (first) {
          first = false;
        } else {
          builder.append(", ");
        }
        builder.append(getBaseName(element.getFileName()));
        builder.append("[");
        builder.append(element.getLineNumber());
        builder.append("](");
        builder.append(element.getMethodName());
        builder.append(")");
          }
    }
    builder.append(" }");
    return builder.toString();
  }

  private final StringBuilder logged;
  private boolean onSystem;
  private PrintStream systemOut;
  private PrintStream systemErr;

  private File saveOnClose;

  public Console() {
    super(new ByteArrayOutputStream(), true);
    logged = new StringBuilder();
    onSystem = false;
    saveOnClose = null;
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        close();
      }
    });
  }

  public boolean isOnSystem() { return onSystem; }

  public void putOnSystem() {
    if (!onSystem) {
      onSystem = true;
      systemOut = System.out;
      systemErr = System.err;
      System.setOut(this);
      System.setErr(this);
    }
  }

  public void backToSystem() {
    if (onSystem) {
      onSystem = false;
      System.setOut(systemOut);
      System.setErr(systemErr);
      systemOut = null;
      systemErr = null;
    }
  }

  public void setSaveOnClose(File saveOnClose) {
    this.saveOnClose = saveOnClose;
  }

  public String getLogged() { return logged.toString(); }

  @Override
  public void close() {
    super.close();
    backToSystem();
    if (saveOnClose != null) {
      try {
        Files.writeString(saveOnClose.toPath(), getLogged());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void flush() {
    if (systemOut != null) {
      systemOut.flush();
    }
    if (systemErr != null) {
      systemErr.flush();
    }
    super.flush();
  }

  @Override
  public void print(String s) {
    if (s == null) {
      s = "null";
    }
    if (systemOut != null) {
      systemOut.print(s);
    }
    logged.append(s);
    super.print(s);
  }

  @Override
  public void print(Object obj) {
    print(String.valueOf(obj));
  }

  @Override
  public void print(boolean b) {
    print(String.valueOf(b));
  }

  @Override
  public void print(char c) {
    print(String.valueOf(c));
  }

  @Override
  public void print(char[] s) {
    print(String.valueOf(s));
  }

  @Override
  public void print(double d) {
    print(String.valueOf(d));
  }

  @Override
  public void print(float f) {
    print(String.valueOf(f));
  }

  @Override
  public void print(int i) {
    print(String.valueOf(i));
  }

  @Override
  public void print(long l) {
    print(String.valueOf(l));
  }

  @Override
  public void println() {
    StringBuilder builder = new StringBuilder();
    builder.append(getOrigin());
    builder.append(System.lineSeparator());
    print(builder.toString());
  }

  @Override
  public void println(String x) {
    if (x == null) {
      x = "null";
    }
    StringBuilder builder = new StringBuilder();
    builder.append(getOrigin());
    builder.append(System.lineSeparator());
    builder.append("    - ");
    builder.append(x);
    builder.append(System.lineSeparator());
    print(builder.toString());
  }

  @Override
  public void println(Object x) {
    println(String.valueOf(x));
  }

  @Override
  public void println(boolean x) {
    println(String.valueOf(x));
  }

  @Override
  public void println(char x) {
    println(String.valueOf(x));
  }

  @Override
  public void println(char[] x) {
    println(String.valueOf(x));
  }

  @Override
  public void println(double x) {
    println(String.valueOf(x));
  }

  @Override
  public void println(float x) {
    println(String.valueOf(x));
  }

  @Override
  public void println(int x) {
    println(String.valueOf(x));
  }

  @Override
  public void println(long x) {
    println(String.valueOf(x));
  }
}
