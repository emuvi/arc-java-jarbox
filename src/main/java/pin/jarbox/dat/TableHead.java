package pin.jarbox.dat;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import pin.jarbox.wzd.WzdChars;
import pin.jarbox.wzd.WzdData;

public class TableHead {

    public String catalog;
    public String schema;
    public String name;

    public TableHead() {
        this(null, null, null);
    }

    public TableHead(String catalog) {
        this(catalog, null, null);
    }

    public TableHead(String catalog, String schema) {
        this(catalog, schema, null);
    }

    public TableHead(String catalog, String schema, String name) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
    }

    public String getSchemaName() {
        return WzdChars.sum(".", schema, name);
    }

    public String getCatalogSchemaName() {
        return WzdChars.sum(".", catalog, schema, name);
    }

    public String getNameForFile() {
        return WzdChars.sum(".", catalog, schema, name);
    }

    public Table getTable(Connection connection) throws Exception {
        Table result = new Table(this, new ArrayList<>(), new ArrayList<>());
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet set = meta.getPrimaryKeys(this.catalog, this.schema, this.name);
        while (set.next()) {
            result.keys.add(set.getString(4));
        }
        ResultSet rst = meta.getColumns(this.catalog, this.schema, this.name, "%");
        while (rst.next()) {
            TableField campo = new TableField();
            campo.name = rst.getString(4);
            campo.nature = WzdData.getNatureOfSQL(rst.getInt(5));
            campo.size = rst.getInt(7);
            campo.precision = rst.getInt(9);
            campo.notNull = "NO".equals(rst.getString(18));
            campo.key = false;
            if (result.keys.contains(campo.name)) {
                campo.key = true;
            }
            result.fields.add(campo);
        }
        return result;
    }

}
