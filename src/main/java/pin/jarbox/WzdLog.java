package pin.jarbox;

import javax.swing.JOptionPane;

public class WzdLog {

  public static final boolean DEBUG = false;

  public static void treat(String message, Object... values) {
    System.out.println(String.format(message, values));
  }

  public static void treat(Exception error) {
    treat(error, false);
  }

  public static void treat(Exception error, boolean silent) {
    System.out.println(getDescription(error));
    if (!silent && WzdDesk.started) {
      WzdDesk.callOrInvoke(() -> {
        JOptionPane.showMessageDialog(null, error.getMessage(), WzdBin.title,
            JOptionPane.ERROR_MESSAGE);
      });
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
