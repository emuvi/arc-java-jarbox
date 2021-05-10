package pin.jarbox.wzd;

import java.util.Objects;

public class WzdArray {

  @SuppressWarnings("all")
  public static <T> Boolean has(T value, T... onArray) {
    if (onArray != null) {
      for (Object daMatriz : onArray) {
        if (Objects.equals(value, daMatriz)) {
          return true;
        }
      }
    }
    return false;
  }

  public static Boolean has(char value, char... onArray) {
    if (onArray != null) {
      for (char daMatriz : onArray) {
        if (daMatriz == value) {
          return true;
        }
      }
    }
    return false;
  }

  public static Boolean has(int value, int... onArray) {
    if (onArray != null) {
      for (int daMatriz : onArray) {
        if (daMatriz == value) {
          return true;
        }
      }
    }
    return false;
  }

}
