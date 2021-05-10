package pin.jarbox.val;

import java.io.Serializable;

public class Local implements Serializable {

  private static final long serialVersionUID = 1284589748663738635L;

  public Integer x;
  public Integer y;


  public Local() {
    this.x = null;
    this.y = null;
  }


  public Local(Integer x, Integer y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "{x='" + x + "'" + ", y='" + y + "'}";
  }

}
