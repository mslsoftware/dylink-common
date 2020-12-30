package cn.net.vidyo.dylink.builder.domain;

import java.util.ArrayList;
import java.util.List;

public class TableSchema {
    String schemaName="";
    String tableName="";
    String desception="";
    List<ColumnSchema> columns=new ArrayList<>();
    ColumnSchema IdColumn=null;


    public void addColumn(ColumnSchema columnSchema){
        columns.add(columnSchema);
        if(columnSchema.getColumnName()=="id"){
            IdColumn=columnSchema;
        }
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getDesception() {
        return desception;
    }

    public void setDesception(String desception) {
        this.desception = desception;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnSchema> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnSchema> columns) {
        this.columns = columns;
    }
}
