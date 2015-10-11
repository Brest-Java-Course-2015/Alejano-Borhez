package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.SplittableRandom;

/**
 * Created by alexander on 7.10.15.
 */
public class UserDaoImpl implements UserDao {


    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${user.select}") private String userSelect;
    @Value("${user.selectById}") private String userSelectById;
    @Value("${user.delete}") private String userDelete;
    @Value("${user.insert}") private String userInsert;
    @Value("${user.changeLogin}") private String userChangeLogin;
    @Value("${user.changePassword}") private String userChangePassword;
    @Value("${user.selectLogin}") private String userSelectLogin;

    private static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getInt("userId"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));

            return user;
        }
    }

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("Starting method getAllUsers");
        return jdbcTemplate.query(userSelect, new UserMapper());

    }

    @Override
    public boolean isThereAUser (Integer id) {
        try
        {
        LOGGER.info("Starting method isThereAUser");
            String login = jdbcTemplate.queryForObject(userSelectLogin, new Object[]{id}, String.class);
        if (login != null)
        {
            LOGGER.info("User with id: {} found! It's login is {}", id, login);
            return true;
        }
        else
        {
            LOGGER.info("There is no user with id: {}", id);
            return false;
        }
        }
        catch (EmptyResultDataAccessException e)
        {
            LOGGER.info("There is no user with id: {}", id);
            return false;
        }
    }

    @Override
    public User getUserById(Integer id) {
        LOGGER.info("Starting method getUserById with id: {}", id);
        if (isThereAUser(id))
        return jdbcTemplate.queryForObject(userSelectById, new  Object[]{id}, new UserMapper());
        else
        {
            return null;
        }
    }

    @Override

    public void insertUser(User user) {
        LOGGER.info("Starting method insertUser");
        if (!isThereAUser(user.getUserId()))
        {
            jdbcTemplate.update(userInsert, user.getUserId(), user.getLogin(), user.getPassword());
            LOGGER.info("New user with id: {} successfully inserted!", user.getUserId());
        }
        else
        {
            LOGGER.info("Couldn't insert a user with id: {}", user.getUserId());
        }
    }

    @Override
    public void deleteUser(Integer id) {
        LOGGER.info("Starting method deleteUser with id: {}", id);
        if (isThereAUser(id)) {
            jdbcTemplate.update(userDelete, id);
            LOGGER.info("User with id: {} was successfully deleted!", id);
        }
        else
        {
            LOGGER.info("Couldn't delete a user with id: {}", id);
        }
    }

    @Override
    public void changeUserLogin (Integer id, String login) {
        LOGGER.info("Starting method changeUserLogin with id: {} to new login: {}", id, login);
        if (isThereAUser(id)) {
            jdbcTemplate.update(userChangeLogin, login, id);
            LOGGER.info("Login of user with id: {} was successfully changed to {}", id, login);
        }
        else
        {
            LOGGER.info("Couldn't change a user login with id: {}", id);
        }
    }

    @Override
    public void changeUserPassword (Integer id, String password) {
        LOGGER.info("Starting method changeUserPassword with id: {} to new password: {}", id, password);
        if (isThereAUser(id)) {
            jdbcTemplate.update(userChangePassword, password, id);
            LOGGER.info("Password of user with id: {} was successfully changed to {}", id, password);
        }
        else
        {
            LOGGER.info("Couldn't change a user password with id: {}", id);
        }


    }


}
