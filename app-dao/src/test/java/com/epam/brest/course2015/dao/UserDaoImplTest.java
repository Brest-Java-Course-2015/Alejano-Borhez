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
        public void testIsThereAUser() throws Exception {
            assertFalse(userDao.isThereAUser(5));
            assertTrue(userDao.isThereAUser(1));
        }

        @Test
        public void testGetAllUsers() throws Exception {
            List<User> users = userDao.getAllUsers();
            assertTrue(users.size() > 0);
        }

        @Test
        public void testGetUserById() throws Exception {
            User user1 = userDao.getUserById(1);
            assertTrue(user1.getUserId() == 1 && user1.getLogin().equals("user1") && user1.getPassword().equals("user1Password"));
        }

        @Test
        public void testInsertUser() throws Exception {
            Integer sizeBefore = userDao.getAllUsers().size();
            Integer testId = 3;
            User user = new User();
            user.setUserId(testId);
            user.setLogin("user3");
            user.setPassword("user3Password");

            userDao.insertUser(user);

            Integer sizeAfter = userDao.getAllUsers().size();

            assertTrue( (sizeAfter - sizeBefore) == 1);

            userDao.deleteUser(testId);
            sizeAfter = userDao.getAllUsers().size();
            assertTrue((sizeBefore - sizeAfter) == 0);
          }

        @Test
        public void testChangeUserLogin () throws Exception {
            Integer testId = 4;
            User user = new User();
            user.setUserId(testId);
            user.setLogin("user" + testId);
            user.setPassword("user" + testId + "Password");

            userDao.insertUser(user);

            String login = "newLogin";
            userDao.changeUserLogin(testId, login);

            assertTrue(userDao.getUserById(testId).getLogin().equals(login));

            userDao.deleteUser(testId);
        }

        @Test
        public void testChangeUserPassword () throws Exception {
            Integer testId = 4;
            User user = new User();
            user.setUserId(testId);
            user.setLogin("user" + testId);
            user.setPassword("user" + testId + "Password");

            userDao.insertUser(user);

            String password = "newLogin";
            userDao.changeUserPassword(testId, password);

            assertTrue(userDao.getUserById(testId).getPassword().equals(password));

            userDao.deleteUser(testId);
        }

    }