package demo.dao;

import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import cn.net.vidyo.dylink.data.jpa.ICondition;
import demo.domain.User;

import java.util.List;

public interface UserDao extends CommonJpaRepository<User,Integer> {

    List<User> findByCondition(ICondition<User> condition);
}
