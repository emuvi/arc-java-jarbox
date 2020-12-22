package pin.jarbox;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Evaluate {

  private final Variables variables;
  private final String expression;
  private final Deque<Object> stack = new LinkedList<>();

  public Evaluate(Variables variables, String expression) {
    this.variables = variables;
    this.expression = expression;
  }

  public Object get() throws Exception {
    List<String> tokens = getTokens();
    for (int i = tokens.size() - 1; i >= 0; i--) {
      String token = tokens.get(i);
      if (token.isEmpty() || token.equals("(") || token.equals(")")) {
        continue;
      }
      if (token.startsWith("@")) {
        stack.addFirst(token);
      } else if (token.startsWith("$")) {
        stack.addFirst(variables.get(token));
      } else if (token.startsWith("\"")) {
        stack.addFirst(Utils.getFromDoubleQuotes(token));
      } else if (Character.isDigit(token.charAt(0))) {
        stack.addFirst(Utils.getNumber(token));
      } else if (token.equals("\\")) {
        stack.pollFirst();
      } else if (token.equals("true")) {
        stack.addFirst(true);
      } else if (token.equals("false")) {
        stack.addFirst(false);
      } else if (token.equals("=")) {
        return stack.pollFirst();
      } else if (token.equals("!")) {
        processNot();
      } else if (token.equals(":=")) {
        processAssign();
      } else if (token.equals("==")) {
        processEquals();
      } else if (token.equals("!=")) {
        processNotEquals();
      } else if (token.equals(">")) {
        processBigger();
      } else if (token.equals("<")) {
        processSmaller();
      } else if (token.equals(">=")) {
        processBiggerOrEquals();
      } else if (token.equals("<=")) {
        processSmallerOrEquals();
      } else if (token.equals("&&")) {
        processAnd();
      } else if (token.equals("||")) {
        processOr();
      } else if (token.equals("?")) {
        processTernary();
      } else if (token.equals("+")) {
        processPlus();
      } else if (token.equals("++")) {
        processPlusPlus();
      } else if (token.equals("+=")) {
        processPlusAssign();
      } else if (token.equals("-")) {
        processMinus();
      } else if (token.equals("--")) {
        processMinusMinus();
      } else if (token.equals("-=")) {
        processMinusAssign();
      } else if (token.equals("*")) {
        processMultiply();
      } else if (token.equals("**")) {
        processMultiplySelf();
      } else if (token.equals("*=")) {
        processMultiplyAssign();
      } else if (token.equals("/")) {
        processDivide();
      } else if (token.equals("/=")) {
        processDivideAssign();
      } else if (token.equals("%")) {
        processRest();
      } else if (token.equals("%=")) {
        processRestAssign();
      } else {
        throw new Exception("Undefined token: '" + token + "'");
      }
    }
    return stack.pollFirst();
  }

  private void addToken(StringBuilder builder, List<String> list) {
    String token = builder.toString().trim();
    if (!token.isEmpty()) {
      list.add(token);
    }
    builder.delete(0, builder.length());
  }

  private List<String> getTokens() {
    List<String> result = new ArrayList<>();
    boolean open = false;
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < expression.length(); i++) {
      char before = i > 0 ? expression.charAt(i - 1) : (char) 0;
      char actual = expression.charAt(i);
      builder.append(actual);
      if (!open && actual == ' ') {
        addToken(builder, result);
      } else if (actual == '"' && before != '\\') {
        if (open) {
          addToken(builder, result);
        }
        open = !open;
      }
    }
    addToken(builder, result);
    return result;
  }

  private void processAssign() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    if (param1 instanceof String atName) {
      variables.set(atName, param2);
    } else {
      throw new Exception("You must define the variable name for assign.");
    }
  }

  private void processNot() throws Exception {
    Object param = stack.pollFirst();
    if (param instanceof Boolean paramBool) {
      stack.addFirst(!paramBool);
    } else {
      throw new Exception("Can only process 'not' operations over booleans.");
    }
  }

  private void processEquals() {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(checkEquals(param1, param2));
  }

  private void processNotEquals() {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(!checkEquals(param1, param2));
  }

  private void processBigger() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(checkBigger(param1, param2));
  }

  private void processSmaller() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(checkSmaller(param1, param2));
  }

  private void processBiggerOrEquals() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(checkEquals(param1, param2) || checkBigger(param1, param2));
  }

  private void processSmallerOrEquals() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(checkEquals(param1, param2) || checkSmaller(param1, param2));
  }

  private void processAnd() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    if (param1 instanceof Boolean param1Bool) {
      if (param2 instanceof Boolean param2Bool) {
        stack.addFirst(param1Bool && param2Bool);
      } else {
        throw new Exception("Can only process 'and' operations over booleans.");
      }
    } else {
      throw new Exception("Can only process 'and' operations over booleans.");
    }
  }

  private void processOr() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    if (param1 instanceof Boolean param1Bool) {
      if (param2 instanceof Boolean param2Bool) {
        stack.addFirst(param1Bool || param2Bool);
      } else {
        throw new Exception("Can only process 'or' operations over booleans.");
      }
    } else {
      throw new Exception("Can only process 'or' operations over booleans.");
    }
  }

  private void processTernary() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    Object param3 = stack.pollFirst();
    stack.addFirst(checkTernary(param1, param2, param3));
  }

  private void processPlus() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(makePlus(param1, param2));
  }

  private void processPlusPlus() throws Exception {
    Object param1 = stack.pollFirst();
    if (param1 instanceof String atName) {
      variables.set(atName, makePlus(variables.get(atName), 1));
    } else {
      throw new Exception(
          "You must define the variable name for make a plus plus.");
    }
  }

  private void processPlusAssign() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    if (param1 instanceof String atName) {
      variables.set(atName, makePlus(variables.get(atName), param2));
    } else {
      throw new Exception(
          "You must define the variable name for make a plus assign.");
    }
  }

  private void processMinus() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(makeMinus(param1, param2));
  }

  private void processMinusMinus() throws Exception {
    Object param1 = stack.pollFirst();
    if (param1 instanceof String atName) {
      variables.set(atName, makeMinus(variables.get(atName), 1));
    } else {
      throw new Exception(
          "You must define the variable name for make a minus minus.");
    }
  }

  private void processMinusAssign() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    if (param1 instanceof String atName) {
      variables.set(atName, makeMinus(variables.get(atName), param2));
    } else {
      throw new Exception(
          "You must define the variable name for make a minus assign.");
    }
  }

  private void processMultiply() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(makeMultiply(param1, param2));
  }

  private void processMultiplySelf() throws Exception {
    Object param1 = stack.pollFirst();
    if (param1 instanceof String atName) {
      Object value = variables.get(atName);
      variables.set(atName, makeMultiply(value, value));
    } else {
      throw new Exception(
          "You must define the variable name for make a multiply self.");
    }
  }

  private void processMultiplyAssign() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    if (param1 instanceof String atName) {
      variables.set(atName, makeMultiply(variables.get(atName), param2));
    } else {
      throw new Exception(
          "You must define the variable name for make a multiply assign.");
    }
  }

  private void processDivide() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(makeDivide(param1, param2));
  }

  private void processDivideAssign() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    if (param1 instanceof String atName) {
      variables.set(atName, makeDivide(variables.get(atName), param2));
    } else {
      throw new Exception(
          "You must define the variable name for make a divide assign.");
    }
  }

  private void processRest() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    stack.addFirst(makeRest(param1, param2));
  }

  private void processRestAssign() throws Exception {
    Object param1 = stack.pollFirst();
    Object param2 = stack.pollFirst();
    if (param1 instanceof String atName) {
      variables.set(atName, makeRest(variables.get(atName), param2));
    } else {
      throw new Exception(
          "You must define the variable name for make a rest assign.");
    }
  }

  private Boolean checkEquals(Object param1, Object param2) {
    return Objects.equals(param1, param2);
  }

  private Boolean checkBigger(Object param1, Object param2) throws Exception {
    if (param1 instanceof String param1String) {
      if (param2 instanceof String param2String) {
        return param1String.compareTo(param2String) > 0;
      } else {
        throw new Exception("Can not check bigger on different types.");
      }
    } else if (param1 instanceof Number param1Number) {
      if (param2 instanceof Number param2Number) {
        return param1Number.doubleValue() > param2Number.doubleValue();
      } else {
        throw new Exception("Can not check bigger on different types.");
      }
    } else {
      throw new Exception("Can only check bigger over strings or numbers.");
    }
  }

  private Boolean checkSmaller(Object param1, Object param2) throws Exception {
    if (param1 instanceof String param1String) {
      if (param2 instanceof String param2String) {
        return param1String.compareTo(param2String) < 0;
      } else {
        throw new Exception("Can not check smaller on different types.");
      }
    } else if (param1 instanceof Number param1Number) {
      if (param2 instanceof Number param2Number) {
        return param1Number.doubleValue() < param2Number.doubleValue();
      } else {
        throw new Exception("Can not check smaller on different types.");
      }
    } else {
      throw new Exception("Can only check smaller over strings or numbers.");
    }
  }

  private Object checkTernary(Object param1, Object param2, Object param3)
      throws Exception {
      if (param1 instanceof Boolean param1Boolean) {
        if (param1Boolean) {
          return param2;
        } else {
          return param3;
        }
      } else {
        throw new Exception(
            "You must pass a Boolean as first parameter of a ternary.");
      }
  }

  private Object makePlus(Object param1, Object param2) throws Exception {
    if (param1 instanceof String || param2 instanceof String) {
      return String.valueOf(param1) + String.valueOf(param2);
    } else {
      if (param1 instanceof Number param1Number) {
        if (param2 instanceof Number param2Number) {
          if (param1Number instanceof Double
              || param2Number instanceof Double) {
            return param1Number.doubleValue() + param2Number.doubleValue();
          } else {
            return param1Number.intValue() + param2Number.intValue();
          }
        } else {
          throw new Exception(
              "Could not make plus because parameter 2 isn't a number.");
        }
      } else {
        throw new Exception(
            "Could not make plus because parameter 1 isn't a number.");
      }
    }
  }

  private Object makeMinus(Object param1, Object param2) throws Exception {
    if (param1 instanceof Number param1Number) {
      if (param2 instanceof Number param2Number) {
        if (param1Number instanceof Double || param2Number instanceof Double) {
          return param1Number.doubleValue() - param2Number.doubleValue();
        } else {
          return param1Number.intValue() - param2Number.intValue();
        }
      } else {
        throw new Exception(
            "Could not make minus because parameter 2 isn't a number.");
      }
    } else {
      throw new Exception(
          "Could not make minus because parameter 1 isn't a number.");
    }
  }

  private Object makeMultiply(Object param1, Object param2) throws Exception {
    if (param1 instanceof Number param1Number) {
      if (param2 instanceof Number param2Number) {
        if (param1Number instanceof Double || param2Number instanceof Double) {
          return param1Number.doubleValue() * param2Number.doubleValue();
        } else {
          return param1Number.intValue() * param2Number.intValue();
        }
      } else {
        throw new Exception(
            "Could not make multiply because parameter 2 isn't a number.");
      }
    } else {
      throw new Exception(
          "Could not make multiply because parameter 1 isn't a number.");
    }
  }

  private Object makeDivide(Object param1, Object param2) throws Exception {
    if (param1 instanceof Number param1Number) {
      if (param2 instanceof Number param2Number) {
        if (param1Number instanceof Double || param2Number instanceof Double) {
          return param1Number.doubleValue() / param2Number.doubleValue();
        } else {
          return param1Number.intValue() / param2Number.intValue();
        }
      } else {
        throw new Exception(
            "Could not make divide because parameter 2 isn't a number.");
      }
    } else {
      throw new Exception(
          "Could not make divide because parameter 1 isn't a number.");
    }
  }

  private Object makeRest(Object param1, Object param2) throws Exception {
    if (param1 instanceof Number param1Number) {
      if (param2 instanceof Number param2Number) {
        if (param1Number instanceof Double || param2Number instanceof Double) {
          return param1Number.doubleValue() % param2Number.doubleValue();
        } else {
          return param1Number.intValue() % param2Number.intValue();
        }
      } else {
        throw new Exception(
            "Could not make rest because parameter 2 isn't a number.");
      }
    } else {
      throw new Exception(
          "Could not make rest because parameter 1 isn't a number.");
    }
  }

}
