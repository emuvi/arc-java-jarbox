package pin.jarbox;

import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class EditLocal extends Edit<Local> {

  private static final long serialVersionUID = -8551248222048610162L;

  private final EditInteger x = new EditInteger();
  private final EditInteger y = new EditInteger();

  private final JButton pasteButton = new JButton();

  private final JPanel field = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));

  public EditLocal() {
    super();
    pasteButton.setIcon(new ImageIcon(Icons.getPaste()));
    pasteButton.addActionListener(event -> paste());
    field.add(Utils.wrap(x, "X:"));
    field.add(Utils.wrap(y, "Y:"));
    field.add(Utils.wrap(pasteButton, " "));
    addMain(field);
  }

  public void paste() {
    try {
      String pasted = Utils.getStringFromClipboard();
      if (pasted != null) {
        String[] parts = pasted.split("\\,");
        x.setValue(Integer.parseInt(parts[0]));
        y.setValue(Integer.parseInt(parts[1]));
      }
    } catch (Exception e) {
      Utils.treat(e);
    }
  }

  @Override
  public Local getValue() {
    return new Local(x.getValue(), y.getValue());
  }

  @Override
  public void setValue(Local value) {
    if (value == null) {
      x.setValue(null);
      y.setValue(null);
    } else {
      x.setValue(value.x);
      y.setValue(value.y);
    }
  }

}
