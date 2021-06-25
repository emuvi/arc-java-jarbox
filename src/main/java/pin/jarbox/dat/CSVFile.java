package pin.jarbox.dat;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import pin.jarbox.wzd.WzdChars;

public class CSVFile implements Closeable {

  private final File file;
  private final FileMode mode;
  private final BufferedReader reader;
  private final PrintWriter writer;

  public CSVFile(File file, FileMode mode) throws Exception {
    this.file = file;
    this.mode = mode;
    if (mode == FileMode.READ) {
      reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
      writer = null;
    } else {
      reader = null;
      writer = new PrintWriter(new FileOutputStream(file, mode == FileMode.APPEND), true,
          StandardCharsets.UTF_8);
    }
  }

  public File getFile() {
    return this.file;
  }

  public FileMode getMode() {
    return this.mode;
  }

  @Override
  public String toString() {
    return "{" + " file='" + getFile() + "'" + ", mode='" + getMode() + "'" + "}";
  }

  public synchronized String[] readLine() throws Exception {
    final var line = reader.readLine();
    if (line == null) {
      return null;
    }
    var result = new LinkedList<>();
    var openQuotes = false;
    var builder = new StringBuilder();
    for (int i = 0; i < line.length(); i++) {
      var actual = line.charAt(i);
      var next = i < line.length() - 1 ? line.charAt(i + 1) : ' ';
      if (openQuotes) {
        if (actual == '"') {
          if (next == '"') {
            builder.append('"');
            i++;
          } else {
            openQuotes = false;
          }
        } else {
          builder.append(actual);
        }
      } else {
        if (actual == '"') {
          openQuotes = true;
        } else if (actual == ',' || actual == ';') {
          result.add(WzdChars.remakeControlFlow(builder.toString()));
          builder = new StringBuilder();
        } else {
          builder.append(actual);
        }
      }
    }
    result.add(WzdChars.remakeControlFlow(builder.toString()));
    return result.toArray(new String[result.size()]);
  }

  public synchronized void writeLine(String... columns) {
    if (columns != null) {
      for (int i = 0; i < columns.length; i++) {
        var column = WzdChars.replaceControlFlow(columns[i]);
        if (WzdChars.contains(column, '"', ' ', ',', ';')) {
          column = '"' + column.replace("\"", "\"\"") + '"';
        }
        if (i > 0) {
          writer.write(",");
        }
        if (WzdChars.isNotEmpty(column)) {
          writer.write(column);
        }
      }
    }
    writer.write(System.lineSeparator());
  }

  @Override
  public void close() throws IOException {
    if (reader != null) {
      reader.close();
    }
    if (writer != null) {
      writer.close();
    }
  }

}
