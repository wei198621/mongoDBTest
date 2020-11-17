package com.tiza.leo.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;



/**
 * 未经过测试 leo
 */
public class TestMongo003 {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> t_user;

    @Before
    public void before() {
        mongoClient = new MongoClient("192.168.28.136", 27017);
        database = mongoClient.getDatabase("test");
        t_user = database.getCollection("t_user");
    }

    /**
     * 批量插入
     */
    @Test
    public void testInstert() {
        for (int i = 0; i < 100; i++) {
            Document document = new Document();
            document.put("_id", UUID.randomUUID().toString());
            document.put("name", "chenyn" + i);
            document.put("age", i);
            t_user.insertOne(document);
        }
    }


    /**
     * 删除
     */
    @Test
    public void testDelete() {
        t_user.deleteMany(Filters.gt("age", 23));
    }


    /**
     * 更新
     */
    @Test
    public void testUpdate() {
        Bson filters = Filters.eq("age", 12);
        t_user.updateOne(filters, new Document("$set", new Document("name", "aa")));
    }

    @After
    public void after() {
        mongoClient.close();
    }


}