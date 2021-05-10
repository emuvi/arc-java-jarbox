package pin.jarbox.dsk;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import pin.jarbox.bin.Progress;
import pin.jarbox.wzd.WzdDesk;

public class ProgressDesk extends Progress {

  private final Helm helm;

  private volatile boolean paused = false;
  private volatile boolean stopped = false;

  private final JTextArea textArea = new JTextArea(30, 5);
  private final JProgressBar barProgress = new JProgressBar();
  private final JButton btnPause = new JButton("Pause");
  private final JButton btnStop = new JButton("Stop");
  private final Panel rowActions = new RowPanel().add(btnPause).add(btnStop).close();
  private final Panel mainPanel = new ColPanel().addMax(textArea).addCross(barProgress)
      .add(rowActions);

  public ProgressDesk(String title) {
    this(title, true);
  }

  public ProgressDesk(String title, boolean show) {
    super(title);
    textArea.setEditable(false);
    textArea.setOpaque(false);
    textArea.setBackground(new Color(0, 0, 0, 0));
    textArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    helm = new Helm(new JFrame("Progress " + getTitle()), mainPanel);
    helm.pack();
    helm.setSize(450, 210);
    if (show) {
      helm.show();
    }
  }

  public Helm helm() {
    return helm;
  }

  @Override
  public synchronized void pause() {
    paused = true;
  }

  @Override
  public synchronized void resume() {
    paused = false;
  }

  @Override
  public synchronized void stop() {
    stopped = true;
  }

  @Override
  public synchronized boolean isPaused() {
    return paused;
  }

  @Override
  public synchronized boolean isStopped() {
    return stopped;
  }

  @Override
  public void setSize(int size) {
    WzdDesk.callOrInvoke(() -> {
      barProgress.setMaximum(size);
    });
  }

  @Override
  public void setWalked(int walked) {
    WzdDesk.callOrInvoke(() -> {
      barProgress.setValue(walked);
    });
  }

  @Override
  public void disablePause() {
    WzdDesk.callOrInvoke(() -> {
      rowActions.del(btnPause);
      if (rowActions.isEmpty()) {
        mainPanel.del(rowActions);
      }
    });
  }

  @Override
  public void disableStop() {
    WzdDesk.callOrInvoke(() -> {
      rowActions.del(btnStop);
      if (rowActions.isEmpty()) {
        mainPanel.del(rowActions);
      }
    });
  }

  @Override
  public void disableWalker() {
    WzdDesk.callOrInvoke(() -> {
      mainPanel.del(barProgress);
    });
  }

  public synchronized void log(String message) {
    System.out.println(message);
    WzdDesk.callOrInvoke(() -> {
      textArea.setText(message);
    });
  }

}
