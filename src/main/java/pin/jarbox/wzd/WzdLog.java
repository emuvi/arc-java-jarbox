package pin.jarbox.wzd;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import pin.jarbox.bin.Console;

public class WzdLog {

  public static final boolean DEBUG = false;

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
    }
  }

  public static void treat(String message, Object... values) {
    var formatted = String.format(message, values);
    System.out.println(formatted);
    if (WzdDesk.started) {
      WzdDesk.callOrInvoke(() -> {
        JOptionPane.showMessageDialog(null, formatted, Console.appTitle,
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
    final var formatted = getDescription(error);
    System.out.println(formatted);
    if (!silent && WzdDesk.started) {
      WzdDesk.callOrInvoke(() -> {
        JOptionPane.showMessageDialog(null, formatted, Console.appTitle,
            JOptionPane.ERROR_MESSAGE);
      });
    }
    if (consumers != null) {
      for (var consumer : consumers) {
        consumer.accept(formatted);
      }
    }
  }

  public static String getDescription(Exception error) {
    StringBuilder builder = new StringBuilder();
    builder.append(Console.getOrigin(error));
    builder.append(System.lineSeparator());
    builder.append("    - ");
    builder.append(error.getMessage());
    return builder.toString();
  }

}
