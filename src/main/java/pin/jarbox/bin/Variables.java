package pin.jarbox.bin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Variables {

  private final Map<String, Object> map = new HashMap<>();

  public synchronized Object get(String name) throws Exception {
    if (name.startsWith("$") || name.startsWith("@")) {
      name = name.substring(1);
    }
    if (!name.contains(".")) {
      return map.get(name);
    } else {
      String parts[] = name.split("\\.");
      Object path = map.get(parts[0]);
      for (int i = 1; i < parts.length && path != null; i++) {
        String part = parts[i];
        Field field = path.getClass().getField(part);
        path = field.get(path);
      }
      return path;
    }
  }

  public synchronized void set(String name, Object value) throws Exception {
    if (name.startsWith("@") || name.startsWith("$")) {
      name = name.substring(1);
    }
    if (!name.contains(".")) {
      map.put(name, value);
    } else {
      String parts[] = name.split("\\.");
      Object path = map.get(parts[0]);
      Field field = null;
      for (int i = 1; i < parts.length -1 && path != null; i++) {
        String part = parts[i];
        field = path.getClass().getField(part);
        path = field.get(path);
      }
      String part = parts[parts.length -1];
      field = path.getClass().getField(part);
      field.set(path, value);
    }
  }

  public Object evaluate(String expression) throws Exception {
    if (expression == null) {
      return expression;
    }
    if (expression.startsWith("$")) {
      return get(expression);
    } else if (expression.startsWith("=")) {
      return new Evaluate(this, expression).get();
    } else {
      return expression;
    }
  }

  public Object evaluateNotNull(String expression) throws Exception {
    Object result = evaluate(expression);
    if (result == null) {
      throw new Exception("The evaluated value can not be null.");
    }
    return result;
  }

}
