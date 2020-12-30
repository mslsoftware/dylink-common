package demo.logic;

import cn.net.vidyo.dylink.common.CommonEnumCodes;
import cn.net.vidyo.dylink.common.Result;
import demo.dao.UserDao;
import demo.domain.User;
import demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userLogic")
public class UserLogic {
    @Resource
    UserService userService;
    @Resource
    UserDao userDao;

    public Result get(int id){
        User user = userDao.getById(id);
        if(user==null){
            return Result.fail(CommonEnumCodes.NOT_EXIST);
        }
        return Result.ok(user);
    }
//    public Result query(User user){
//        userService.
//    }
    public Result add(User user){
        if(user==null){
            return Result.fail(CommonEnumCodes.IS_EMPT);
        }
        return userService.add(user);
    }
    public Result delete(int id){
        try {
            userDao.deleteById(id);
        }catch (Exception ex){
            return Result.fail(CommonEnumCodes.DB_ACCESS_EXCEPTION);
        }
        return Result.ok();
    }
    public Result edit(User user){
        if(user==null){
            return Result.fail(CommonEnumCodes.IS_EMPT);
        }
        return userService.update(user);
    }

}
