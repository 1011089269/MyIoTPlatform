package service;

import dao.UserDao;
import entity.Token;
import entity.User;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
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

    public UserManageService(UserDao userDao, TokenManager tokenManager) {
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
        if (!foundUser.getPassword().equals(getMD5Str(user.getPassword()))) {
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
        user.setPassword(getMD5Str(user.getPassword()));
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
            User user = new User();
            user.setId(token.getId());
            List<User> users = userDao.findUser(user);
            result.setStatus(0);
            result.setMsg(token.getType().getName() + "，已登录");
            result.setData(users);
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
        System.out.println(user.getPassword());
        if(user.getPassword() != null && !user.getPassword().equals("")){
            user.setPassword(getMD5Str(user.getPassword()));
        }
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
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }


    //重置密码
    public Result ChangePassword(String name, String semail) {
        Result result = new Result();
        User user = new User();
        user.setName(name);
        List<User> users = userDao.findUser(user);
        if (users == null || users.isEmpty()) {
            result.setStatus(0);
            result.setMsg("此用户不存在，请先进行注册");
            return result;
        }
        User foundUser = users.get(0);
        String email = foundUser.getEmail();
        if (!semail.equals(email)) {
            result.setStatus(0);
            result.setMsg("邮箱错误");
            return result;
        }
        String newPassword = getPassWordOne(8);
        foundUser.setPassword(getMD5Str(newPassword));
        int temp = userDao.updateUserInfo(foundUser);
        sendEmail(email, newPassword);
        result.setStatus(1);
        result.setMsg("邮件已发送，请重置密码!");
        return result;
    }

    // 发送邮件
    public void sendEmail(String emailAddr, String emailInfo) {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        // 服务器地址:
        String smtp = "smtp.163.com";
        // 登录用户名:
        String username = "C38097451@163.com";
        // 登录口令:
        String password = "LL0806";
        // 连接到SMTP服务器587端口:
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp); // SMTP主机名
        props.put("mail.smtp.port", "25"); // 主机端口号
        props.put("mail.smtp.auth", "true"); // 是否需要用户认证
        props.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密
//        props.put("mail.smtp.ssl.enable", true);
        // 获取Session实例:
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        // 设置debug模式便于调试:
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        try {
            // 设置发送方地址:
            message.setFrom(new InternetAddress("C38097451@163.com"));
            // 设置接收方地址:
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddr));
            // 设置邮件主题:
            message.setSubject("重置密码", "UTF-8");
            // 设置邮件正文:
            message.setText("新的密码:"+emailInfo, "UTF-8");
            // 发送:
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //生成随机密码
    public static String getPassWordOne(int len) {
        int i; //生成的随机数
        int count = 0; //生成的密码的长度
        // 密码字典
        char[] str = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '~', '!', '@', '#', '$', '%', '^', '-', '+'
        };
        StringBuffer stringBuffer = new StringBuffer("");
        Random r = new Random();
        while (count < len) {
            //生成 0 ~ 密码字典-1之间的随机数
            i = r.nextInt(str.length);
            stringBuffer.append(str[i]);
            count++;
        }
        return stringBuffer.toString();
    }

    public Result updatepwd(int id , String oldpwd , String newpwd) {
        Result result = new Result();
        User user = new User();
        user.setId(id);
        userDao.findUser(user);
        List<User> users = userDao.findUser(user);
        User foundUser = users.get(0);
        if (foundUser.getPassword().equals(getMD5Str(oldpwd))) {
            if (oldpwd.equals(newpwd)) {
                result.setStatus(0);
                result.setMsg("原密码与新密码一致");
            } else {
                foundUser.setPassword(getMD5Str(newpwd));
                userDao.updateUserInfo(foundUser);
                result.setStatus(1);
                result.setMsg("密码修改成功");
            }
        } else {
            result.setStatus(0);
            result.setMsg("原密码错误");
        }
        return result;
    }
}