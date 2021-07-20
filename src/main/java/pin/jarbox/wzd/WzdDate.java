package pin.jarbox.wzd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class WzdDate {

  public static Date get(Object fromValue) throws Exception {
    if (fromValue == null) {
      return null;
    }
    var clazz = fromValue.getClass();
    if (Date.class.isAssignableFrom(clazz)) {
      return (Date) fromValue;
    } else if (fromValue instanceof java.sql.Date) {
      return new Date(((java.sql.Date) fromValue).getTime());
    } else if (fromValue instanceof java.sql.Time) {
      return new Date(((java.sql.Time) fromValue).getTime());
    } else if (fromValue instanceof java.sql.Timestamp) {
      return new Date(((java.sql.Timestamp) fromValue).getTime());
    } else if (fromValue instanceof String) {
      var string = (String) fromValue;
      for (SimpleDateFormat format : FORMATS) {
        if (is(string, format)) {
          return format.parse(string);
        }
      }
    }
    throw new Exception("Could not convert this value to a date value.");
  }

  public static boolean is(String str, SimpleDateFormat inFormat) {
    return Objects.equals(WzdChars.getNonLettersAndNonDigits(str), WzdChars
        .getNonLettersAndNonDigits(inFormat.toPattern()));
  }

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

  public static String formatMoment(Date date) {
    if (date == null)
      return "";
    return MOMENT_FORMAT.format(date);
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

  public static String formatMomentFile(Date date) {
    if (date == null)
      return "";
    return MOMENT_FILE_FORMAT.format(date);
  }

  public static Date parseDate(String formatted) throws Exception {
    if (formatted == null || formatted.isEmpty())
      return null;
    return DATE_FORMAT.parse(formatted);
  }

  public static Date parseTime(String formatted) throws Exception {
    if (formatted == null || formatted.isEmpty())
      return null;
    return TIME_FORMAT.parse(formatted);
  }

  public static Date parseTimeMillis(String formatted) throws Exception {
    if (formatted == null || formatted.isEmpty())
      return null;
    return TIME_MILLIS_FORMAT.parse(formatted);
  }

  public static Date parseTimestamp(String formatted) throws Exception {
    if (formatted == null || formatted.isEmpty())
      return null;
    return TIMESTAMP_FORMAT.parse(formatted);
  }

  public static Date parseMoment(String formatted) throws Exception {
    if (formatted == null || formatted.isEmpty())
      return null;
    return MOMENT_FORMAT.parse(formatted);
  }

  public static Date parseTimeFile(String formatted) throws Exception {
    if (formatted == null || formatted.isEmpty())
      return null;
    return TIME_FILE_FORMAT.parse(formatted);
  }

  public static Date parseTimeMillisFile(String formatted) throws Exception {
    if (formatted == null || formatted.isEmpty())
      return null;
    return TIME_MILLIS_FILE_FORMAT.parse(formatted);
  }

  public static Date parseTimestampFile(String formatted) throws Exception {
    if (formatted == null || formatted.isEmpty())
      return null;
    return TIMESTAMP_FILE_FORMAT.parse(formatted);
  }

  public static Date parseMomentFile(String formatted) throws Exception {
    if (formatted == null || formatted.isEmpty())
      return null;
    return MOMENT_FILE_FORMAT.parse(formatted);
  }

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
  public static final SimpleDateFormat TIME_MILLIS_FORMAT = new SimpleDateFormat(
      "HH:mm:ss.ZZZ");
  public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");
  public static final SimpleDateFormat MOMENT_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss.ZZZ");

  public static final SimpleDateFormat TIME_FILE_FORMAT = new SimpleDateFormat(
      "HH-mm-ss");
  public static final SimpleDateFormat TIME_MILLIS_FILE_FORMAT = new SimpleDateFormat(
      "HH-mm-ss.ZZZ");
  public static final SimpleDateFormat TIMESTAMP_FILE_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd-HH-mm-ss");
  public static final SimpleDateFormat MOMENT_FILE_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd-HH-mm-ss.ZZZ");

  public static final SimpleDateFormat[] FORMATS = {DATE_FORMAT, TIME_FORMAT,
      TIME_MILLIS_FORMAT, TIMESTAMP_FORMAT, MOMENT_FORMAT, TIME_FILE_FORMAT,
      TIME_MILLIS_FILE_FORMAT, MOMENT_FILE_FORMAT};

}
