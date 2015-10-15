package com.epam.brest.course2015.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by alexander on 5.10.15.
 */
public class UserTest {
    private User user;
    @Before
    public void setUp() throws Exception{
        user = new User();
    }

    @Test
    public void testGetLogin() throws Exception {
        user.setLogin("LOGIN");
        assertNotNull(user.getLogin());
        assertEquals("LOGIN", user.getLogin());
    }

    @Test
    public void testGetPassword() throws Exception {
        user.setPassword("PASSWORD");
        assertEquals("PASSWORD", user.getPassword());
    }

    @Test
    public void testGetUserId() throws Exception {
        user.setUserId(25);
        assertEquals(25, user.getUserId(), 0);
    }

    @Test
    public void testGetCreatedDate() throws Exception {
        user.setCreatedDate(new Date(123456789));
        assertEquals(new Date(123456789), user.getCreatedDate());
    }

    @Test
    public void testGetUpdatedDate() throws Exception {
        Date date = new Date();
        user.setUpdatedDate(date);
        assertEquals(date, user.getUpdatedDate());

    }

}