package pin.jarbox;

public class WzdFile {

  public static String getBaseName(final String fileName) {
    if (fileName == null) {
      return null;
    }
    final int dot = fileName.lastIndexOf(".");
    if (dot > -1) {
      return fileName.substring(0, dot);
    } else {
      return fileName;
    }
  }

  public static String getExtension(final String fileName) {
    if (fileName == null) {
      return null;
    }
    final int dot = fileName.lastIndexOf(".");
    if (dot > -1) {
      return fileName.substring(dot);
    } else {
      return "";
    }
  }
  
}
