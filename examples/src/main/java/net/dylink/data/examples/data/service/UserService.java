package net.dylink.data.examples.data.service;

import cn.net.vidyo.dylink.data.service.EntityService;
import net.dylink.data.examples.data.dao.UserDao;
import net.dylink.data.examples.data.model.User;

public interface UserService extends EntityService<UserDao, User,Integer> {
}
