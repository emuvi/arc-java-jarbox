package pin.jarbox.dat;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import pin.jarbox.wzd.WzdChars;

public class CSVFile implements Closeable {

  public static enum Mode {
    READ, WRITE, APPEND
  };

  private final File file;
  private final Mode mode;
  private final PrintWriter writer;

  public CSVFile(File file, Mode mode) throws Exception {
    this.file = file;
    this.mode = mode;
    if (mode == Mode.READ) {
      writer = null;
    } else {
      writer = new PrintWriter(new FileOutputStream(file, mode == Mode.APPEND), true);
    }
  }

  public File getFile() {
    return this.file;
  }

  public Mode getMode() {
    return this.mode;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof CSVFile)) {
      return false;
    }
    CSVFile cSVFile = (CSVFile) o;
    return Objects.equals(file, cSVFile.file) && Objects.equals(mode, cSVFile.mode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(file, mode);
  }

  @Override
  public String toString() {
    return "{" + " file='" + getFile() + "'" + ", mode='" + getMode() + "'" + "}";
  }

  public synchronized String[] readLine() {
    return null;
  }

  public synchronized void writeLine(String[] columns) {
    for (int i = 0; i < columns.length; i++) {
      String column = columns[i];
      if (WzdChars.contains(column, '"', ' ', ',')) {
        columns[i] = '"' + column.replace("\"", "\"\"") + '"';
      }
    }
    writer.write(WzdChars.sum(",", columns) + WzdChars.LINE_SEPARATOR);
  }

  @Override
  public void close() throws IOException {
    if (writer != null) {
      writer.close();
    }
  }

}
