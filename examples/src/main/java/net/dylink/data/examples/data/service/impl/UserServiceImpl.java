package net.dylink.data.examples.data.service.impl;

import cn.net.vidyo.dylink.data.service.EntityServiceImpl;
import net.dylink.data.examples.data.dao.UserDao;
import net.dylink.data.examples.data.model.User;
import net.dylink.data.examples.data.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends EntityServiceImpl<UserDao, User,Integer> implements UserService {
    @Resource
    UserDao userDao;
    @Override
    protected UserDao getEntityDao() {
        return userDao;
    }
}
