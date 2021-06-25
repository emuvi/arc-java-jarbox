package pin.jarbox.dat;

import java.util.List;
import com.google.gson.Gson;
import pin.jarbox.wzd.WzdChars;

public class Table {

  public TableHead head;
  public List<TableField> fields;
  public List<String> keys;

  public Table() {
    this(null, null, null);
  }

  public Table(TableHead head) {
    this(head, null, null);
  }

  public Table(TableHead head, List<TableField> fields) {
    this(head, fields, null);
  }

  public Table(TableHead head, List<TableField> fields, List<String> keys) {
    this.head = head;
    this.fields = fields;
    this.keys = keys;
  }

  public String getSchemaName() {
    return head.getSchemaName();
  }

  public String getCatalogSchemaName() {
    return head.getCatalogSchemaName();
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Table fromString(String source) {
    return new Gson().fromJson(source, Table.class);
  }

}
