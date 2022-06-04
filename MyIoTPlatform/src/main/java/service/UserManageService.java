package service;

import dao.UserDao;
import entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86189
 * @program: IntelliJ IDEA
 * @description:
 * @date 2022年6月4日 15点05分
 */
@Service
public class UserManageService {
    private final UserDao userDao;
    public UserManageService(UserDao userDao) {
        this.userDao =userDao;
    }

    public Result addUser(User user) {
        Result result = new Result();
        int affectedRowCount = userDao.addUser(user);
        if (affectedRowCount == 1) {
            result.setStatus(0);
            result.setMsg("添加用户成功");
        } else {
            result.setMsg("添加用户失败");
        }
        return result;
    }

    public Result findUser(User user) {
        Result result = new Result();
        List<User> users = userDao.findUser(user);
        result.setData(users);
        return result;
    }

    public Result updateUserInfo(User user) {
        Result result = new Result();
        int affectedRowCount = userDao.updateUserInfo(user);
        if (affectedRowCount == 1) {
            result.setStatus(0);
            result.setMsg("修改用户信息成功");
        } else {
            result.setMsg("修改用户信息失败");
        }
        return result;
    }

    public Result deleteUser(User user) {
        Result result = new Result();
        userDao.deleteUser(user);
        result.setMsg("删除用户完成");
        return result;
    }

}