package cn.net.vidyo.dylink.builder.domain;

public class ColumnSchema {
    String columnName="";
    String desception="";
    private String dataTypeName;
    private Integer Length=0;
    public Class ColumnType;


    public void convert(){
//        System.out.println(dataTypeName);
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDesception() {
        return desception;
    }

    public void setDesception(String desception) {
        this.desception = desception;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public Integer getLength() {
        return Length;
    }

    public void setLength(Integer length) {
        Length = length;
    }
}
