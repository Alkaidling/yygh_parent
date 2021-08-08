package com.alkaid.yygh.hosp;


import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-07 18:57
 * @ClassName Test1
 * @Description:
 */
@SpringBootTest
public class Test1 {
    //注入MongoTemplate
    @Autowired
    private MongoTemplate mongoTemplate;

    //添加
    @Test
    public void create() {
        User user = new User();
        user.setAge(20);
        user.setName("test");
        user.setEmail("11111@111.111");

        User user1 = mongoTemplate.insert(user);

        System.out.println(user1);
    }
}
