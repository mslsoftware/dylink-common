package net.dylink.data.examples.data.service;

import cn.net.vidyo.dylink.data.service.EntityConditionService;
import cn.net.vidyo.dylink.data.service.EntityService;
import net.dylink.data.examples.data.dao.UserDao;
import net.dylink.data.examples.data.model.User;
import net.dylink.data.examples.data.model.UserCondition;

public interface UserService extends EntityConditionService<UserCondition, UserDao, User,Integer> {

}
