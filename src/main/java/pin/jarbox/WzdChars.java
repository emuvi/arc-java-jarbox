package pin.jarbox;

public class WzdChars {

  public static String firstNonEmpty(String... ofStrings) {
    if (ofStrings == null) {
      return "";
    }
    for (String chars : ofStrings) {
      if (chars != null && !chars.isEmpty()) {
        return chars;
      }
    }
    return "";
  }

  public static String sum(String withUnion, String... allStrings) {
    if (allStrings == null) {
      return null;
    }
    if (withUnion == null) {
      withUnion = "";
    }
    boolean atLeastOne = false;
    StringBuilder result = new StringBuilder();
    for (String chars : allStrings) {
      if (chars != null && !chars.isEmpty()) {
        if (atLeastOne) {
          result.append(withUnion);
        }
        result.append(chars);
        atLeastOne = true;
      }
    }
    return result.toString();
  }

  public static String spaceUppers(final String string) {
    if (string == null) {
      return null;
    }
    String result = string;
    for (int i = 'A'; i <= 'Z'; i++) {
      result = result.replace(((char)i) + "", " " + ((char)i));
    }
    return result.trim();
  }

  public static boolean isFirstUpper(final String string) {
    if (string == null) {
      return false;
    }
    if (string.length() > 0) {
      final String first = string.substring(0, 1);
      return first.toUpperCase().equals(first);
    } else {
      return false;
    }
  }

  public static String firstUpper(final String string) {
    StringBuilder result = new StringBuilder();
    if (string.length() > 0) {
      result.append(string.substring(0, 1).toUpperCase());
    }
    if (string.length() > 1) {
      result.append(string.substring(1).toLowerCase());
    }
    return result.toString();
  }

  public static String upperFirst(final String string) {
    StringBuilder result = new StringBuilder();
    if (string.length() > 0) {
      result.append(string.substring(0, 1).toUpperCase());
    }
    if (string.length() > 1) {
      result.append(string.substring(1));
    }
    return result.toString();
  }

  public static String getFromDoubleQuotes(final String string) {
    if (string == null) {
      return string;
    }
    if (string.length() >= 2) {
      if (string.charAt(0) == '"' &&
          string.charAt(string.length() - 1) == '"') {
        return string.substring(1, string.length() - 1);
      } else {
        return string;
      }
    } else {
      return string;
    }
  }

  public static Number getNumber(final String string) {
    if (string == null) {
      return null;
    }
    if (string.contains(".")) {
      return Double.parseDouble(string);
    } else {
      return Integer.parseInt(string);
    }
  }

  public static String fill(final char withChar, final int untilLength) {
    return fill(null, withChar, untilLength, true);
  }

  public static String fill(final String theString, final char withChar,
      final int untilLength, final boolean atStart) {
    StringBuilder result = new StringBuilder();
    final int diference =
      untilLength - (theString != null ? theString.length() : 0);
    if (!atStart && theString != null) {
      result.append(theString);
    }
    for (int i = 0; i < diference; i++) {
      result.append(withChar);
    }
    if (atStart && theString != null) {
      result.append(theString);
    }
    return result.toString();
  }

  public static final String LINE_SEPARATOR = "\r\n";

}
