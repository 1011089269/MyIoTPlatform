package service.mongodbService;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class MongodbService {

    //连接数据库
    public void connectMongoDB() {
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("test");
        System.out.println(database.getName());
    }

    //使用身份认证连接数据库
    public void connectMongoDBWithAuthentication() {
        MongoDatabase database = connectMongoDBWithAuthenticationImpl();
        System.out.println(database.getName());
    }

    private MongoDatabase connectMongoDBWithAuthenticationImpl() {
        ServerAddress serverAddress = new ServerAddress("localhost", 27017);
        MongoCredential credential = MongoCredential.createCredential("root", "admin", "13579".toCharArray());
        MongoClient client = new MongoClient(serverAddress, Arrays.asList(credential));
        return client.getDatabase("test");

    }

    //创建和获取集合
    public void createAndGetCollection() {
        MongoDatabase database = connectMongoDBWithAuthenticationImpl();
        String collectionName = "java_collection";
        database.createCollection(collectionName);
        MongoCollection<Document> mongoCollection = database.getCollection(collectionName);
        System.out.println(mongoCollection);
    }

    //插入和查询文档
    public void insertAndFindDocument() {
        MongoDatabase database = connectMongoDBWithAuthenticationImpl();
        //插入文档
        Document document = new Document("title", "MongoDB java操作测试");
        document.append("description", "使用Java测试MongoDB的基本操作")
                .append("by", "kql");
        MongoCollection<Document> collection = database.getCollection("java_collection");
        collection.insertOne(document);

        Document document2 = new Document("title", "MongoDB java插入测试");
        document2.append("by", "kql")
                .append("likes", 200);
        collection.insertOne(document2);
        //查询文档
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());

        }
    }

    //更新文档
    public void updateDocument() {
        MongoDatabase database = connectMongoDBWithAuthenticationImpl();
        MongoCollection<Document> collection = database.getCollection("java_collection");//查询文档
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
        //找到likes=200的文档，将其by改为201959225210
        collection.updateMany(Filters.eq("likes", 200),
                new Document("$set", new Document("by", "201959225210")));
        //
        findIterable = collection.find();
        mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
    }

    //获取集合
    private MongoCollection<Document> getCollection(String name) {
        MongoDatabase database = connectMongoDBWithAuthenticationImpl();
        return database.getCollection(name);
    }
    //查询文档
    private void findAndPrintDocument(MongoCollection<Document> collection) {
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
    }

    //删除文档
    public void deleteDocument() {
        MongoCollection<Document> collection = getCollection("java_collection");System.out.println("删除前");
        findAndPrintDocument(collection);
        collection.findOneAndDelete(Filters.eq( "by","kql"));
        System.out.println("删除后");
        findAndPrintDocument(collection);
    }
}