package pin.jarbox.bin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pin.jarbox.wzd.WzdChars;
import pin.jarbox.wzd.WzdDate;
import pin.jarbox.wzd.WzdFile;

public class Console extends PrintStream {

  public static String appTitle;
  public static String appVersion;
  public static String appDescription;

  public static final Console instance = new Console();

  private static final List<String> arguments = new ArrayList<>();

  private static final long startedIn = System.currentTimeMillis();

  public static void start(String appTitle, String appVersion, String[] withArguments,
      String appDescription) {
    Console.appTitle = appTitle;
    Console.appVersion = appVersion;
    Console.appDescription = appDescription;
    arguments.clear();
    if (withArguments != null) {
      for (String argument : withArguments) {
        arguments.add(argument);
      }
    }
    instance.putOnSystem();
    System.out.println("Console of " + appTitle + " (" + appVersion + ") started in "
        + WzdDate.MOMMENT_FORMAT.format(new Date(startedIn)));
  }

  public static String[] getArguments() {
    return arguments.toArray(new String[arguments.size()]);
  }

  public static boolean hasArgument(Character shortName) {
    return hasArgument(shortName, null);
  }

  public static boolean hasArgument(String longName) {
    return hasArgument(null, longName);
  }

  public static boolean hasArgument(Character shortName, String longName) {
    final var shortCheck = shortName == null ? null : "" + shortName;
    final var longCheck = longName == null ? null : "--" + longName;
    for (String argument : arguments) {
      if (argument.startsWith("--")) {
        if (longCheck != null && argument.equals(longCheck)) {
          return true;
        }
      } else if (argument.startsWith("-")) {
        if (shortCheck != null && argument.contains(shortCheck)) {
          return true;
        }
      }
    }
    return false;
  }

  public static String getArgumentValue(Character shortName, String defaultValue) {
    return getArgumentValue(shortName, null, defaultValue);
  }

  public static String getArgumentValue(String longName, String defaultValue) {
    return getArgumentValue(null, longName, defaultValue);
  }

  public static String getArgumentValue(Character shortName, String longName,
      String defaultValue) {
    final var shortCheck = shortName == null ? null : "" + shortName;
    final var longCheck = longName == null ? null : "--" + longName;
    for (int i = 0; i < arguments.size(); i++) {
      String argument = arguments.get(i);
      if (argument.startsWith("--")) {
        if (longCheck != null && argument.equals(longCheck)) {
          if (i < arguments.size() - 1) {
            return arguments.get(i + 1);
          }
        }
      } else if (argument.startsWith("-")) {
        if (shortCheck != null && argument.contains(shortCheck)) {
          if (i < arguments.size() - 1) {
            return arguments.get(i + 1);
          }
        }
      }
    }
    return defaultValue;
  }

  public static void printUsage(ConsoleOption... options) {
    StringBuilder builder = new StringBuilder();
    builder.append(appTitle);
    builder.append(" (");
    builder.append(appVersion);
    builder.append(") : ");
    builder.append(appDescription);
    builder.append(System.lineSeparator());
    builder.append(System.lineSeparator());
    int maxNames = 0;
    for (ConsoleOption opt : options) {
      maxNames = Math.max(maxNames, opt.printNames().length());
    }
    for (ConsoleOption opt : options) {
      builder.append(WzdChars.fill(opt.printNames(), ' ', maxNames));
      builder.append(" : ");
      builder.append(opt.getDescription());
      builder.append(opt.printDefault());
      builder.append(System.lineSeparator());
    }
    System.out.println(builder.toString());
  }

  public static void printVersion() {
    System.out.println(appTitle + " - Version: " + appVersion);
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
      if (element.getClassName().startsWith("pin.") && !element.getClassName().equals(
          "pin.jarbox.bin.Console") && !element.getClassName().equals(
              "pin.jarbox.wzd.WzdLog")) {
        if (first) {
          first = false;
        } else {
          builder.append(", ");
        }
        builder.append(WzdFile.getBaseName(element.getFileName()));
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
      if (element.getClassName().startsWith("pin.") && !element.getClassName().equals(
          "pin.jarbox.bin.Console") && !element.getClassName().equals(
              "pin.jarbox.wzd.WzdLog")) {
        if (first) {
          first = false;
        } else {
          builder.append(", ");
        }
        builder.append(WzdFile.getBaseName(element.getFileName()));
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

  public boolean isOnSystem() {
    return onSystem;
  }

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

  public String getLogged() {
    return logged.toString();
  }

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
