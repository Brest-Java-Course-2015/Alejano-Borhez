package com.epam.brest.course2015.service;

import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alexander on 18.10.15.
**/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional()
public class UserServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    private static final User user = new User("userLogin3", "userPassword3");

    @Test
    public void testGetAllUsers() throws Exception {
        LOGGER.debug("test: getAllUsers()");
        Assert.assertTrue(userService.getAllUsers().size() > 0);
    }

    @Test
    public void testAddUser() throws Exception {
        LOGGER.debug("test: addUser()");
        int sizeBefore = userService.getAllUsers().size();
        userService.addUser(user);
        Assert.assertTrue(sizeBefore + 1 == userService.getAllUsers().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullUser() throws Exception {
        LOGGER.debug("test: addNullUser()");
        userService.addUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithNotNullId() throws Exception {
        LOGGER.debug("test: addUserWithNotNullId()");
        User user = new User();
        user.setUserId(1);
        userService.addUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithNullLogin() throws Exception {
        LOGGER.debug("test: addUserWithNullLogin()");
        User user = new User();
        userService.addUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithNullPassword() throws Exception {
        LOGGER.debug("test: addUserWithNullPassword()");
        User user = new User();
        user.setLogin("login");
        userService.addUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithNotUniqueLogin() throws Exception {
        LOGGER.debug("test: addUserWithNotUniqueLogin()");
        User user = new User("login", "password");
        userService.addUser(user);
        userService.addUser(user);
    }



}