package cn.net.vidyo.dylink.data.service;

import cn.net.vidyo.dylink.data.jpa.EntityDao;

import java.io.Serializable;


public interface DefaultEntityDao <T, ID extends Serializable> extends EntityDao<T,ID> {
}
