package service.mongodbService;

import entity.Alert;
import entity.DataBaseDao;

import java.util.List;

public class AlertService implements DataBaseDao<Alert> {
    @Override
    public void createCollection() {

    }

    @Override
    public void insert(Alert entity) {

    }

    @Override
    public Alert findByDeviceId(String deviceId) {
        return null;
    }

    @Override
    public List<Alert> findListByDeviceId(String deviceId, int skip, int limit) {
        return null;
    }

    @Override
    public List<Alert> findListByPeriod(String deviceId, String begin, String end) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteByDeviceId(String deviceId) {

    }
}
