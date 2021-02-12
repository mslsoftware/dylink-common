package net.dylink.data.examples;

import net.dylink.data.examples.data.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonJpaRepositoryTest {
    @Resource
    UserService userService;

    @Test
    public void test(){
        System.out.println(userService);
    }
}
