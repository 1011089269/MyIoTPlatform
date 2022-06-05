package service;

import dao.UserDao;
import entity.Token;
import entity.User;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 86189
 * @program: IntelliJ IDEA
 * @description:
 * @date 2022年6月4日 15点05分
 */
@Service
public class UserManageService {
    private final UserDao userDao;
    private final TokenManager tokenManager;
    public UserManageService(UserDao userDao,TokenManager tokenManager) {
        this.userDao = userDao;
        this.tokenManager = tokenManager;
    }

    public Result login(User user) {
        Result result = new Result();
        //判断用户名是否存在
        List<User> users = userDao.findUser(user);
        if (users == null || users.isEmpty()) {
            result.setStatus(1);
            result.setMsg("此用户不存在，请先进行注册");
            return result;
        }
        //判断用户密码是否正确
        User foundUser = users.get(0);
        if (!foundUser.getPassword().equals(user.getPassword())) {
            result.setStatus(2);
            result.setMsg("密码错误");
            return result;
        }
        result.setStatus(3);
        result.setMsg("登录成功");
        //创建token并返回
        Token token = tokenManager.createToken(foundUser.getId(),
                Token.Type.values()[foundUser.getRole()]);
        result.setData(token.getValue());
        return result;
    }

    public Result addUser(User user) {
        Result result = new Result();
        //判断用户名是否存在
        List<User> users = userDao.findUser(user);
        if (users != null && !users.isEmpty()) {
            result.setStatus(0);
            result.setMsg("用户名已存在，请直接登录或者更换用户名");
            return result;
        }
        //添加用户
        int affectedRowCount = userDao.addUser(user);
        if (affectedRowCount == 1) {
            result.setStatus(1);
            result.setMsg("添加用户成功");
        } else {
            result.setStatus(2);
            result.setMsg("添加用户失败");
        }
        return result;
    }

    public Result checkLogin(String tokenValue) {
        Result result = new Result();
        Token token = new Token(tokenValue);
        if (tokenManager.checkToken(token)) {
            result.setStatus(0);
            result.setMsg(token.getType().getName() + "，已登录");
        } else {
            result.setStatus(1);
            result.setMsg("未登录");
        }
        return result;
    }

    public Result logout(String tokenValue) {
        Result result = new Result();
        Token token = new Token(tokenValue);
        tokenManager.deleteToken(token);
        result.setMsg("未登录");
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

    //判断密码格式是否正确
    public boolean isPassword(String password) {
        //String str = "^[0-9 a-z A-Z] {6,18}+$";//必须同时包含字母、数字、下划线，并且是6-18位
        String str = "^[a-zA-Z]\\w{5,17}$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    //加密密码方法
//    public static String getMD5Str(String str) {
//        MessageDigest messageDigest = null;
//        try {
//            messageDigest = MessageDigest.getInstance("MD5");
//            messageDigest.reset();
//            messageDigest.update(str.getBytes("UTF-8"));
//        } catch (NoSuchAlgorithmException e) {
//            System.out.println("NoSuchAlgorithmException caught");
//            System.exit(-1);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        byte[] byteArray = messageDigest.digest();
//        StringBuffer md5StrBuff = new StringBuffer();
//        for (int i = 0; i<byteArray.length; i++) {
//            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)) {
//                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
//            }
//            else {
//                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
//            }
//            return md5StrBuff.toString();
//        }
//    }


}