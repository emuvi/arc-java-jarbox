package pin.jarbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Utils {

  // Log Utils

  public static final boolean DEBUG = false;

  public static void treat(String message, Object... values) {
    System.out.println(String.format(message, values));
  }

  public static void treat(Exception error) { treat(error, false); }

  public static void treat(Exception error, boolean silent) {
    System.out.print(getDescription(error));
    if (!silent) {
      Runnable runner = () -> {
        JOptionPane.showMessageDialog(null, error.getMessage(), title,
            JOptionPane.ERROR_MESSAGE);
      };
      if (SwingUtilities.isEventDispatchThread()) {
        runner.run();
      } else {
        SwingUtilities.invokeLater(runner);
      }
    }
  }

  public static String getDescription(Exception error) {
    StringBuilder builder = new StringBuilder();
    builder.append(Console.getOrigin(error));
    builder.append(System.lineSeparator());
    builder.append("    - ");
    builder.append(error.getMessage());
    builder.append(System.lineSeparator());
    return builder.toString();
  }

  // Interface Utils

  public static String title = "Pointel";

  public static void startSystemLook() {
    java.awt.EventQueue.invokeLater(() -> {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
        Utils.treat(e, true);
      }
    });
  }

  public static void startMain(Helm helm) {
    if (helm.window instanceof JFrame) {
      title = ((JFrame) helm.window).getTitle();
    } else if (helm.window instanceof JDialog) {
      title = ((JDialog) helm.window).getTitle();
    }
    java.awt.EventQueue.invokeLater(() -> {
      try {
        helm.show();
      } catch (Exception e) {
        Utils.treat(e);
      }
    });
  }

  public static void message(String message) { message(message, false); }

  public static void message(String message, boolean silent) {
    treat(message);
    if (!silent) {
      Runnable runner = () -> {
        JOptionPane.showMessageDialog(getActiveWindow(), message, title,
            JOptionPane.INFORMATION_MESSAGE);
      };
      if (SwingUtilities.isEventDispatchThread()) {
        runner.run();
      } else {
        SwingUtilities.invokeLater(runner);
      }
    }
  }

  public static boolean question(String question) {
    return JOptionPane.showConfirmDialog(getActiveWindow(), question, title,
        JOptionPane.YES_NO_OPTION) ==
      JOptionPane.YES_OPTION;
  }

  public static String input(String question, String value) {
    return (String)JOptionPane.showInputDialog(
        getActiveWindow(), question, title, JOptionPane.QUESTION_MESSAGE, null,
        null, value);
  }

  public static Window getActiveWindow() {
    for (Window old : JFrame.getWindows()) {
      if (old.isActive()) {
        return old;
      }
    }
    return null;
  }

  public static void setNextLocationFor(Window window) {
    Point result = null;
    Window active = getActiveWindow();
    if (active != null) {
      result = new Point(active.getX() + 45, active.getY() + 45);
    }
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    if (result == null) {
      Randomize randomize = new Randomize();
      result =
        new Point(randomize.getInteger(screen.width - window.getWidth()),
            randomize.getInteger(screen.height - window.getHeight()));
    } else {
      if (result.x + window.getWidth() > screen.width) {
        result.x = screen.width - window.getWidth();
      }
      if (result.y + window.getHeight() > screen.height) {
        result.y = screen.height - window.getHeight();
      }
    }
    window.setLocation(result);
  }

  public static JPanel wrap(JComponent component, String title) {
    JPanel result = new JPanel(new BorderLayout(0, 0));
    result.add(new JLabel(title), BorderLayout.NORTH);
    result.add(component, BorderLayout.CENTER);
    return result;
  }

  public static void setWidthMinAsPreferredMax(JComponent... ofComponents) {
    int maxValue = 0;
    for (JComponent component : ofComponents) {
      if (component.getPreferredSize().width > maxValue) {
        maxValue = component.getPreferredSize().width;
      }
    }
    for (JComponent component : ofComponents) {
      Dimension dimension =
        new Dimension(maxValue, component.getPreferredSize().height);
      component.setMinimumSize(dimension);
      component.setPreferredSize(dimension);
    }
  }

  public static void putShortCut(JComponent component, String name,
      String keyStroke, Runnable runnable) {
    InputMap inputMap =
      component.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    ActionMap actionMap = component.getActionMap();
    inputMap.put(KeyStroke.getKeyStroke(keyStroke), name);
    actionMap.put(name, Utils.getAction(runnable));
  }

  public static Action getAction(Runnable runnable) {
    return new AbstractAction() {
      private static final long serialVersionUID = -1482117853128881492L;

      @Override
      public void actionPerformed(ActionEvent e) {
        runnable.run();
      }
    };
  }

  public static String getStringFromClipboard() throws Exception {
    return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(
        DataFlavor.stringFlavor);
  }

  public static void copyToClipboard(String theString) {
    StringSelection selection = new StringSelection(theString);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(selection, selection);
  }

  public static BufferedImage getImageFromClipboard() throws Exception {
    Transferable transferable =
      Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
    if (transferable != null &&
        transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
      BufferedImage pasted =
        (BufferedImage)transferable.getTransferData(DataFlavor.imageFlavor);
      return convertToRGB(pasted);
    } else {
      return null;
    }
  }

  public static BufferedImage convertToRGB(BufferedImage image) {
    BufferedImage result = new BufferedImage(
        image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
    return result;
  }

  // String Utils

  public static String spaceUppers(final String string) {
    if (string == null) {
      return null;
    }
    String result = string;
    for (int i = 'A'; i <= 'Z'; i++) {
      result = result.replace(((char)i) + "", " " + ((char)i));
    }
    return result.trim();
  }

  public static boolean isFirstUpper(final String string) {
    if (string == null) {
      return false;
    }
    if (string.length() > 0) {
      final String first = string.substring(0, 1);
      return first.toUpperCase().equals(first);
    } else {
      return false;
    }
  }

  public static String firstUpper(final String string) {
    StringBuilder result = new StringBuilder();
    if (string.length() > 0) {
      result.append(string.substring(0, 1).toUpperCase());
    }
    if (string.length() > 1) {
      result.append(string.substring(1).toLowerCase());
    }
    return result.toString();
  }

  public static String upperFirst(final String string) {
    StringBuilder result = new StringBuilder();
    if (string.length() > 0) {
      result.append(string.substring(0, 1).toUpperCase());
    }
    if (string.length() > 1) {
      result.append(string.substring(1));
    }
    return result.toString();
  }

  public static String getFromDoubleQuotes(final String string) {
    if (string == null) {
      return string;
    }
    if (string.length() >= 2) {
      if (string.charAt(0) == '"' &&
          string.charAt(string.length() - 1) == '"') {
        return string.substring(1, string.length() - 1);
      } else {
        return string;
      }
    } else {
      return string;
    }
  }

  public static Number getNumber(final String string) {
    if (string == null) {
      return null;
    }
    if (string.contains(".")) {
      return Double.parseDouble(string);
    } else {
      return Integer.parseInt(string);
    }
  }

  public static String fill(final char withChar, final int untilLength) {
    return fill(null, withChar, untilLength, true);
  }

  public static String fill(final String theString, final char withChar,
      final int untilLength, final boolean atStart) {
    StringBuilder result = new StringBuilder();
    final int diference =
      untilLength - (theString != null ? theString.length() : 0);
    if (!atStart && theString != null) {
      result.append(theString);
    }
    for (int i = 0; i < diference; i++) {
      result.append(withChar);
    }
    if (atStart && theString != null) {
      result.append(theString);
    }
    return result.toString();
  }

  public static final String LINE_SEPARATOR = "\r\n";

  // File Utils

  public static String getBaseName(final String fileName) {
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

  public static String getExtension(final String fileName) {
    if (fileName == null) {
      return null;
    }
    final int dot = fileName.lastIndexOf(".");
    if (dot > -1) {
      return fileName.substring(dot);
    } else {
      return "";
    }
  }

  // DataBase Utils

  public static String clean(String theString) {
    if (theString == null)
      return "";
    return theString.replace("\"", "\"\"");
  }

  public static String formatDate(Date theDate) {
    if (theDate == null)
      return "";
    return DATE_FORMAT.format(theDate);
  }

  public static String formatTime(Time theTime) {
    if (theTime == null)
      return "";
    return TIME_FORMAT.format(theTime);
  }

  public static String formatTimestamp(Timestamp theTimestamp) {
    if (theTimestamp == null)
      return "";
    return TIMESTAMP_FORMAT.format(theTimestamp);
  }

  public static String encodeBase64(byte[] theBytes) {
    return Base64.getEncoder().encodeToString(theBytes);
  }

  public static String getClassOfSQL(int jdbcType) {
    switch (jdbcType) {
      case 16:
        return "Boolean";
      case -7:
      case -6:
      case 5:
      case 4:
        return "Integer";
      case -5:
        return "Long";
      case 6:
      case 7:
        return "Float";
      case 8:
      case 2:
      case 3:
        return "Double";
      case 1:
      case -15:
        return "Character";
      case 12:
      case -1:
      case -9:
      case -16:
        return "String";
      case 91:
        return "Date";
      case 92:
      case 2013:
        return "Time";
      case 93:
      case 2014:
        return "Timestamp";
      case -2:
      case -3:
      case -4:
      case 2004:
      case 2005:
      case 2011:
      case 2009:
        return "Byte";
      default:
        return "";
    }
  }

  public static final SimpleDateFormat DATE_FORMAT =
    new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat TIME_FORMAT =
    new SimpleDateFormat("HH:mm:ss");
  public static final SimpleDateFormat TIMESTAMP_FORMAT =
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  // Threads

  @SafeVarargs
  public static <T> List<T> wait(Future<T>... futures) throws Exception {
    List<T> result = new ArrayList<>();
    for (Future<T> future : futures) {
      result.add(future.get());
    }
    return result;
  }

  public static void delay(int millis, Runnable runnable) {
    new Thread("Utils Delay") {
      @Override
      public void run() {
        try {
          sleep(millis);
          runnable.run();
        } catch (Exception e) {
          Utils.treat(e);
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

  public static Future<?> submit(Runnable runnable) {
    return executor.submit(runnable);
  }

  public static <T> Future<T> submit(Callable<T> callable) {
    return executor.submit(callable);
  }

  private static final ExecutorService executor =
    Executors.newCachedThreadPool();
}
