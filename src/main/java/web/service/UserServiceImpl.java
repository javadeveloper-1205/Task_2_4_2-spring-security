package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void add(User user) {
        userDao.addUser(user);
    }

    @Override
    public void edit(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User find(int id) {
        return userDao.findUser(id);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public User getUserByName(String s) {
        return userDao.findUsername(s);
    }
}
