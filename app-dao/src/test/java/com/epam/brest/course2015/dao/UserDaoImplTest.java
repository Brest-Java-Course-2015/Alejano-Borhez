package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
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

        @Autowired
        private UserDao userDao;



        @Test
        public void testGetAllUsers() throws Exception {
            List<User> users = userDao.getAllUsers();
            assertTrue(users.size() > 0);
        }

        @Test
        public void testIsThereAUser() throws Exception {
        assertFalse(userDao.isThereAUser(5));
        assertTrue(userDao.isThereAUser(1));
        }

        @Test
        public void testGetUserById() throws Exception {
            User user1 = userDao.getUserById(1);
            assertTrue(user1.getUserId() == 1 && user1.getLogin().equals("user1") && user1.getPassword().equals("user1Password"));
        }

        @Test
        public void testAddUser() throws Exception {
            Integer sizeBefore = userDao.getAllUsers().size();
            User user = new User(null, "login", "password", new Date(), new Date());


            Integer userId = userDao.addUser(user);

            Integer sizeAfter = userDao.getAllUsers().size();

            assertTrue( (sizeAfter - sizeBefore) == 1);

            userDao.deleteUser(userId);
            sizeAfter = userDao.getAllUsers().size();
            assertTrue((sizeBefore - sizeAfter) == 0);
          }

        @Test
        public void testChangeUserLogin () throws Exception {
            Integer testId = 4;
            User user = new User();
            user.setLogin("user" + testId);
            user.setPassword("user" + testId + "Password");

            Integer userId = userDao.addUser(user);

            String login = "newLogin";
            userDao.changeUserLogin(userId, login);

            assertTrue(userDao.getUserById(userId).getLogin().equals(login));

        }

        @Test
        public void testChangeUserPassword () throws Exception {
            Integer testId = 4;
            User user = new User();
            user.setLogin("user" + testId);
            user.setPassword("user" + testId + "Password");

            Integer userId = userDao.addUser(user);

            String password = "newPassword";
            userDao.changeUserPassword(userId, password);

            assertTrue(userDao.getUserById(userId).getPassword().equals(password));

        }

        @Test
        public void testGetUserByLogin() throws Exception {
            User user = userDao.getUserByLogin("user1");
            assertNotNull(user);
            assertEquals("user1", user.getLogin());
        }
}