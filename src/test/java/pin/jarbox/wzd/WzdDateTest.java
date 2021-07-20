package pin.jarbox.wzd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class WzdDateTest {

  @Test
  public void is() {
    assertEquals(true, WzdDate.is("2021-12-10", WzdDate.DATE_FORMAT));
    assertEquals(true, WzdDate.is("12:12:12", WzdDate.TIME_FORMAT));
    assertEquals(true, WzdDate.is("2021-12-10 12:12:12", WzdDate.TIMESTAMP_FORMAT));
  }

}
