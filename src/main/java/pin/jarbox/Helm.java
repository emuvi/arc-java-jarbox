package pin.jarbox;

import java.awt.Dimension;
import java.awt.Taskbar;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class Helm {

  public final Window window;
  public final Panel container;

  public Helm(Window window, Panel container) {
    this.window = window;
    this.container = container;
    if (window instanceof JFrame jFrame) {
      jFrame.setContentPane(container.getMain());
    } else if (window instanceof JDialog jDialog) {
      jDialog.setContentPane(container.getMain());
    }
    container.panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 2, 2));
    putShortcut("Close", "ESCAPE", () -> close());
  }

  public void setExitOnClose() {
    if (window instanceof JFrame jFrame) {
      jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } else if (window instanceof JDialog jDialog) {
      jDialog.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
    }
  }

  public void setDisposeOnClose() {
    if (window instanceof JFrame jFrame) {
      jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    } else if (window instanceof JDialog jDialog) {
      jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
  }

  public void setHideOnClose() {
    if (window instanceof JFrame jFrame) {
      jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    } else if (window instanceof JDialog jDialog) {
      jDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
  }

  public void setCancelOnClose() {
    if (window instanceof JFrame jFrame) {
      jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    } else if (window instanceof JDialog jDialog) {
      jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }
  }

  public void setIcon(BufferedImage image) {
    window.setIconImage(image);
    boolean isMain = false;
    if (window instanceof JFrame jFrame) {
      isMain = jFrame.getDefaultCloseOperation() == JFrame.EXIT_ON_CLOSE;
    } else if (window instanceof JDialog jDialog) {
      isMain = jDialog.getDefaultCloseOperation() == JDialog.EXIT_ON_CLOSE;
    }
    if (isMain) {
      try {
        Taskbar.getTaskbar().setIconImage(image);
      } catch (Exception e) {
      }
    }
  }

  public void setDefaultButton(JButton button) {
    if (window instanceof JFrame jFrame) {
      jFrame.getRootPane().setDefaultButton(button);
    } else if (window instanceof JDialog jDialog) {
      jDialog.getRootPane().setDefaultButton(button);
    }
  }

  public void pack() {
    window.pack();
    Dimension size = container.panel.getPreferredSize();
    size.width += 45;
    size.height += 45;
    window.setSize(size);
    Utils.setNextLocationFor(window);
  }

  public void show() { window.setVisible(true); }

  public void close() {
    callWindowClosing();
    if (window instanceof JFrame jFrame) {
      switch (jFrame.getDefaultCloseOperation()) {
        case JFrame.DO_NOTHING_ON_CLOSE:
          break;
        case JFrame.HIDE_ON_CLOSE:
          jFrame.setVisible(false);
          callWindowClosed();
          break;
        case JFrame.DISPOSE_ON_CLOSE:
          jFrame.setVisible(false);
          jFrame.dispose();
          callWindowClosed();
          break;
        case JFrame.EXIT_ON_CLOSE:
          jFrame.setVisible(false);
          jFrame.dispose();
          callWindowClosed();
          System.exit(0);
          break;
      }
    } else if (window instanceof JDialog jDialog) {
      switch (jDialog.getDefaultCloseOperation()) {
        case JDialog.DO_NOTHING_ON_CLOSE:
          break;
        case JDialog.HIDE_ON_CLOSE:
          jDialog.setVisible(false);
          callWindowClosed();
          break;
        case JDialog.DISPOSE_ON_CLOSE:
          jDialog.setVisible(false);
          jDialog.dispose();
          callWindowClosed();
          break;
        case JDialog.EXIT_ON_CLOSE:
          jDialog.setVisible(false);
          jDialog.dispose();
          callWindowClosed();
          System.exit(0);
          break;
      }
    }
  }

  public void callWindowClosed() {
    window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSED));
  }

  public void callWindowClosing() {
    window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
  }

  public void putShortcut(String name, String keyStroke, Runnable runnable) {
    if (container != null) {
      Utils.putShortCut(container.getMain(), name, keyStroke, runnable);
    }
  }
}
