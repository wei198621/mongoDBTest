package com.tiza.leo.springBootMongo;

import com.tiza.leo.Application;
import com.tiza.leo.springBootMongo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;


/**
 * @author leowei
 * @date 2020/11/18  - 23:45
 */

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class mongoTemplateTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setAge(18);
        user.setBir(new Date());
        user.setName("leo");

        mongoTemplate.save(user);


    }

}