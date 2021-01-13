package pin.jarbox;

public class WzdString {

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

}
