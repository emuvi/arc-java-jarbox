package pin.jarbox.wzd;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import pin.jarbox.bin.Console;

public class WzdLog {

  private static List<Consumer<String>> consumers = null;

  public static void addConsumer(Consumer<String> consumer)  {
    if (consumers == null) {
      consumers = new ArrayList<>();
    }
    consumers.add(consumer);
  }

  public static void delConsumer(Consumer<String> consumer)  {
    if (consumers != null) {
      consumers.remove(consumer);
      if (consumers.isEmpty()) {
        consumers = null;
      }
    }
  }

  public static void debug(String topic, String message, Object... values) {
    var builder = new StringBuilder("[ DEBUG ] ");
    builder.append(topic);
    builder.append(System.lineSeparator());
    builder.append("[ VALUE ] ");
    var formatted = values != null ? String.format(message, values) : message;
    builder.append(getDescription(formatted));
    System.out.println(builder.toString());
  }

  public static void treat(String message, Object... values) {
    var formatted = values != null ? String.format(message, values) : message;
    var description = getDescription(formatted);
    System.out.println(description);
    if (WzdDesk.started) {
      WzdDesk.callOrInvoke(() -> {
        JOptionPane.showMessageDialog(null, description, Console.appTitle,
            JOptionPane.INFORMATION_MESSAGE);
      });
    }
    if (consumers != null) {
      for (var consumer : consumers) {
        consumer.accept(formatted);
      }
    }
  }

  public static void treat(Exception error) {
    treat(error, false);
  }

  public static void treat(Exception error, boolean silent) {
    final var description = getDescription(error);
    System.out.println(description);
    if (!silent && WzdDesk.started) {
      WzdDesk.callOrInvoke(() -> {
        JOptionPane.showMessageDialog(null, description, Console.appTitle,
            JOptionPane.ERROR_MESSAGE);
      });
    }
    if (consumers != null) {
      for (var consumer : consumers) {
        consumer.accept(description);
      }
    }
  }

  public static String getDescription(String forMessage) {
    var builder = new StringBuilder();
    builder.append(forMessage);
    builder.append(System.lineSeparator());
    builder.append("  : ");
    builder.append(Console.getOrigin());
    return builder.toString();
  }

  public static String getDescription(Exception forError) {
    var builder = new StringBuilder();
    builder.append(forError.getMessage());
    builder.append(System.lineSeparator());
    builder.append("  : ");
    builder.append(Console.getOrigin(forError));
    return builder.toString();
  }

}
