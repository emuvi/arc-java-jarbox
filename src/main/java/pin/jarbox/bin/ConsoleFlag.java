package pin.jarbox.bin;

public class ConsoleFlag extends ConsoleOption {

  public ConsoleFlag(Character shortName, String longName, String description) {
    super(true, shortName, longName, false, description);
  }

  public ConsoleFlag(Character shortName, String longName, Boolean defaultValue,
      String description) {
    super(true, shortName, longName, defaultValue, description);
  }

}
