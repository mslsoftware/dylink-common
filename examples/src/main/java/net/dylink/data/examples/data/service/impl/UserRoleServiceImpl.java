package net.dylink.data.examples.data.service.impl;

import cn.net.vidyo.dylink.data.jpa.sql.QueryWhere;
import cn.net.vidyo.dylink.data.service.EntityConditionServiceImpl;
import net.dylink.data.examples.data.dao.UserRoleDao;
import net.dylink.data.examples.data.model.UserRole;
import net.dylink.data.examples.data.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserRoleServiceImpl extends EntityConditionServiceImpl<UserRole,UserRoleDao, UserRole,Integer> implements UserRoleService {
    @Resource
    UserRoleDao userRoleDao;
    @Override
    protected UserRoleDao getEntityDao() {
        return userRoleDao;
    }

    @Override
    public QueryWhere buildWhere(UserRole userRole) {
        return null;
    }
}
