package net.dylink.data.examples.data.service;

import cn.net.vidyo.dylink.data.service.EntityConditionService;
import cn.net.vidyo.dylink.data.service.EntityService;
import net.dylink.data.examples.data.dao.UserDao;
import net.dylink.data.examples.data.model.User;
import net.dylink.data.examples.data.model.UserCondition;

import java.util.List;

public interface UserService extends EntityConditionService<UserCondition, UserDao, User,Integer> {

    List<User> findByAge(int minAge,int maxAge);
}
