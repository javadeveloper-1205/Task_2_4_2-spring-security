package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);

    List<User> getAllUsers();

    void saveUser(User user);

    User findUser(int id);

    void deleteUser(int id);

    User findUsername(String username);

}


