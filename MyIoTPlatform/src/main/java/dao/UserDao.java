package dao;

import entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {
    int addUser(User user);
    List<User> findUser(User user);
    int updateUserInfo(User user);
    int deleteUser(User user);
}
