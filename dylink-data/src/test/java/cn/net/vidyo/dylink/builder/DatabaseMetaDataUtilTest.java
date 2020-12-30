package cn.net.vidyo.dylink.builder;

import cn.net.vidyo.dylink.builder.domain.DatabaseSchema;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseMetaDataUtilTest {

    @Test
    public void test(){
        String url="jdbc:mysql://admin.vidyo.net.cn:33060/teaching_server?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false&autoReconnect=true&failOverReadOnly=false";
        String username="admin";
        String password="vidyo@2012";
        DatabaseSchema databaseSchema = DatabaseMetaDataUtil.queryDatabaseMetaData(url, username, password);
        System.out.println(databaseSchema);
    }
}