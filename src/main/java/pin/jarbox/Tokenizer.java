package pin.jarbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {

  private final String expression;

  private static final List<String> KEYWORDS = Arrays.asList("\\", "true", "false", "=",
      "!", ":=", "==", "!=", ">", "<", ">=", "<=", "&&", "||", "?", "+", "++", "+=",
      "-", "--", "-=", "*", "**", "*=", "/", "/=", "%", "%=");

  public Tokenizer(String expression) {
    this.expression = expression;
  }

  private void addToken(StringBuilder builder, List<Token> list, int start) {
    String token = builder.toString().trim();
    if (!token.isEmpty()) {
      Token.Type type = Token.Type.UNKNOWN;
      if (KEYWORDS.contains(token)) {
        type = Token.Type.KEYWORD;
      } else if (token.startsWith("$") || token.startsWith("@")) {
        type = Token.Type.VARIABLE;
      } else if (token.equals("(") || token.equals(")")) {
        type = Token.Type.BLOCK;
      } else if (token.startsWith("\"")) {
        type = Token.Type.STRING;
      } else if (Character.isDigit(token.charAt(0))) {
        type = Token.Type.NUMBER;
      }
      list.add(new Token(type, token, start));
    }
    builder.delete(0, builder.length());
  }

  public List<Token> get() {
    List<Token> result = new ArrayList<>();
    boolean open = false;
    StringBuilder builder = new StringBuilder();
    int start = 0;
    for (int i = 0; i < expression.length(); i++) {
      char before = i > 0 ? expression.charAt(i - 1) : (char) 0;
      char actual = expression.charAt(i);
      builder.append(actual);
      if (!open && actual == ' ') {
        addToken(builder, result, start);
        start = i + 1;
      } else if (actual == '"' && before != '\\') {
        if (open) {
          addToken(builder, result, start);
          start = i + 1;
        }
        open = !open;
      }
    }
    addToken(builder, result, start);
    return result;
  }

}
