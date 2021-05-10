package pin.jarbox.val;

import java.io.Serializable;
import pin.jarbox.wzd.WzdChars;

public class Pass implements Serializable {

  private static final long serialVersionUID = -8071782074686371832L;

  public byte[] value;

  public Pass() {
    this.value = null;
  }

  public Pass(byte[] value) {
    this.value = value;
  }

  public String getPassword() {
    return value != null ? new String(value) : null;
  }

  @Override
  public String toString() {
    return WzdChars.fill('*', value != null ? value.length : 0);
  }

}
