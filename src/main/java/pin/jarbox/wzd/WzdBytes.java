package pin.jarbox.wzd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Base64;

public class WzdBytes {

  public static byte[] get(Object fromValue) throws Exception {
    if (fromValue instanceof byte[]) {
      return (byte[]) fromValue;
    } else if (fromValue instanceof Byte[]) {
      return (byte[]) fromValue;
    } else if (fromValue instanceof Serializable) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(((Serializable) fromValue));
      oos.flush();
      return bos.toByteArray();
    }
    throw new Exception("Could not convert this value to a bytes value.");
  }
  
  public static String encodeToBase64(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  public static byte[] decodeFromBase64(String formatted) {
    return Base64.getDecoder().decode(formatted);
  }

  public static String encodeToHex(byte[] bytes) {
    StringBuilder hexString = new StringBuilder(2 * bytes.length);
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(0xff & bytes[i]);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

  public static String checkSHA256(File file) throws Exception {
    return checkSHA256(Files.readAllBytes(file.toPath()));
  }

  public static String checkSHA256(byte[] bytes) throws Exception {
    return encodeToHex(MessageDigest.getInstance("SHA-256").digest(bytes));
  }
  
}
