package pin.jarbox;

import java.io.Serializable;

public class Source implements Serializable {

  private static final long serialVersionUID = 6135700739150458449L;

  public String value;

  public Source() {
    this.value = null;
  }

  public Source(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

}
