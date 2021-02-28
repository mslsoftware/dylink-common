package net.dylink.data.examples.data.dao;

import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import net.dylink.data.examples.data.model.User;
import net.dylink.data.examples.data.model.UserRole;

public interface UserRoleDao extends CommonJpaRepository<UserRole,Integer> {
}
