package pin.jarbox.dat;

import com.google.gson.Gson;

public class TableField {

  public String name;
  public Nature nature;
  public Integer size;
  public Integer precision;
  public Boolean notNull;
  public Boolean key;

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public static Table fromString(String source) {
    return new Gson().fromJson(source, Table.class);
  }
  
}
