package pin.jarbox;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class WzdData {
  
  public static String clean(String theString) {
    if (theString == null)
      return "";
    return theString.replace("\"", "\"\"");
  }

  public static String formatDate(Date theDate) {
    if (theDate == null)
      return "";
    return DATE_FORMAT.format(theDate);
  }

  public static String formatTime(Time theTime) {
    if (theTime == null)
      return "";
    return TIME_FORMAT.format(theTime);
  }

  public static String formatTimestamp(Timestamp theTimestamp) {
    if (theTimestamp == null)
      return "";
    return TIMESTAMP_FORMAT.format(theTimestamp);
  }

  public static String encodeBase64(byte[] theBytes) {
    return Base64.getEncoder().encodeToString(theBytes);
  }

  public static String getClassOfSQL(int jdbcType) {
    switch (jdbcType) {
      case 16:
        return "Boolean";
      case -7:
      case -6:
      case 5:
      case 4:
        return "Integer";
      case -5:
        return "Long";
      case 6:
      case 7:
        return "Float";
      case 8:
      case 2:
      case 3:
        return "Double";
      case 1:
      case -15:
        return "Character";
      case 12:
      case -1:
      case -9:
      case -16:
        return "String";
      case 91:
        return "Date";
      case 92:
      case 2013:
        return "Time";
      case 93:
      case 2014:
        return "Timestamp";
      case -2:
      case -3:
      case -4:
      case 2004:
      case 2005:
      case 2011:
      case 2009:
        return "Byte";
      default:
        return "";
    }
  }

  public static final SimpleDateFormat DATE_FORMAT =
    new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat TIME_FORMAT =
    new SimpleDateFormat("HH:mm:ss");
  public static final SimpleDateFormat TIMESTAMP_FORMAT =
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

}
