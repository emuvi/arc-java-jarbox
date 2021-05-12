package pin.jarbox.dat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.File;
import org.junit.jupiter.api.Test;

public class CSVFileTest {
  @Test
	public void testWriteReadAppendRead() throws Exception {
    var testFile = new File("test.csv");
    try (var csvFile = new CSVFile(testFile, FileMode.WRITE)) {
      csvFile.writeLine("Col1", "Col2");
      csvFile.writeLine("Col 1", "Col 2");
      csvFile.writeLine("Col 1,2", "Col 2,1");
      csvFile.writeLine("Col \"1\"", "Col 2,3");
      csvFile.writeLine("Col 1\nBreak", "Col 2\tTab");
    }
    try (var csvFile = new CSVFile(testFile, FileMode.READ)) {
      var lin1 = csvFile.readLine();
      assertEquals("Col1", lin1.get(0));
      assertEquals("Col2", lin1.get(1));
      var lin2 = csvFile.readLine();
      assertEquals("Col 1", lin2.get(0));
      assertEquals("Col 2", lin2.get(1));
      var lin3 = csvFile.readLine();
      assertEquals("Col 1,2", lin3.get(0));
      assertEquals("Col 2,1", lin3.get(1));
      var lin4 = csvFile.readLine();
      assertEquals("Col \"1\"", lin4.get(0));
      assertEquals("Col 2,3", lin4.get(1));
      var lin5 = csvFile.readLine();
      assertEquals("Col 1\nBreak", lin5.get(0));
      assertEquals("Col 2\tTab", lin5.get(1));
      var lin6 = csvFile.readLine();
      assertNull(lin6);
    }
    try (var csvFile = new CSVFile(testFile, FileMode.APPEND)) {
      csvFile.writeLine("New Col 1\nBreak", "New Col 2\tTab");
    }
    try (var csvFile = new CSVFile(testFile, FileMode.READ)) {
      var lin1 = csvFile.readLine();
      assertEquals("Col1", lin1.get(0));
      assertEquals("Col2", lin1.get(1));
      var lin2 = csvFile.readLine();
      assertEquals("Col 1", lin2.get(0));
      assertEquals("Col 2", lin2.get(1));
      var lin3 = csvFile.readLine();
      assertEquals("Col 1,2", lin3.get(0));
      assertEquals("Col 2,1", lin3.get(1));
      var lin4 = csvFile.readLine();
      assertEquals("Col \"1\"", lin4.get(0));
      assertEquals("Col 2,3", lin4.get(1));
      var lin5 = csvFile.readLine();
      assertEquals("Col 1\nBreak", lin5.get(0));
      assertEquals("Col 2\tTab", lin5.get(1));
      var lin6 = csvFile.readLine();
      assertEquals("New Col 1\nBreak", lin6.get(0));
      assertEquals("New Col 2\tTab", lin6.get(1));
      var lin7 = csvFile.readLine();
      assertNull(lin7);
    }
    testFile.delete();
	}
}
