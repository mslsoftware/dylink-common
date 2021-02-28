package net.dylink.data.examples;

import net.dylink.data.examples.data.dao.UserDao;
import net.dylink.data.examples.data.model.User;
import net.dylink.data.examples.data.model.UserCondition;
import net.dylink.data.examples.data.model.UserRole;
import net.dylink.data.examples.data.service.UserRoleService;
import net.dylink.data.examples.data.service.UserService;
import org.jsmth.faker.FakerName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonJpaRepositoryTest {
    @Resource
    UserService userService;
    @Resource
    UserRoleService userRoleService;

    @Test
    public void test(){
//        userService.deleteAll();
//        userService.truncateParmeryKey();
//        insert();

        UserCondition userCondition=new UserCondition();
        userCondition.setName("马");
        List<User> users = userService.query(userCondition);
        if(users.size()>0){
            User user = users.get(0);
            user.setMoney(293);
            userService.save(user);

            userCondition.setId(user.getId());
        }
        users = userService.query(userCondition);

        List<User> byAge = userService.findByAge(1, 5);
        userService.deleteById(byAge.get(0).getId());

        System.out.println(users);
        //userService.deleteAll(users);
    }
    @Test
    public void testQuery(){
        UserDao userDao = userService.getRepositoryDao();
        List<Integer> age = userDao.queryColumn(Integer.class, "age", "id<?", 10);
        System.out.println(age);
        List<String> names = userDao.queryColumn(String.class, "name", "id>5 and id<18");
        System.out.println(names);
    }

    @Test
    public void testQuery2(){
        UserRole userRole = userRoleService.getById(1);
        System.out.println(userRole);
    }
    @Test
    public void insert(){
        for(int index=0;index<100;index++){
            User user=new User();
            user.setAge(1+index);
            user.setStatus(1);
            user.setName(FakerName.name());
            userService.save(user);

        }
    }
}
