package web.service;

import web.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    void add(User user);

    void edit(User user);

    User find(int id);

    void delete(int id);

    User getUserByName(String s);
}
