package demo.service;

import cn.net.vidyo.dylink.data.service.EntityService;
import demo.dao.UserDao;
import demo.domain.User;

public interface UserService extends EntityService<UserDao, User,Integer> {
}
