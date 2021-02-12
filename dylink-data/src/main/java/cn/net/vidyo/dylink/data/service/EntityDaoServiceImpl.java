package cn.net.vidyo.dylink.data.service;

import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;

import java.io.Serializable;

public class EntityDaoServiceImpl<DAO extends CommonJpaRepository<T, ID>, T, ID extends Serializable> {
}
