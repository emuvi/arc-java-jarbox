package pin.jarbox;

public interface Messenger {

  public void handle(String message);

  default public void handle(Exception error) {
    handle(Utils.getDescription(error));
  }
}
