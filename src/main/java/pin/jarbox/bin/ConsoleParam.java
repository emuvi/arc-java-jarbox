package pin.jarbox.bin;

public class ConsoleParam extends ConsoleOption {

  public ConsoleParam(Character shortName, String longName, String description) {
    super(false, shortName, longName, (String) null, description);
  }

  public ConsoleParam(Character shortName, String longName, Integer defaultValue,
      String description) {
    super(false, shortName, longName, defaultValue, description);
  }

  public ConsoleParam(Character shortName, String longName, Double defaultValue,
      String description) {
    super(false, shortName, longName, defaultValue, description);
  }

  public ConsoleParam(Character shortName, String longName, String defaultValue,
      String description) {
    super(false, shortName, longName, defaultValue, description);
  }

}
