package controller;

import controller.interceptor.FreeToken;
import entity.Token;
import entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.Result;
import service.UserManageService;

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
    public UserController(UserManageService userManageService) {
        this.userManageService = userManageService;
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
    @FreeToken
    public Result checkLogin(String tokenValue) {
        return userManageService.checkLogin(tokenValue);
    }

    @PostMapping("/logout")
    @ResponseBody
    @FreeToken
    public Result logout(String tokenValue) {
        return userManageService.logout(tokenValue);
    }
    @GetMapping("/modify/info")
    @ResponseBody
    @FreeToken
    public Result modifyUserInfo() {
        return new Result();
    }

    @GetMapping("/manage")
    @ResponseBody
    @FreeToken
    public Result manageUsers() {
        return new Result();
    }

    @GetMapping("/update/version")
    @ResponseBody
    @FreeToken
    public Result updateSoftwareVersion() {
        return new Result();
    }

    //@RequestMapping(value="/add",method = RequestMethod.POST)
    @PostMapping("/add")
    @ResponseBody
    @FreeToken
    public Result addUser(User user) {
        System.out.println("获取待新增用户信息：" + user);
        return userManageService.addUser(user);
    }

    @ApiOperation(value = "查找用户", tags = {"查找用户tag"}, notes = "所有参数均为选填，若都不填则找出所有用户")
    @GetMapping("/find")
    @ResponseBody
    @FreeToken
    public Result findUser(User user) {
        System.out.println("获取待查询用户信息" + user);
        return userManageService.findUser(user);
    }

    @PutMapping("/update")
    @ResponseBody
    @FreeToken
    public Result updateUserInfo(User user) {
        System.out.println("获取待更新用户信息：" + user);
        return userManageService.updateUserInfo(user);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    @FreeToken
    public Result deleteUser(User user) {

        System.out.println("获取待删除用户信息：" + user);
        return userManageService.deleteUser(user);
    }


}
