package service.mongodbService;

import entity.Alert;
import entity.DataBaseDao;
import entity.Measurement;

import java.util.List;

public class MeasurementService implements DataBaseDao<Measurement> {
    @Override
    public void createCollection() {

    }

    @Override
    public void insert(Measurement entity) {

    }

    @Override
    public Measurement findByDeviceId(String deviceId) {
        return null;
    }

    @Override
    public List<Measurement> findListByDeviceId(String deviceId, int skip, int limit) {
        return null;
    }

    @Override
    public List<Measurement> findListByPeriod(String deviceId, String begin, String end) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteByDeviceId(String deviceId) {

    }
}
