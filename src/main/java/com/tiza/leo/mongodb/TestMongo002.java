package com.tiza.leo.mongodb;
 import com.mongodb.MongoClient;
        import com.mongodb.client.FindIterable;
        import com.mongodb.client.MongoCollection;
        import com.mongodb.client.MongoDatabase;
        import com.mongodb.client.model.Filters;
        import org.bson.Document;
        import org.junit.After;
        import org.junit.Before;
        import org.junit.Test;

        import java.util.regex.Pattern;


/**
 * 经过测试 leo
 *
 * 处理数据前要经过数据初始化，过程见下
 *
 use ems;
 show tables;
 db.createCollection("t_user");

 for(var i=0;i<10;i++){
 db.t_user.insert({"id":i,"name":"xiaohei","addr":"xuzhou","age":11});
 }

 db.t_user.find();
 *
 *
 *
 */
public class TestMongo002 {

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
     * 有条件的查询数据---ok
     */
    @Test
    public void testFind() {
        Document document = new Document();
        document.put("name", "xiaohei");
        FindIterable<Document> documents = t_user.find(document);
        for (Document doc : documents) {
            System.out.println(doc);
        }
    }

    /**
     * 有条件查询展示指定的字段 --- ok
     */
    @Test
    public void testFindField() {
        Document document = new Document();
        document.put("name", "xiaohei");

        Document bson = new Document();
        bson.put("_id", 1);
        bson.put("name", 1);
        FindIterable<Document> documents = t_user.find(document)
                .projection(bson);//
        for (Document document1 : documents) {
            System.out.println(document1);
        }
    }


    /**
     * 查询数据取值范围-----ok
     * 条件搜索$lt/ $lte / $gt / $gte /  $ne / $eq <====> < / <= / >  /  >=  /  != /==
     */
    @Test
    public void tesRange() {
        FindIterable<Document> documents = t_user.find()
                .filter(Filters.gt("age", 54))
                .filter(Filters.lte("age", 24));
        for (Document document : documents) {
            System.out.println(document);
        }
    }

    /**
     * ---------------------------ok
     * 条件搜索OR查询$in
     * 查询id的值是2或者是3的docuemnt
     */
    @Test
    public void testIn() {
        FindIterable<Document> documents = t_user.find().filter(Filters.in("id", new Integer[]{2,3,4}));
        for (Document document : documents) {
            System.out.println(document);
        }
    }


    /**
     * -------------------------------------------ok
     * 条件搜索OR查询$or
     * 查询id是1或者是name等于jiangzz的所有文档
     */
    @Test
    public void testOr() {
        Document document1 = new Document();
        document1.put("name", "chenyn");
        Document document2 = new Document();
        document2.put("age", 11);

        FindIterable<Document> documents = t_user.find().filter(Filters.or(document1, document2));
        for (Document document : documents) {
            System.out.println(document);
        }
    }

    /**
     * 模糊查询---------------------------ok
     */
    @Test
    public void testQueryLike() {
        Document document = new Document();
        document.put("name", Pattern.compile("x", Pattern.CASE_INSENSITIVE));
        FindIterable<Document> documents = t_user.find(document);
        for (Document document1 : documents) {
            System.out.println(document1);
        }

    }


    /**
     * null值得处理--------------------ok
     */

    @Test
    public void testNull() {
        Document document = new Document();
        document.put("name", null);
        FindIterable<Document> documents = t_user.find(document);
        for (Document document1 : documents) {
            System.out.println(document1);
        }
    }

    /**
     * 查询数组 查询地址中是北京 上海  广州的记录------------ok
     */
    @Test
    public void testAll() {
        FindIterable<Document> address = t_user.find().filter(Filters.all("address", new String[]{"beijing"}));
        for (Document document : address) {
            System.out.println(document);
        }
    }

    /**
     * 查询数组 查询地址中包含 河北 的document---------------ok
     */
    @Test
    public void testArray() {
        Document document1 = new Document();
        document1.put("address", "beijing");
        FindIterable<Document> address = t_user.find(document1);
        for (Document document : address) {
            System.out.println(document);
        }
    }


    /**
     * 对查询结果进行排序----------------------------ok
     */
    @Test
    public void testSort() {
        FindIterable<Document> documents = t_user.find().sort(new Document("age", -1));
        for (Document document : documents) {
            System.out.println(document);
        }
    }


    /**
     * 对查询结果进行排序 分页查询-----------------ok
     */
    @Test
    public void testPage() {
        FindIterable<Document> documents = t_user.find().sort(new Document("age", -1)).skip(0).limit(2);
        for (Document document : documents) {
            System.out.println(document);
        }
    }


    @After
    public void after() {
        mongoClient.close();
    }


}
