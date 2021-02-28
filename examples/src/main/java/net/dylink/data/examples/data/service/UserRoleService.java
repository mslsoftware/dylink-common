package net.dylink.data.examples.data.service;

import cn.net.vidyo.dylink.data.service.EntityConditionService;
import net.dylink.data.examples.data.dao.UserDao;
import net.dylink.data.examples.data.dao.UserRoleDao;
import net.dylink.data.examples.data.model.User;
import net.dylink.data.examples.data.model.UserCondition;
import net.dylink.data.examples.data.model.UserRole;

import java.util.List;

public interface UserRoleService extends EntityConditionService<UserRole, UserRoleDao, UserRole,Integer> {


}
