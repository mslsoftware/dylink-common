package net.dylink.data.examples;

import net.dylink.data.examples.data.model.User;
import net.dylink.data.examples.data.model.UserCondition;
import net.dylink.data.examples.data.service.UserService;
import org.jsmth.faker.FakerName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonJpaRepositoryTest {
    @Resource
    UserService userService;

    @Test
    public void test(){
//        userService.deleteAll();
//        userService.truncateParmeryKey();
//        insert();

        UserCondition userCondition=new UserCondition();
        userCondition.setName("马");
        List<User> query = userService.query(userCondition);
        System.out.println(query);
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
