package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    @Value("${user.id}") private String user_id;
    @Value("${user.login}") private String user_login;
    @Value("${user.password}") private String user_password;

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

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("Starting method getAllUsers");
        return namedParameterJdbcTemplate.query(userSelect, new UserMapper());

    }

    @Override
    public boolean isThereAUser (Integer id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(user_id, id);
        try
        {
        LOGGER.info("Starting method isThereAUser");
        String login = namedParameterJdbcTemplate.queryForObject(userSelectLogin, namedParameters, String.class);
        LOGGER.info("User with id: {} found! It's login is {}", id, login);
        return true;
        }
        catch (EmptyResultDataAccessException e)
        {
            LOGGER.info("There is no user with id: {}", id);
            return false;
        }
    }

    @Override
    public User getUserById(Integer id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(user_id, id);

        LOGGER.info("Starting method getUserById with id: {}", id);
        if (isThereAUser(id))
        return namedParameterJdbcTemplate.queryForObject(userSelectById, namedParameters, new UserMapper());
        else
        {
            return null;
        }
    }

    @Override

    public void insertUser(User user) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
        LOGGER.info("Starting method insertUser");
        if (!isThereAUser(user.getUserId()))
        {
            namedParameterJdbcTemplate.update(userInsert, namedParameters);
            LOGGER.info("New user with id: {} successfully inserted!", user.getUserId());
        }
        else
        {
            LOGGER.info("Couldn't insert a user with id: {}", user.getUserId());
        }
    }

    @Override
    public void deleteUser(Integer id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(user_id, id);

        LOGGER.info("Starting method deleteUser with id: {}", id);
        if (isThereAUser(id)) {
            namedParameterJdbcTemplate.update(userDelete, namedParameters);
            LOGGER.info("User with id: {} was successfully deleted!", id);
        }
        else
        {
            LOGGER.info("Couldn't delete a user with id: {}", id);
        }
    }

    @Override
    public void changeUserLogin (Integer id, String login) {
        User user = new User();
        user.setUserId(id);
        user.setLogin(login);
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);

        LOGGER.info("Starting method changeUserLogin with id: {} to new login: {}", id, login);
        if (isThereAUser(id)) {
            namedParameterJdbcTemplate.update(userChangeLogin, namedParameters);
            LOGGER.info("Login of user with id: {} was successfully changed to {}", id, login);
        }
        else
        {
            LOGGER.info("Couldn't change a user login with id: {}", id);
        }
    }

    @Override
    public void changeUserPassword (Integer id, String password) {
        User user = new User();
        user.setUserId(id);
        user.setPassword(password);

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);

        LOGGER.info("Starting method changeUserPassword with id: {} to new password: {}", id, password);
        if (isThereAUser(id)) {
            namedParameterJdbcTemplate.update(userChangePassword, namedParameters);
            LOGGER.info("Password of user with id: {} was successfully changed to {}", id, password);
        }
        else
        {
            LOGGER.info("Couldn't change a user password with id: {}", id);
        }


    }


}
