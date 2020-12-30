package demo.service.impl;

import cn.net.vidyo.dylink.data.service.EntityServiceImpl;
import demo.dao.UserDao;
import demo.domain.User;
import demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends EntityServiceImpl<UserDao, User,Integer> implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    protected UserDao getEntityDao() {
        return userDao;
    }
}
