package pin.jarbox;

public abstract class Progress {

  private final String title;

  public Progress(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public abstract void pause();

  public abstract void resume();

  public abstract void stop();

  public abstract boolean isPaused();

  public abstract boolean isStopped();

  public abstract void setSize(int size);

  public abstract void setWalked(int walked);

  public abstract void log(String message);

  public void log(Exception error) {
    log(WzdLog.getDescription(error));
  }

  public boolean waitIfPausedAndCheckStopped() throws Exception {
    while (isPaused()) {
      Thread.sleep(10);
    }
    return isStopped();
  }

  public void waitIfPausedAndThrowIfStopped() throws Exception {
    while (isPaused()) {
      Thread.sleep(10);
    }
    if (isStopped()) {
      throw new Exception("Process was stopped.");
    }
  }

}
