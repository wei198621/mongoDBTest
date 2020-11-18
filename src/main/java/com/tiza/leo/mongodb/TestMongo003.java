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
        mongoClient = new MongoClient("192.168.121.102", 27017);
        database = mongoClient.getDatabase("ems");
        t_user = database.getCollection("t_user");
    }

    /**
     * 批量插入--------------------------------------------------------ok
     */
    @Test
    public void testInstert() {
        for (int i = 0; i < 15; i++) {
            Document document = new Document();
            document.put("_id", UUID.randomUUID().toString());
            document.put("name", "chenyn" + (i+20));
            document.put("age", (i+20));
            t_user.insertOne(document);
        }
    }


    /**
     * 删除---------------------------------------------------------ok
     */
    @Test
    public void testDelete() {
        t_user.deleteMany(Filters.gt("age", 20));
    }


    /**
     * 更新-----------------------------------------------------------ok
     */
    @Test
    public void testUpdate() {
        Bson filters = Filters.eq("age", 9);
        t_user.updateOne(filters, new Document("$set", new Document("name", "aa")));
    }

    @After
    public void after() {
        mongoClient.close();
    }


}