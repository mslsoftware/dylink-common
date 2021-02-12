package net.dylink.data.examples.data.service.impl;

import cn.net.vidyo.dylink.data.jpa.sql.QueryWhere;
import cn.net.vidyo.dylink.data.jpa.sql.SqlLike;
import cn.net.vidyo.dylink.data.jpa.sql.SqlThan;
import cn.net.vidyo.dylink.data.service.EntityConditionServiceImpl;
import cn.net.vidyo.dylink.data.service.EntityServiceImpl;
import net.dylink.data.examples.data.dao.UserDao;
import net.dylink.data.examples.data.model.User;
import net.dylink.data.examples.data.model.UserCondition;
import net.dylink.data.examples.data.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends EntityConditionServiceImpl<UserCondition,UserDao, User,Integer> implements UserService {
    @Resource
    UserDao userDao;
    @Override
    protected UserDao getEntityDao() {
        return userDao;
    }

    @Override
    public QueryWhere buildWhere(UserCondition condition) {
        QueryWhere sql= new QueryWhere();
        if(condition.getId()>0){
            sql.addIdWhere(condition.getId());
        }
        if(condition.getName()!=null && condition.getName().length()>0){
            sql.addLikeWhere("name",condition.getName(), SqlLike.LIKE_KEYWORD);
        }
        return sql;
    }
}
