package com.tiza.leo.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author leowei
 * @date 2020/11/18  - 0:16
 *
 * 20201118  手工敲打 经过测试
 */
public class TestMongo001 {

    private MongoClient mongoClient;


    @Before
    public void before(){
        mongoClient =new MongoClient("192.168.121.102",27017);
    }
   /* @After
    public void after(){
        mongoClient.close();
    }*/

    /**
     * 获取所有数据名称-----报错
     */
    @Test
    public void testDatabaseNames(){
        MongoIterable<String> listDatabaseNames = mongoClient.listDatabaseNames();
        for (String databaseName : listDatabaseNames) {
            System.out.println("databaseName = " + databaseName);
        }
    }


    /**
     * 获取所有集合名称-----报错
     */
    @Test
    public void testGetCollectionNames(){
        MongoDatabase ems = mongoClient.getDatabase("ems");
        MongoIterable<String> tables = ems.listCollectionNames();
        for (String name : tables) {
            System.out.println("name = " + name);
        }
    }



    @Test
    public void testGetCollection(){
        MongoDatabase ems = mongoClient.getDatabase("ems444");
        MongoCollection<Document> t_user = ems.getCollection("t_user444");
        System.out.println("t_user = " + t_user);
    }


    /**
     * 获取指定集合中的所有数据
     */
    @Test
    public void testGetCollectionFindAll(){
        MongoDatabase database = mongoClient.getDatabase("ems");
        MongoCollection<Document> collection = database.getCollection("t_user");
        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println("document = " + document);
            System.out.println("TestMongo001.testGetCollectionFindAll");
            System.out.print(document.get("name")+"===");
            System.out.print(document.get("age")+"====");
            System.out.println();
            System.out.println();
        }
    }
    /**
     * 保存一条数据
     */
    @Test
    public void testSave(){
        MongoDatabase database = mongoClient.getDatabase("ems");
        MongoCollection<Document> t_user = database.getCollection("t_user");
        Document document = new Document();
        document.put("_id","1");
        document.put("name","xiaochen");
        document.put("age",23);
        document.put("bir",new Date());
        t_user.insertOne(document);
    }

    /**
     * 保存多条数据
     */
    @Test
    public void testSaveMany(){
        MongoDatabase database = mongoClient.getDatabase("ems");
        MongoCollection<Document> t_user = database.getCollection("t_user");
        ArrayList<Document> list = new ArrayList<Document>();
        Document document = new Document();
        document.put("_id","3");
        document.put("name","XIAOBAI");
        document.put("age",23);
        document.put("bir",new Date());
        Document document1 = new Document();
        document1.put("_id","2");
        document1.put("name","XIAOBAI");
        document1.put("age",23);
        document1.put("bir",new Date());
        list.add(document);
        list.add(document1);
        t_user.insertMany(list);
    }

    /**
     * 删除一条数据
     */
    @Test
    public void testDelete(){
        MongoDatabase database = mongoClient.getDatabase("ems");
        MongoCollection<Document> t_user = database.getCollection("t_user");
        Document document = new Document();
        document.put("_id","1");   //注意 数字还是 字符串  1,or "1"
        t_user.deleteOne(document);
    }

    /**
     * 删除多个数据
     */
    @Test
    public void testDeleteMany(){
        MongoDatabase database = mongoClient.getDatabase("ems");
        MongoCollection<Document> t_user = database.getCollection("t_user");
        Document document = new Document();
        document.put("name","XIAOBAI");
        t_user.deleteMany(document);
    }


    /**
     * 查询总条数
     *
     */
    @Test
    public void tesCount() {
        MongoDatabase database = mongoClient.getDatabase("ems");
        MongoCollection<Document> t_user = database.getCollection("t_user");
        System.out.println(t_user.count());
    }

}




















