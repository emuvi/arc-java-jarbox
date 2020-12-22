package pin.jarbox;

import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class Panel {

  public final JPanel panel;
  public final JScrollPane scroll;

  public Panel() { this(false); }

  public Panel(boolean scrollable) {
    panel = new JPanel();
    scroll = scrollable ? new JScrollPane(panel) : null;
  }

  public Panel(boolean scrollable, LayoutManager layout) {
    panel = new JPanel(layout);
    scroll = scrollable ? new JScrollPane(panel) : null;
  }

  public JComponent getMain() { return scroll != null ? scroll : panel; }

  public abstract Panel add(Component component);

  public abstract Panel addMax(Component component, int weight);

  public abstract Panel addMax(Component component);

  public abstract Panel close();

  public Panel add(Panel panel) {
    add(panel.getMain());
    return this;
  }

  public Panel addMax(Panel panel, int weight) {
    addMax(panel.getMain(), weight);
    return this;
  }

  public Panel addMax(Panel panel) {
    addMax(panel.getMain());
    return this;
  }
}
