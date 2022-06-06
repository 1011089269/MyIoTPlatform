package service.mongodbService;

import entity.Alert;
import entity.DataBaseDao;
import entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
@Service
public class AlertService implements DataBaseDao<Alert> {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void createCollection() {
        if(!mongoTemplate.collectionExists(Alert.class)){
            mongoTemplate.createCollection(Alert.class);
        }
    }

    @Override
    public boolean insert(Alert entity) {
        mongoTemplate.insert(entity);
        return true;
    }

    @Override
    public Alert findLastByDeviceId(String deviceId) {
        Query query = query(where("deviceId").is(deviceId))
                .with(Sort.by(Sort.Direction.DESC, "time"));
        return mongoTemplate.findOne(query, Alert.class);
    }

    @Override
    public List<Alert> findListByDeviceId(String deviceId, int skip, int limit) {
        Query query = query(where("deviceId").is(deviceId))
                .with(Sort.by(Sort.Direction.DESC, "time"))
                .skip(skip).limit(limit);
        return mongoTemplate.find(query, Alert.class);
    }

    @Override
    public List<Alert> findListByPeriod(String deviceId, String begin, String end) {
        Query query = query(where("deviceId").is(deviceId).and("time").gte(begin).lte(end))
                .with(Sort.by(Sort.Direction.DESC, "time"));
        return mongoTemplate.find(query, Alert.class);
    }

    @Override
    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));
        this.mongoTemplate.remove(query, Alert.class);
    }

    @Override
    public void deleteByDeviceId(String deviceId) {
        Query query = new Query();
        query.addCriteria(new Criteria("deviceId").is(deviceId));
        this.mongoTemplate.remove(query, Alert.class);
    }
}
