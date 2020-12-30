package cn.net.vidyo.dylink.data.domain;

import cn.net.vidyo.dylink.data.jpa.SqlEQ;

public interface Condition {
    String getName();
    SqlEQ getOperator();
    Object getValue();

}
