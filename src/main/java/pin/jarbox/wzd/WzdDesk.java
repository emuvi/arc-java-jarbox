package pin.jarbox.wzd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import pin.jarbox.bin.Console;
import pin.jarbox.bin.Randomize;

public class WzdDesk {

    public static boolean started = false;

    public static void startSystemLook() throws Exception {
        java.awt.EventQueue.invokeAndWait(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                started = true;
            } catch (Exception e) {
                WzdLog.treat(e);
            }
        });
    }

    public static void message(String message) {
        message(message, false);
    }

    public static void message(String message, boolean silent) {
        WzdLog.treat(message);
        if (!silent) {
            Runnable runner = () -> {
                JOptionPane.showMessageDialog(getActiveWindow(), message, Console.appTitle,
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
        return JOptionPane.showConfirmDialog(getActiveWindow(), question, Console.appTitle,
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static String input(String question, String value) {
        return (String) JOptionPane.showInputDialog(getActiveWindow(), question, Console.appTitle,
                JOptionPane.QUESTION_MESSAGE, null, null, value);
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
            result = new Point(randomize.getInteger(screen.width - window.getWidth()),
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
            Dimension dimension = new Dimension(maxValue, component.getPreferredSize().height);
            component.setMinimumSize(dimension);
            component.setPreferredSize(dimension);
        }
    }

    public static void putShortCut(JComponent component, String name, String keyStroke, Runnable runnable) {
        InputMap inputMap = component.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap = component.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(keyStroke), name);
        actionMap.put(name, getAction(runnable));
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
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    }

    public static void copyToClipboard(String theString) {
        StringSelection selection = new StringSelection(theString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static BufferedImage getImageFromClipboard() throws Exception {
        Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            BufferedImage pasted = (BufferedImage) transferable.getTransferData(DataFlavor.imageFlavor);
            return convertToRGB(pasted);
        } else {
            return null;
        }
    }

    public static BufferedImage convertToRGB(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        return result;
    }

    public static Pattern deskMnemonic = Pattern.compile("\\s\\s\\s\\[\\s.\\s\\]$");

    public static String delMnemonic(String fromTitle) {
        if (fromTitle == null) {
            return fromTitle;
        }
        if (deskMnemonic.matcher(fromTitle).find()) {
            return fromTitle.substring(0, fromTitle.length() - 8);
        } else {
            return fromTitle;
        }
    }

    public static JComponent getItem(JMenuBar fromBar, String onPath) {
        JComponent result = fromBar;
        if (onPath != null) {
            String[] parts = onPath.split("\\.");
            if (parts.length > 0) {
                for (String part : parts) {
                    if (result instanceof JMenu) {
                        boolean found = false;
                        for (Component comp : ((JMenu) result).getMenuComponents()) {
                            if (comp instanceof JMenu) {
                                if (part.equals(delMnemonic(((JMenu) comp).getText()))) {
                                    result = (JMenu) comp;
                                    found = true;
                                }
                            } else if (comp instanceof JMenuItem) {
                                if (part.equals(delMnemonic(((JMenuItem) comp).getText()))) {
                                    result = (JMenuItem) comp;
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            return null;
                        }
                    } else {
                        boolean found = false;
                        for (Component comp : result.getComponents()) {
                            if (comp instanceof JMenu) {
                                if (part.equals(delMnemonic(((JMenu) comp).getText()))) {
                                    result = (JMenu) comp;
                                    found = true;
                                }
                            } else if (comp instanceof JMenuItem) {
                                if (part.equals(delMnemonic(((JMenuItem) comp).getText()))) {
                                    result = (JMenuItem) comp;
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            return null;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static JComponent getItem(JPopupMenu doPopup, String onPath) {
        JComponent result = doPopup;
        if (onPath != null) {
            String[] parts = onPath.split("\\.");
            if (parts.length > 0) {
                for (String part : parts) {
                    if (result instanceof JMenu) {
                        boolean found = false;
                        for (Component comp : ((JMenu) result).getMenuComponents()) {
                            if (comp instanceof JMenu) {
                                if (part.equals(delMnemonic(((JMenu) comp).getText()))) {
                                    result = (JMenu) comp;
                                    found = true;
                                }
                            } else if (comp instanceof JMenuItem) {
                                if (part.equals(delMnemonic(((JMenuItem) comp).getText()))) {
                                    result = (JMenuItem) comp;
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            return null;
                        }
                    } else {
                        boolean found = false;
                        for (Component comp : result.getComponents()) {
                            if (comp instanceof JMenu) {
                                if (part.equals(delMnemonic(((JMenu) comp).getText()))) {
                                    result = (JMenu) comp;
                                    found = true;
                                }
                            } else if (comp instanceof JMenuItem) {
                                if (part.equals(delMnemonic(((JMenuItem) comp).getText()))) {
                                    result = (JMenuItem) comp;
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            return null;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static JMenu getMenu(JMenuBar fromBar, String onPath) {
        JMenu result = null;
        if (onPath != null) {
            String[] parts = onPath.split("\\.");
            if (parts.length > 0) {
                for (Component comp : fromBar.getComponents()) {
                    if (comp instanceof JMenu) {
                        if (parts[0].equals(delMnemonic(((JMenu) comp).getText()))) {
                            result = (JMenu) comp;
                            break;
                        }
                    }
                }
                for (int ip = 1; ip < parts.length; ip++) {
                    result = getMenu(result, parts[ip]);
                    if (result == null) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static JMenu getMenu(JPopupMenu doPopup, String onPath) {
        JMenu result = null;
        if (onPath != null) {
            String[] parts = onPath.split("\\.");
            if (parts.length > 0) {
                for (Component comp : doPopup.getComponents()) {
                    if (comp instanceof JMenu) {
                        if (parts[0].equals(delMnemonic(((JMenu) comp).getText()))) {
                            result = (JMenu) comp;
                            break;
                        }
                    }
                }
                for (int ip = 1; ip < parts.length; ip++) {
                    result = getMenu(result, parts[ip]);
                    if (result == null) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static JMenu getMenu(JMenu fromMenu, String withTitle) {
        JMenu result = null;
        if (withTitle != null) {
            for (Component comp : fromMenu.getMenuComponents()) {
                if (comp instanceof JMenu) {
                    if (withTitle.equals(delMnemonic(((JMenu) comp).getText()))) {
                        result = (JMenu) comp;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static JMenuItem getMenuItem(JPopupMenu doPopup, String onPath) {
        if (onPath != null) {
            String[] parts = onPath.split("\\.");
            if (parts.length > 0) {
                JMenu menu = null;
                for (Component comp : doPopup.getComponents()) {
                    if (comp instanceof JMenu) {
                        if (parts[0].equals(delMnemonic(((JMenu) comp).getText()))) {
                            menu = (JMenu) comp;
                            break;
                        }
                    } else if (comp instanceof JMenuItem) {
                        if (parts[0].equals(delMnemonic(((JMenuItem) comp).getText()))) {
                            return (JMenuItem) comp;
                        }
                    }
                }
                if (menu == null) {
                    return null;
                }
                for (int ip = 1; ip < parts.length - 1; ip++) {
                    menu = getMenu(menu, parts[ip]);
                    if (menu == null) {
                        return null;
                    }
                }
                return getMenuItem(menu, parts[parts.length - 1]);
            }
        }
        return null;
    }

    public static JMenuItem getMenuItem(JMenu fromMenu, String withTitle) {
        JMenuItem result = null;
        if (withTitle != null) {
            for (Component comp : fromMenu.getMenuComponents()) {
                if (comp instanceof JMenuItem) {
                    if (withTitle.equals(delMnemonic(((JMenuItem) comp).getText()))) {
                        result = (JMenuItem) comp;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static void execute(ActionListener[] actions) {
        if (actions != null) {
            for (ActionListener act : actions) {
                act.actionPerformed(null);
            }
        }
    }

    public static void callOrInvoke(Runnable runnable) {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeLater(runnable);
        }
    }

    public static void callOrWait(Runnable runnable) throws Exception {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeAndWait(runnable);
        }
    }

}
