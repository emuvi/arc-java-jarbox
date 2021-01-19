package pin.jarbox;

public class WzdChars {

  public static String sum(String... allStrings) {
    return sum(allStrings, null);
  }

  public static String sum(String[] allStrings, String withUnion) {
    if (allStrings == null) {
      return null;
    }
    String retorno = "";
    for (String osCaracteres : allStrings) {
      retorno = sum(retorno, withUnion, osCaracteres);
    }
    return retorno;
  }

  public static String sum(String theString, String withString) {
    return sum(theString, null, withString);
  }

  public static String sum(String theString, String withUnion, String andString) {
    String retorno = theString;
    if (andString != null) {
      if (!andString.isEmpty()) {
        if (retorno == null) {
          retorno = andString;
        } else if (retorno.isEmpty()) {
          retorno = andString;
        } else {
          retorno += (withUnion != null ? withUnion : "") + andString;
        }
      }
    }
    return retorno;
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
