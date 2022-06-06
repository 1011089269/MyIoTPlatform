package controller;

import com.google.gson.Gson;
import controller.interceptor.Authority;
import entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.Result;
import service.UserManageService;
import service.mongodbService.AlertService;
import service.mongodbService.MeasurementService;
import service.mongodbService.MongodbService;
import service.mongodbService.StatusService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("device")
public class DeviceController {
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

    //设备添加
    @PostMapping("/add")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public void addDevice(Device device) {
        //name,time,dataType,id别写
        service.insert(device);
    }
    //设备查找
    @GetMapping("/find")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public String findDevice() {
        service.findList(0,100);
        Gson gson = new Gson();
        String json = gson.toJson(service.findList(0,100));
        System.out.println("查找全部设备："+json);
        return json;
    }
    //设备更新
    @PutMapping("/update")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public void updateDevice(Device device) {
        //name,time,dataType,id都写
        service.update(device);
    }
    //设备删除
    @DeleteMapping("/delete")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public void deleteDevice(Device device) {
        //有id就行
        service.delete(device.getId());
    }

    //设备数据添加
    @PostMapping("/addData")
    @ResponseBody
    @Authority(role = {Token.Type.DEVELOP})
    public void addData(String id, String data) {
        //name,time,dataType,id别写
        Device device = service.findById(id);
        int type = device.getDataType();
        //判断数据类型并添加
        if(type == 1){
            Measurement measurement = new Measurement();
            measurement.setDeviceId(id);
            measurement.setTime(df.format(new Date()));
            measurement.setValue(data);
            measurementService.insert(measurement);
        }else if(type == 2){
            Alert alert = new Alert();
            alert.setDeviceId(id);
            alert.setTime(df.format(new Date()));
            alert.setNews(data);
            alertService.insert(alert);
        }
        else if(type == 3){
            Status status = new Status();
            status.setDeviceId(id);
            status.setTime(df.format(new Date()));
            status.setStatus(Integer.valueOf(data));
            statusService.insert(status);
        }
    }

    //查找某设备ID的全部历史数据
    @PostMapping("/findDataById")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public String findDataById(String deviceId) {
        Gson gson = new Gson();
        Device device = service.findById(deviceId);
        int type = device.getDataType();
        String json = null;
        //判断数据类型并查找
        if(type == 1){
            json = gson.toJson(measurementService.findListByDeviceId(deviceId,0,100));
        }else if(type == 2){
            json = gson.toJson(alertService.findListByDeviceId(deviceId,0,100));
        }
        else if(type == 3){
            json = gson.toJson(statusService.findListByDeviceId(deviceId,0,100));
        }

        System.out.println("设备ID的全部历史数据："+json);
        return json;
    }

    //查找某设备ID的最新数据
    @PostMapping("/findLastByDeviceId")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public String findLastByDeviceId(String deviceId) {
        Gson gson = new Gson();
        Device device = service.findById(deviceId);
        int type = device.getDataType();
        String json = null;
        //判断数据类型并查找
        if(type == 1){
            json = gson.toJson(measurementService.findLastByDeviceId(deviceId));
        }else if(type == 2){
            json = gson.toJson(alertService.findLastByDeviceId(deviceId));
        }
        else if(type == 3){
            json = gson.toJson(statusService.findLastByDeviceId(deviceId));
        }
        System.out.println("查找设备ID的最新数据："+json);
        return json;
    }

    //查找某设备ID的时间段数据
    @PostMapping("/findListByPeriod")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public String findListByPeriod(String deviceId, String begin, String end) {
        Gson gson = new Gson();
        Device device = service.findById(deviceId);
        int type = device.getDataType();
        String json = null;
        //判断数据类型并查找
        if(type == 1){
            json = gson.toJson(measurementService.findListByPeriod(deviceId,begin,end));
        }else if(type == 2){
            json = gson.toJson(alertService.findListByPeriod(deviceId,begin,end));
        }
        else if(type == 3){
            json = gson.toJson(statusService.findListByPeriod(deviceId,begin,end));
        }
        return json;
    }

    //删除某条数据根据其id以及deviceId
    @PostMapping("/deleteDataByDeviceId")
    @ResponseBody
    @Authority(role = {Token.Type.DEVELOP})
    public void deleteDataByDeviceId(String id, String deviceId) {
        Device device = service.findById(deviceId);
        int type = device.getDataType();
        //判断数据类型并查找
        if(type == 1){
            measurementService.delete(id);
        }else if(type == 2){
            alertService.delete(id);
        }
        else if(type == 3){
            statusService.delete(id);
        }
    }

    //删除某设备的全部数据
    @PostMapping("/deleteDataById")
    @ResponseBody
    @Authority(role = {Token.Type.DEVELOP})
    public void deleteDataById(String deviceId) {
        measurementService.deleteByDeviceId(deviceId);
        alertService.deleteByDeviceId(deviceId);
        statusService.deleteByDeviceId(deviceId);
    }
}
