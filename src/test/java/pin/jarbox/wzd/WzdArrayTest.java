package pin.jarbox.wzd;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

public class WzdArrayTest {
  
  @Test
  public void insert() {
    var base = new String[]{"el1", "el2"};
    var result1 = WzdArray.insert(0, "elT", base);
    var should1 = new String[]{"elT", "el1", "el2"};
    assertArrayEquals(should1, result1);
    var result2 = WzdArray.insert(1, "elT", base);
    var should2 = new String[]{"el1", "elT", "el2"};
    assertArrayEquals(should2, result2);
    var result3 = WzdArray.insert(2, "elT", base);
    var should3 = new String[]{"el1", "el2", "elT"};
    assertArrayEquals(should3, result3);
    var result4 = WzdArray.insert(3, "elT", base);
    var should4 = new String[]{"el1", "el2", null, "elT"};
    assertArrayEquals(should4, result4);
  }
}
