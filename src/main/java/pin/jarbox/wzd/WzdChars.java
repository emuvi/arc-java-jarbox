package pin.jarbox.wzd;

public class WzdChars {

  public static String LINE_SEPARATOR = "\r\n";

  public static boolean isEmpty(String theString) {
    return theString == null || theString.isEmpty();
  }

  public static boolean isNotEmpty(String theString) {
    return theString != null && !theString.isEmpty();
  }

  public static String firstNonEmpty(String... ofStrings) {
    if (ofStrings == null) {
      return "";
    }
    for (String chars : ofStrings) {
      if (isNotEmpty(chars)) {
        return chars;
      }
    }
    return "";
  }

  public static String sum(String withUnion, String... allStrings) {
    return sum(withUnion, null, allStrings);
  }

  public static String sum(String withUnion, StringBuilder andBuilder,
      String... allStrings) {
    if (allStrings == null) {
      return null;
    }
    if (withUnion == null) {
      withUnion = "";
    }
    var atLeastOne = false;
    var result = andBuilder != null ? andBuilder : new StringBuilder();
    for (String chars : allStrings) {
      if (isNotEmpty(chars)) {
        if (atLeastOne) {
          result.append(withUnion);
        } else {
          atLeastOne = true;
        }
        result.append(chars);
      }
    }
    return result.toString();
  }

  public static String insertSpaceInUppers(String fromString) {
    if (fromString == null) {
      return null;
    }
    String result = fromString;
    for (int i = 'A'; i <= 'Z'; i++) {
      result = result.replace(((char) i) + "", " " + ((char) i));
    }
    return result.trim();
  }

  public static boolean isFirstUpper(String inString) {
    if (inString == null) {
      return false;
    }
    if (inString.length() > 0) {
      String first = inString.substring(0, 1);
      return first.toUpperCase().equals(first);
    } else {
      return false;
    }
  }

  public static String toUpperOnlyFirstChar(String inString) {
    StringBuilder result = new StringBuilder();
    if (inString.length() > 0) {
      result.append(inString.substring(0, 1).toUpperCase());
    }
    if (inString.length() > 1) {
      result.append(inString.substring(1).toLowerCase());
    }
    return result.toString();
  }

  public static String toUpperFirstChar(String inString) {
    StringBuilder result = new StringBuilder();
    if (inString.length() > 0) {
      result.append(inString.substring(0, 1).toUpperCase());
    }
    if (inString.length() > 1) {
      result.append(inString.substring(1));
    }
    return result.toString();
  }

  public static String getFromDoubleQuotes(String inString) {
    if (inString == null) {
      return inString;
    }
    if (inString.length() >= 2) {
      if (inString.charAt(0) == '"' && inString.charAt(inString.length() - 1) == '"') {
        return inString.substring(1, inString.length() - 1);
      } else {
        return inString;
      }
    } else {
      return inString;
    }
  }

  public static Number getNumber(String ofString) {
    if (ofString == null) {
      return null;
    }
    if (ofString.contains(".")) {
      return Double.parseDouble(ofString);
    } else {
      return Integer.parseInt(ofString);
    }
  }

  public static String fill(char withChar, int untilLength) {
    return fill(null, withChar, untilLength, true);
  }

  public static String fill(String theString, char withChar, int untilLength) {
    return fill(theString, withChar, untilLength, false);
  }

  public static String fillAtStart(String theString, char withChar, int untilLength) {
    return fill(theString, withChar, untilLength, true);
  }

  public static String fill(String theString, char withChar, int untilLength,
      boolean atStart) {
    StringBuilder result = new StringBuilder();
    int diference = untilLength - (theString != null ? theString.length() : 0);
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

  public static boolean contains(String inString, Character... anyCharacter) {
    if (isNotEmpty(inString) && anyCharacter != null && anyCharacter.length > 0) {
      for (int i = 0; i < inString.length(); i++) {
        if (WzdArray.has(inString.charAt(i), anyCharacter)) {
          return true;
        }
      }
    }
    return false;
  }

  public static String replaceAll(String source, String[] from, String[] to) {
    if (from == null || source == null || source.isEmpty()) {
      return source;
    }
    for (int i = 0; i < from.length; i++) {
      var newValue = to == null || i >= to.length ? "" : to[i];
      source = source.replace(from[i], newValue);
    }
    return source;
  }

  public static String replaceControlFlow(String inString) {
    if (isEmpty(inString)) {
      return inString;
    }
    inString = inString.replace("\\", "\\\\");
    inString = inString.replace("\r", "\\r");
    inString = inString.replace("\n", "\\n");
    inString = inString.replace("\t", "\\t");
    inString = inString.replace("\f", "\\f");
    inString = inString.replace("\b", "\\b");
    return inString;
  }

  public static String remakeControlFlow(String inString) {
    if (isEmpty(inString)) {
      return inString;
    }
    inString = inString.replace("\\b", "\b");
    inString = inString.replace("\\f", "\f");
    inString = inString.replace("\\t", "\t");
    inString = inString.replace("\\n", "\n");
    inString = inString.replace("\\r", "\r");
    inString = inString.replace("\\\\", "\\");
    return inString;
  }
}
