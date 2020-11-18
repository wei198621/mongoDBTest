package com.tiza.leo.springBootMongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.tiza.leo.Application;
import com.tiza.leo.springBootMongo.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * @author leowei
 * @date 2020/11/19  - 0:22
 */

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class mongoTemplate03Test {
    @Autowired
    private MongoTemplate temp;


    @Test
    public void testAddBatch(){
        for(int i = 0 ; i<10;i++){
            String id = UUID.randomUUID().toString().replace("-", "");
            Person p = new Person(id, "zhangsan"+i, 10+i*2, 10000+i*2);
            temp.save(p);
        }
    }

    @Test  //   排序
    public void testOrder(){
        Query query = new 	Query();
        query.addCriteria(new Criteria().where("age").gt(10));
        query.limit(10);
        query.skip(2);
        // 按照salary的升序排列
//		query.with(new Sort(Direction.ASC,"salary"));
        // 按照salary的降序排列
        query.with(new Sort(Sort.Direction.DESC, "salary"));
        List<Person> list = temp.find(query, Person.class);

        bianli(list);
    }
    @Test
    public void testLimitSkip(){
        Query query = new 	Query();
        query.addCriteria(new Criteria().where("age").gt(10));
        query.limit(10);
        query.skip(2);
        List<Person> list = temp.find(query, Person.class);
        bianli(list);
    }
    @Test  // 查询与数组相关的信息
    public void testQuery03(){
        Query query = new 	Query();
        Pattern pattern = Pattern.compile("^.*1.*$", Pattern.CASE_INSENSITIVE);

        query.addCriteria(Criteria.where("name").regex(pattern));
        List<Person> list = temp.find(query, Person.class);
        bianli(list);
    }
    @Test  // 查询与数组相关的信息--------------ok
    public void testQuery02(){
        Query query = new 	Query();
        // 查询  age数组长度为4的数据
//		query.addCriteria(Criteria.where("age").size(4));
        // 查询 age数组第三位 为 "aa"的记录
//		query.addCriteria(Criteria.where("age.2").is("aa"));
        List<Person> list = temp.find(query, Person.class);
        bianli(list);
    }
    @Test
    public void testQuery01(){
        Query query = new 	Query();
        // 查询  age =18
        //query.addCriteria(new Criteria().where("age").is(18));
        //查询 age > 12 并且  age< 20
        //query.addCriteria(new Criteria().andOperator(new Criteria("age").gt(12),new Criteria("age").lt(20)));
        //查询  age =1  或者 age = 100
//		query.addCriteria(new Criteria().orOperator(new Criteria("age").is(1),new Criteria("age").is(1000)));
        // 查询  age  in  (1,2)
//		query.addCriteria(new Criteria().where("age").in(1,2));

        // 查询  age值  除以5  余数为2的数据
        query.addCriteria(new Criteria("age").mod(5,2));
        List<Person> list = temp.find(query, Person.class);
        bianli(list);
    }
    public void bianli(List<Person> list){
        for (Object o : list) {
            System.out.println(o);
        }
    }
    @Test
    public void testUpdate04(){
        Query query = new 	Query();
        query.addCriteria(new Criteria().where("key1").is("hello 1"));
        Update update = new Update();
        update.set("key1", "value1");
        update.pushAll("age",new Object[]{12,"aa",13});
        temp.updateFirst(query,update, Person.class);
    }
 /*   @Test
    public void testUpdate03(){
        Query query = new 	Query();
        query.addCriteria(new Criteria("age").gt(26).lt(30));
        // 用新元素替换旧元素
        DBObject obj = new BasicDBObject("key1", "value1");
        Update update = new BasicUpdate(obj);
        temp.updateFirst(query,update, Person.class);
    }
    @Test
    public void testUpdate02(){
        Query query = new 	Query();
        query.addCriteria(new Criteria("age").gt(26).lt(30));
        // 用新元素替换旧元素
        DBObject obj = new BasicDBObject("key1", "value1");
        Update update = new org.springframework.data.mongodb.core.query.BasicUpdate(obj);
        temp.updateFirst(query,update, Person.class);
    }*/
    @Test
    public void testUpdate01(){
        Query query = new 	Query();
        query.addCriteria(new Criteria("age").gt(26).lt(30));
        // 在原始旧数据的基础上进行修改
        Update update = new Update();
        update.set("name","helloe zhangsan"); //根据key，对对应的值进行更新
        update.inc("salary", 3);  // 将该key对于的值进行递增
        update.pull("optKey",new String[]{"optionValue1","optionValue2"});//只对数组有效。 将新数据压入到key对应的数组中
        update.pop("optKey", Update.Position.FIRST); // 只能对数组有用。将数组特定位置的元素移除

        temp.updateFirst(query,update, Person.class);
    }
}