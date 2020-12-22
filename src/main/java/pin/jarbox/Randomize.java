package pin.jarbox;

import java.util.Random;

public class Randomize {

  private final Random random;

  public Randomize() { random = new Random(); }

  public Randomize(long seed) { random = new Random(seed); }

  public void getBytes(byte[] bytes) { random.nextBytes(bytes); }

  public boolean getBoolean() { return random.nextBoolean(); }

  public boolean getBoolean(int probability) {
    return getInteger(100) < probability;
  }

  public int getDigit() { return random.nextInt(10); }

  public int getInteger() { return random.nextInt(); }

  public int getInteger(int max) { return random.nextInt(max); }

  public int getInteger(int min, int max) {
    return min + random.nextInt(max - min);
  }

  public Long getLong() { return random.nextLong(); }

  public char getSimpleChar() {
    switch (getInteger(3)) {
      case 0:
        return (char)getInteger((int)'0', (int)'9');
      case 1:
        return (char)getInteger((int)'A', (int)'Z');
      default:
        return (char)getInteger((int)'a', (int)'z');
    }
  }

  public String getString(int size) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < size; i++) {
      result.append(getSimpleChar());
    }
    return result.toString();
  }

  public Float getFloat() { return random.nextFloat(); }

  public Double getDouble() { return random.nextDouble(); }

  public Double getGaussian() { return random.nextGaussian(); }
}
