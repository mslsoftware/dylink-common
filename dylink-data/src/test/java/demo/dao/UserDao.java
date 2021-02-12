package demo.dao;

import cn.net.vidyo.dylink.data.jpa.CommonJpaRepository;
import cn.net.vidyo.dylink.data.domain.Condition;
import demo.domain.User;

import java.util.List;

public interface UserDao extends CommonJpaRepository<User,Integer> {


}
