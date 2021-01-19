package pin.jarbox;

public abstract class Progress {

  private volatile boolean paused;
  private volatile boolean stopped;

  public Progress() {
    paused = false;
    stopped = false;
  }

  public synchronized void pause() {
    paused = true;
  }

  public synchronized void resume() {
    paused = false;
  }

  public synchronized void stop() {
    stopped = true;
  }

  public synchronized boolean isPaused() {
    return paused;
  }

  public synchronized boolean isStopped() {
    return stopped;
  }

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
