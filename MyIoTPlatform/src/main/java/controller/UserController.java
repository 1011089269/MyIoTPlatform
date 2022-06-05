package controller;

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
    public UserController(UserManageService userManageService) {this.userManageService = userManageService;}

    //@RequestMapping(value="/add",method = RequestMethod.POST)
    @PostMapping("/add")
    @ResponseBody
    public Result addUser(User user) {
        System.out.println("获取待新增用户信息：" + user);
        return userManageService.addUser(user);
    }

    @ApiOperation(value = "查找用户", tags = {"查找用户tag"}, notes = "所有参数均为选填，若都不填则找出所有用户")
    @GetMapping("/find")
    @ResponseBody
    public Result findUser(User user) {
        System.out.println("获取待查询用户信息" + user);
        return userManageService.findUser(user);
    }

    @PutMapping("/update")
    @ResponseBody
    public Result updateUserInfo(User user) {
        System.out.println("获取待更新用户信息：" + user);
        return userManageService.updateUserInfo(user);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public Result deleteUser(User user) {

        System.out.println("获取待删除用户信息：" + user);
        return userManageService.deleteUser(user);
    }


}
