package pin.jarbox.wzd;

import java.util.Arrays;
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
  
  @SuppressWarnings("all")
  public static <T> T[] insert(int index, T value, T... onArray) {
    if (onArray == null) {
      return null;
    }
    T[] result = Arrays.copyOf(onArray, Math.max(onArray.length + 1, index + 1));
    result[index] = value;
    for (int i = index; i < onArray.length; i++) {
      result[i + 1] = onArray[i];
    }
    return result;
  }

}
