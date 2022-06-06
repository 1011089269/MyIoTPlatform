package controller;

import controller.interceptor.Authority;
import controller.interceptor.FreeToken;
import entity.Token;
import entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.Result;
import service.UserManageService;
import service.mqttService.Client;

/**
 * @author fzw
 * @program: IntelliJ IDEA
 * @description:
 * @date 2022年6月4日 15点03分
 */
@Api(value = "用户controller", tags = {"用户操作接口"})
@Controller
@RequestMapping("user")
public class UserController {

    private final UserManageService userManageService;
    public UserController(UserManageService userManageService) throws MqttException {
        this.userManageService = userManageService;
//        Client client = new Client();
//        client.connect();
//        String topic = "test";
//        //订阅
//        client.subscribe(topic,0);
    }

    @PostMapping("/login")
    @ResponseBody
    @FreeToken
    public Result login(User user) {
        return userManageService.login(user);
    }

    @PostMapping("/register")
    @ResponseBody
    @FreeToken
    public Result register(User user) {
        return userManageService.addUser(user);
    }

    @PostMapping("/login/check")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public Result checkLogin(String tokenValue) {
        return userManageService.checkLogin(tokenValue);
    }

    @PostMapping("/logout")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public Result logout(String tokenValue) {
        return userManageService.logout(tokenValue);
    }
    @GetMapping("/modify/info")
    @ResponseBody
    @Authority(role = {Token.Type.ADMIN})
    public Result modifyUserInfo() {
        return new Result();
    }

    @GetMapping("/manage")
    @ResponseBody
    @Authority(role = {Token.Type.ADMIN})
    public Result manageUsers() {
        return new Result();
    }

    @GetMapping("/update/version")
    @ResponseBody
    @Authority(role = {Token.Type.ADMIN})
    public Result updateSoftwareVersion() {
        return new Result();
    }

    //@RequestMapping(value="/add",method = RequestMethod.POST)
    @PostMapping("/add")
    @ResponseBody
    @Authority(role = {Token.Type.ADMIN})
    public Result addUser(User user) {
        System.out.println("获取待新增用户信息：" + user);
        return userManageService.addUser(user);
    }

    @ApiOperation(value = "查找用户", tags = {"查找用户tag"}, notes = "所有参数均为选填，若都不填则找出所有用户")
    @GetMapping("/find")
    @ResponseBody
    @Authority(role = {Token.Type.ADMIN})
    public Result findUser(User user) {
        System.out.println("获取待查询用户信息" + user);
        return userManageService.findUser(user);
    }

    @PutMapping("/modify")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public Result updateUserInfo(String tokenValue, String name, String email, Integer age) {
        User user = new User();
        Token token = new Token(tokenValue);
        user.setId(token.getId());
        user.setAge(age);
        user.setEmail(email);
        user.setName(name);
        System.out.println("获取待更新用户信息：" + user);
        return userManageService.updateUserInfo(user);
    }

    @PutMapping("/update")
    @ResponseBody
    @Authority(role = {Token.Type.ADMIN})
    public Result updateUserInfo(User user) {
        System.out.println("获取待更新用户信息：" + user);
        return userManageService.updateUserInfo(user);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    @Authority(role = {Token.Type.ADMIN})
    public Result deleteUser(User user) {

        System.out.println("获取待删除用户信息：" + user);
        return userManageService.deleteUser(user);
    }

    @PostMapping("/changepwd")
    @ResponseBody
    @FreeToken
    public Result changepwd(String name, String email) {

        System.out.println("获取待重置密码的用户信息：" + name + email);
        return userManageService.ChangePassword(name, email);
    }

    @PostMapping("/updatepwd")
    @ResponseBody
    @Authority(role = {Token.Type.USER, Token.Type.ADMIN, Token.Type.DEVELOP})
    public Result updatepwd(String tokenValue , String oldpassword , String newpassword) {
        Token token = new Token(tokenValue);

        System.out.println("获取待修改密码的信息：tokenvale" + tokenValue +"oldpwd" + oldpassword+"newpwd"+newpassword);
        return userManageService.updatepwd(token.getId(),oldpassword,newpassword);
    }

}
