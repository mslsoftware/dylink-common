package net.dylink.data.examples.data.dao;

import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import net.dylink.data.examples.data.model.User;

public interface UserDao extends CommonJpaRepository<User,Integer> {
}
