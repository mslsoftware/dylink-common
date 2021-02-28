package cn.net.vidyo.dylink.data.jpa;

import cn.net.vidyo.dylink.data.domain.IModel;

import java.io.Serializable;

public interface JdbcDao <ID extends Serializable, ENTITY extends IModel<ID>>{
}
