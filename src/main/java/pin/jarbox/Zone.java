package pin.jarbox;

import java.io.Serializable;

public class Zone implements Serializable {

  private static final long serialVersionUID = -4016530353318761124L;

  public Integer x;
  public Integer y;
  public Integer width;
  public Integer height;

  public Zone() {
    this.x = null;
    this.y = null;
    this.width = null;
    this.height = null;
  }

  public Zone(Integer x, Integer y, Integer width, Integer height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }


  @Override
  public String toString() {
    return "{x='" + x + "'" + ", y='" + y + "'" + ", width='" + width + "'"
      + ", height='" + height + "'}";
  }

}
