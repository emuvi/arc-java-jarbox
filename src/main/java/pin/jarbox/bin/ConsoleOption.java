package pin.jarbox.bin;

import java.util.Objects;
import pin.jarbox.wzd.WzdChars;

public class ConsoleOption {

  private final boolean flag;
  private final Character shortName;
  private final String longName;
  private final String defaultValue;
  private final String description;

  public ConsoleOption(boolean flag, Character shortName, String longName,
      String description) {
    this(flag, shortName, longName, (String) null, description);
  }

  public ConsoleOption(boolean flag, Character shortName, String longName,
      Boolean defaultValue, String description) {
    this(flag, shortName, longName, defaultValue == null ? null
        : String.valueOf(defaultValue), description);
  }

  public ConsoleOption(boolean flag, Character shortName, String longName,
      Integer defaultValue, String description) {
    this(flag, shortName, longName, defaultValue == null ? null
        : String.valueOf(defaultValue), description);
  }

  public ConsoleOption(boolean flag, Character shortName, String longName,
      Double defaultValue, String description) {
    this(flag, shortName, longName, defaultValue == null ? null
        : String.valueOf(defaultValue), description);
  }

  public ConsoleOption(boolean flag, Character shortName, String longName,
      String defaultValue, String description) {
    this.flag = flag;
    this.shortName = shortName;
    this.longName = longName;
    this.defaultValue = defaultValue;
    this.description = description;
  }

  public boolean isFlag() {
    return this.flag;
  }

  public Character getShortName() {
    return this.shortName;
  }

  public String getLongName() {
    return this.longName;
  }

  public String getDefaultValue() {
    return this.defaultValue;
  }

  public String getDescription() {
    return this.description;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof ConsoleOption)) {
      return false;
    }
    ConsoleOption consoleOption = (ConsoleOption) o;
    return flag == consoleOption.flag && Objects.equals(shortName,
        consoleOption.shortName) && Objects.equals(longName, consoleOption.longName)
        && Objects.equals(defaultValue, consoleOption.defaultValue) && Objects.equals(
            description, consoleOption.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flag, shortName, longName, defaultValue, description);
  }

  @Override
  public String toString() {
    return "{" + " flag='" + isFlag() + "'" + ", shortName='" + getShortName() + "'"
        + ", longName='" + getLongName() + "'" + ", defaultValue='" + getDefaultValue()
        + "'" + ", description='" + getDescription() + "'" + "}";
  }

  public String printNames() {
    if (shortName != null && WzdChars.isNotEmpty(longName)) {
      return "-" + shortName + ", --" + longName;
    } else if (shortName != null) {
      return "-" + shortName;
    } else if (WzdChars.isNotEmpty(longName)) {
      return "--" + longName;
    } else {
      return "";
    }
  }

  public String printDefault() {
    if (WzdChars.isNotEmpty(defaultValue)) {
      return " [" + defaultValue + "]";
    }
    return "";
  }

  public Boolean wasFlagged() {
    if (!flag) {
      return false;
    }
    var result = Console.hasArgument(shortName, longName);
    if (Objects.equals(defaultValue, String.valueOf(true))) {
      result = !result;
    }
    return result;
  }

  public String getString() {
    return Console.getArgumentValue(shortName, longName, defaultValue);
  }

  public Boolean getBoolean() {
    return Boolean.parseBoolean(getString());
  }

  public Integer getInteger() {
    return Integer.parseInt(getString());
  }

  public Double getDouble() {
    return Double.parseDouble(getString());
  }

}
