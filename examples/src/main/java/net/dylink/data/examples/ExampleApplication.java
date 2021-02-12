package net.dylink.data.examples;

import cn.net.vidyo.dylink.data.jpa.CommonRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"net.dylink.data.examples"},
        repositoryFactoryBeanClass = CommonRepositoryFactoryBean.class//指定自己的工厂类
)
@SpringBootApplication
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }
}

