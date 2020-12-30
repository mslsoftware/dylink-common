package cn.net.vidyo.dylink.builder.domain;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSchema {
    List<TableSchema> tables=new ArrayList<>();

    public void addTable(TableSchema tableSchema){
        tables.add(tableSchema);
    }
    public List<TableSchema> getTables() {
        return tables;
    }

    public void setTables(List<TableSchema> tables) {
        this.tables = tables;
    }
}
