package controller;

import entity.Device;
import entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.Result;
import service.UserManageService;
import service.mongodbService.MongodbService;

@Api(value = "设备controller", tags = {"设备操作接口"})
@Controller
@RequestMapping("device")
public class DeviceController {
    @Autowired
    MongodbService service;

    //@RequestMapping(value="/add",method = RequestMethod.POST)
    @PostMapping("/add")
    @ResponseBody
    public void addDevice(Device device) {
        service.insertAndFindDocument();
    }

    @ApiOperation(value = "查找设备", tags = {"查找设备tag"}, notes = "所有参数均为选填，若都不填则找出所有用户")
    @GetMapping("/find")
    @ResponseBody
    public void findUser(Device device) {
        service.insertAndFindDocument();
    }

    @PutMapping("/update")
    @ResponseBody
    public void updateUserInfo(Device device) {
        service.updateDocument();
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteUser(Device device) {
        service.deleteDocument();
    }
}
