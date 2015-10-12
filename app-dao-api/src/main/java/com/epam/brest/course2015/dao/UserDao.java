package com.epam.brest.course2015.dao;


import com.epam.brest.course2015.domain.User;

import java.util.List;

/**
 * Created by alexander on 7.10.15.
 */
public interface UserDao {
    public boolean isThereAUser (Integer id);
    public List<User> getAllUsers();
    public User getUserById (Integer id);
    public void insertUser (User user);
    public void deleteUser (Integer id);
    public void changeUserLogin (Integer id, String login);
    public void changeUserPassword (Integer id, String password);
}
