package pin.jarbox.wzd;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WzdDate {

  public static String formatDate(Date date) {
    if (date == null)
      return "";
    return DATE_FORMAT.format(date);
  }

  public static String formatTime(Date date) {
    if (date == null)
      return "";
    return TIME_FORMAT.format(date);
  }

  public static String formatTimeMillis(Date date) {
    if (date == null)
      return "";
    return TIME_MILLIS_FORMAT.format(date);
  }

  public static String formatTimestamp(Date date) {
    if (date == null)
      return "";
    return TIMESTAMP_FORMAT.format(date);
  }

  public static String formatMomment(Date date) {
    if (date == null)
      return "";
    return MOMMENT_FORMAT.format(date);
  }

  public static String formatTimeFile(Date date) {
    if (date == null)
      return "";
    return TIME_FILE_FORMAT.format(date);
  }

  public static String formatTimeMillisFile(Date date) {
    if (date == null)
      return "";
    return TIME_MILLIS_FILE_FORMAT.format(date);
  }

  public static String formatTimestampFile(Date date) {
    if (date == null)
      return "";
    return TIMESTAMP_FILE_FORMAT.format(date);
  }

  public static String formatMommentFile(Date date) {
    if (date == null)
      return "";
    return MOMMENT_FILE_FORMAT.format(date);
  }

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
  public static final SimpleDateFormat TIME_MILLIS_FORMAT = new SimpleDateFormat(
      "HH:mm:ss.ZZZ");
  public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");
  public static final SimpleDateFormat MOMMENT_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss.ZZZ");

  public static final SimpleDateFormat TIME_FILE_FORMAT = new SimpleDateFormat(
      "HH-mm-ss");
  public static final SimpleDateFormat TIME_MILLIS_FILE_FORMAT = new SimpleDateFormat(
      "HH-mm-ss.ZZZ");
  public static final SimpleDateFormat TIMESTAMP_FILE_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd-HH-mm-ss");
  public static final SimpleDateFormat MOMMENT_FILE_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd-HH-mm-ss.ZZZ");

}
