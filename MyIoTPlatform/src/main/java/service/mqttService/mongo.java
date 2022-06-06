package service.mqttService;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entity.Alert;
import entity.Device;
import entity.Measurement;
import entity.Status;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Service
public class mongo{
    MongoTemplate mongoTemplate;
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

    public void insertAlert(Alert alert, String col){
        MongoDatabase database = connectMongoDBWithAuthenticationImpl();
        //插入文档
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Document document = new Document("deviceId", alert.getDeviceId());
        document.append("time", sdf.format(new Date()))
                .append("news", alert.getNews());
        MongoCollection<Document> collection = database.getCollection(col);
        collection.insertOne(document);

        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
    }

    public void insertMeasurement(Measurement measurement, String col){
        MongoDatabase database = connectMongoDBWithAuthenticationImpl();
        //插入文档
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Document document = new Document("deviceId", measurement.getDeviceId());
        document.append("time", sdf.format(new Date()))
                .append("value", measurement.getValue());
        MongoCollection<Document> collection = database.getCollection(col);
        collection.insertOne(document);

        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
    }

    public void insertStatus(Status status, String col){
        MongoDatabase database = connectMongoDBWithAuthenticationImpl();
        //插入文档
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Document document = new Document("deviceId", status.getDeviceId());
        document.append("time", sdf.format(new Date()))
                .append("status", status.getStatus());
        MongoCollection<Document> collection = database.getCollection(col);
        collection.insertOne(document);

        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
    }
}
