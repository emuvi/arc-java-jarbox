package pin.jarbox;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class WzdDesk {

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

}
