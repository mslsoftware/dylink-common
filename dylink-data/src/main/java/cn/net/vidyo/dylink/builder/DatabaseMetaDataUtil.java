package cn.net.vidyo.dylink.builder;

import cn.net.vidyo.dylink.builder.domain.ColumnSchema;
import cn.net.vidyo.dylink.builder.domain.DatabaseSchema;
import cn.net.vidyo.dylink.builder.domain.TableSchema;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMetaDataUtil {
    public static DatabaseSchema queryDatabaseMetaData(String url, String userName, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, userName, password);
            DatabaseMetaData dbMetaData = con.getMetaData();
            return convertToSchema(dbMetaData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static DatabaseSchema convertToSchema(DatabaseMetaData dbMetaData) {
        DatabaseSchema databaseSchema = new DatabaseSchema();
        try {
            System.out.println("数据库已知的用户: " + dbMetaData.getUserName());
            System.out.println("数据库的系统函数的逗号分隔列表: " + dbMetaData.getSystemFunctions());
            System.out.println("数据库的时间和日期函数的逗号分隔列表: " + dbMetaData.getTimeDateFunctions());
            System.out.println("数据库的字符串函数的逗号分隔列表: " + dbMetaData.getStringFunctions());
            System.out.println("数据库供应商用于 'schema' 的首选术语: " + dbMetaData.getSchemaTerm());
            System.out.println("数据库URL: " + dbMetaData.getURL());
            System.out.println("是否允许只读:" + dbMetaData.isReadOnly());
            System.out.println("数据库的产品名称:" + dbMetaData.getDatabaseProductName());
            System.out.println("数据库的版本:" + dbMetaData.getDatabaseProductVersion());
            System.out.println("驱动程序的名称:" + dbMetaData.getDriverName());
            System.out.println("驱动程序的版本:" + dbMetaData.getDriverVersion());

            System.out.println();

            ResultSet rs = dbMetaData.getSchemas();
            List<String> schemas = new ArrayList<>();
            //String tableSchem = rs.getString("TABLE_SCHEM");
            schemas.add("null");
//            while (rs.next()) {
//
//            }
            rs.close();
            for (String schema : schemas) {
                convertToDatabaseSchema(dbMetaData, databaseSchema, schema);
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return databaseSchema;
    }

    static void convertToDatabaseSchema(DatabaseMetaData dbMetaData, DatabaseSchema databaseSchema, String schemaName) {
        System.out.println("获取所有表");
        String[] types = {"TABLE"};
        try {
            ResultSet rs = dbMetaData.getTables(null, schemaName, "%", types);
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME"); // 表名
                String tableType = rs.getString("TABLE_TYPE"); // 表类型
                String schem = rs.getString("TABLE_SCHEM"); // 表类型

                String remarks = rs.getString("REMARKS"); // 表备注
                System.out.println("处理表"+tableName);
                TableSchema tableSchema = new TableSchema();
                tableSchema.setTableName(tableName);
                tableSchema.setDesception(remarks);
                tableSchema.setSchemaName(schemaName);
                convertToTableSchema(dbMetaData,tableSchema);
                databaseSchema.addTable(tableSchema);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }

    static void convertToTableSchema(DatabaseMetaData dbMetaData, TableSchema tableSchema) {
        try {
            ResultSet rs = dbMetaData.getColumns(null, tableSchema.getSchemaName(), tableSchema.getTableName(), "%");
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");// 列名
                String dataTypeName = rs.getString("TYPE_NAME");// java.sql.Types类型名称
                int columnSize = rs.getInt("COLUMN_SIZE");// 列大小
                ColumnSchema columnSchema = new ColumnSchema();
                columnSchema.setColumnName(columnName);
                columnSchema.setDataTypeName(dataTypeName);
                columnSchema.setLength(columnSize);
                columnSchema.convert();
                tableSchema.addColumn(columnSchema);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
