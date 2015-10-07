package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alexander on 7.10.15.
 */
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})

    public class UserDaoImplTest {

        @Autowired
        private UserDao userDao;

        @Test
        public void testGetAllUsers() throws Exception {
            List<User> users = userDao.getAllUsers();
            assertTrue(users.size() == 2);
        }

       @Test
        public void testGetUserById() throws Exception {
            User user = userDao.getUserById(1);
            assertTrue(user.getUserId() == 1 && user.getLogin().equals("user1") && user.getPassword().equals("user1Password"));
        }

        @Test
        public void testInsertUser() throws Exception {
            User user = new User();
            user.setUserId(3);
            user.setLogin("user3");
            user.setPassword("user3Password");

            userDao.insertUser(user);

            List<User> usersList = userDao.getAllUsers();
            assertTrue(usersList.size() == 3);

            User userTest = userDao.getUserById(3);
            assertTrue(userTest.getUserId() == 3 && userTest.getLogin().equals("user3") && userTest.getPassword().equals("user3Password"));
        }


    }