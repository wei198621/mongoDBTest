package com.tiza.leo.springBootMongo;

import com.mongodb.Mongo;
import com.tiza.leo.Application;
import com.tiza.leo.springBootMongo.entity.Order;
import com.tiza.leo.springBootMongo.entity.Person;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author leowei
 * @date 2020/11/19  - 0:22
 */

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class mongoTemplate02Test {


    @Autowired
    private MongoTemplate temp;


    @Test
    public void test09(){
        Person person = temp.findById("1111111111",Person.class);
        System.out.println(person.getOrders());
    }
    // 根据主键id查询      --------------ok
    @Test
    public void test08(){
        Person person = temp.findById("1111111111",Person.class);
        if(person !=null) {
            System.out.println(person.getOrders());
        }else{
            System.out.println("====person is null======");
        }
    }
    // 测试有级联关系的两个对象
    @Test
    public void test06(){
        Person p = new Person("1111111111","haha",23,1111);
        List<Order> list =new ArrayList<Order>();
        for(int i = 0 ; i < 3; i++){
            ObjectId objectId  = new ObjectId(new Date());
            Order o = new Order(objectId, 111111+"_"+i, new Date());
            temp.save(o);
            list.add(o);
        }
        p.setOrders(list);
        temp.save(p);
    }
    // 删除数据
    @Test
    public void test05(){
        Person s = new Person();
        s.setId(1+"");
        temp.remove(s);
    }
    // 查询person表中的所有数据
    @Test
    public void test03(){
        List<Person> list = temp.findAll(Person.class);
        for (Person s : list) {
            System.out.println(s);
        }
    }
    // 添加数据----------------ok
    @Test
    public void test02() {
		/*for(int i = 0 ;  i< 5;i++){
			Student s = new Student(10+i+"", "zhangsan"+i, 12*i, 2000+i);
			temp.save(s);
		}*/
        // 主键如果存在，进行更新
		/*Student s = new Student("2", "zhangsasssssn", 111, 2000);
		temp.save(s);*/
        /**
         * insert方法：如果key存在，报错
         */
        Person s = new Person("2", "aa", 111, 2000);
        temp.insert(s);
    }
 /*   // 查询所有的库
    @Test
    public void test01() {
        Mongo mongo = ac.getBean("mongo1",Mongo.class);
        List<String> list = mongo.getDatabaseNames();
        for (String dbName : list) {
            System.out.println(dbName);
        }
    }*/



}