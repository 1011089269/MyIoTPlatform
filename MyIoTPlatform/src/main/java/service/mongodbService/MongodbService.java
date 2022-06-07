package service.mongodbService;
import entity.Device;
import entity.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MongodbService implements IBaseDao<Device> {
    @Autowired
    private MongoTemplate mongoTemplate;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void createCollection() {
        if(!mongoTemplate.collectionExists(Device.class)){
            mongoTemplate.createCollection(Device.class);
        }
    }

    @Override
    public void insert(Device entity) {
        entity.setTime(df.format(new Date()));
        mongoTemplate.insert(entity);
    }

    @Override
    public Device findById(String id) {
        return mongoTemplate.findById(id,Device.class);
    }

    @Override
    public List<Device> findList(int skip, int limit) {
        Query query = new Query();
        query.skip(skip).limit(limit);
        return this.mongoTemplate.find(query, Device.class);
    }

    @Override
    public List<Device> findListByName(String name) {
        Query query = new Query();
        query.addCriteria(new Criteria("name").is(name));
        return this.mongoTemplate.find(query,Device.class);
    }

    @Override
    public void update(Device entity) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(entity.getId()));
        Update update = new Update();
        update.set("name", entity.getName());
        update.set("time", df.format(new Date()));
        update.set("dataType", entity.getDataType());
        this.mongoTemplate.updateFirst(query, update, Device.class);
    }

    @Override
    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));
        this.mongoTemplate.remove(query, Device.class);
    }


}