package service.mongodbService;

import entity.DataBaseDao;
import entity.Measurement;
import entity.Status;

import java.util.List;

public class StatusService implements DataBaseDao<Status> {
    @Override
    public void createCollection() {

    }

    @Override
    public void insert(Status entity) {

    }

    @Override
    public Status findByDeviceId(String deviceId) {
        return null;
    }

    @Override
    public List<Status> findListByDeviceId(String deviceId, int skip, int limit) {
        return null;
    }

    @Override
    public List<Status> findListByPeriod(String deviceId, String begin, String end) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteByDeviceId(String deviceId) {

    }
}
