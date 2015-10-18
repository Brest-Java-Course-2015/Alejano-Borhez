package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alexander on 7.10.15.
 */
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
    @Transactional() //возвращает начальное состояние перед каждым тестом

public class UserDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String USER_LOGIN1 = "user1";
    public static final String USER_PASSWORD1 = "user1password";

    @Autowired
    private UserDao userDao;

    private static final User user = new User("userLogin3", "userPassword3");

    @Test
    public void testGetAllUsers() throws Exception {
        LOGGER.debug("test: getAllUsers()");
        List<User> users = userDao.getAllUsers();
        assertTrue(users.size() == 2);
    }

    @Test
    public void testGetUser() throws Exception {
        LOGGER.debug("test: getUser()");
        int userId = 1;
        User user = userDao.getUserById(userId);
        assertNotNull(user);
        assertTrue(user.getUserId().equals(userId));
    }

    @Test
    public void testGetUserByLogin() throws Exception {
        LOGGER.debug("test: getUserByLogin()");
        User user = userDao.getUserByLogin(USER_LOGIN1);
        assertNotNull(user);
        assertTrue(user.getLogin().equals(USER_LOGIN1));
    }

    @Test
    public void testAddUser() throws Exception {
        LOGGER.debug("test: addUser()");
        Integer userId = userDao.addUser(user);
        assertNotNull(userId);
        User newUser = userDao.getUserById(userId);
        assertNotNull(newUser);
        assertTrue(user.getLogin().equals(newUser.getLogin()));
        assertTrue(user.getPassword().equals(newUser.getPassword()));
        assertNotNull(newUser.getCreatedDate());
    }

    @Test
    public void testUpdateUser() throws Exception {
        LOGGER.debug("test: updateUser()");
        User user = userDao.getUserByLogin(USER_LOGIN1);
        user.setPassword(USER_PASSWORD1 + 12);
        userDao.updateUser(user);
        User newUser = userDao.getUserById(user.getUserId());
        assertTrue(user.getLogin().equals(newUser.getLogin()));
        assertTrue(user.getPassword().equals(newUser.getPassword()));
        assertTrue(user.getCreatedDate().equals(newUser.getCreatedDate()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        LOGGER.debug("test :deleteUser()");
        List<User> users = userDao.getAllUsers();
        assertTrue(users.size() > 0);
        int sizeBefore = users.size();
        userDao.deleteUser(users.get(0).getUserId());
        assertTrue((sizeBefore - 1) == userDao.getAllUsers().size());
    }
}