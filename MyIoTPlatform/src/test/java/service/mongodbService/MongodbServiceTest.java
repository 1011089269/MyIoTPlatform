package service.mongodbService;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import entity.Alert;
import entity.Device;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebAppConfiguration
@SpringJUnitConfig(locations = "classpath:applicationContext.xml")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
class MongodbServiceTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private MongodbService service;
    @Autowired
    private AlertService alertService;
    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private StatusService statusService;

    @Test
    void insertDevice() {
        Device device = new Device();
        device.setName("光照传感器");
        device.setTime(df.format(new Date()));
        device.setDataType(1);
        service.insert(device);

        List<Device> devices = service.findList(0,100);
        for(Device d : devices){
            System.out.println(d);
        }

    }

    @Test
    void findById() {

        System.out.println(service.findById("629e31e3cc95065a62c7aa8b"));
    }

    @Test
    void findList() {
        service.findList(0,100);
        List<Device> devices = service.findList(0,100);
        for(Device d : devices){
            System.out.println(d);
        }
    }


    @Test
    void findListByName() {
        List<Device> devices = service.findListByName("光照传感器");
        for(Device d : devices){
            System.out.println(d);
        }
    }

    @Test
    void update() {
        Device device = new Device();
        device.setId("629e31e3cc95065a62c7aa8b");
        device.setName("温湿度传感器");
        device.setTime(df.format(new Date()));
        device.setDataType(1);
        service.update(device);
        List<Device> devices = service.findList(0,100);
        for(Device d : devices){
            System.out.println(d);
        }
    }

    @Test
    void deleteDevice() {
        service.delete("629e31e3cc95065a62c7aa8b");
        List<Device> devices = service.findList(0,100);
        for(Device d : devices){
            System.out.println(d);
        }
    }


    @Test
    void insert() {
        Alert alert = new Alert();
        alert.setDeviceId("629df0e5d5a43a7080b8e13a");
        alert.setTime(df.format(new Date()));
        alert.setNews("Alert12");
        alertService.insert(alert);
    }

    @Test
    void findLastByDeviceId() {
        System.out.println(alertService.findLastByDeviceId("629df0e5d5a43a7080b8e13a"));

    }

    @Test
    void findListByDeviceId() {
        System.out.println(alertService.findListByDeviceId("629df0e5d5a43a7080b8e13a",0, 100));

    }

    @Test
    void findListByPeriod() {
        System.out.println(alertService.findListByPeriod("629df0e5d5a43a7080b8e13a","2022-06-07 00:07:00", "2022-06-07 00:07:52"));

    }

    @Test
    void delete() {
        alertService.delete("629e2658cb0c463fca60c1f6");
    }

    @Test
    void deleteByDeviceId() {
        alertService.deleteByDeviceId("629df0e5d5a43a7080b8e13a");

    }

}