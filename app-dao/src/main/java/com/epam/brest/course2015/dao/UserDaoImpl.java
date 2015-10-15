package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.epam.brest.course2015.domain.User.UserFields.*;
/**
 * Created by alexander on 7.10.15.
 */
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${user.select}") private String userSelect;
    @Value("${user.selectById}") private String userSelectById;
    @Value("${user.selectByLogin}") private String userSelectByLogin;
    @Value("${user.delete}") private String userDelete;
    @Value("${user.insert}") private String userInsert;
    @Value("${user.changeLogin}") private String userChangeLogin;
    @Value("${user.changePassword}") private String userChangePassword;
    @Value("${user.selectLogin}") private String userSelectLogin;
    @Value("${user.id}") private String user_id;
    @Value("${user.login}") private String user_login;
    @Value("${user.password}") private String user_password;

    //private RowMapper<User> userMapper = new BeanPropertyRowMapper<>(User.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("Starting method getAllUsers");
        return namedParameterJdbcTemplate.query(userSelect, new UserRowMapper());

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
        return namedParameterJdbcTemplate.queryForObject(userSelectById, namedParameters, new UserRowMapper());
        else
        {
            return null;
        }
    }

    @Override
    public User getUserByLogin(String login) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(user_login, login);

        LOGGER.info("Starting method getUserByLogin with login:{}", login);
        return namedParameterJdbcTemplate.queryForObject(userSelectByLogin, namedParameters, new UserRowMapper());
    }

    @Override
    public Integer addUser(User user) {
        // KeyHolder
        KeyHolder keyHolder = new GeneratedKeyHolder();
        user.setUpdatedDate(new Date());
        LOGGER.info("Starting method addUser");
        namedParameterJdbcTemplate.update(userInsert, getParametersMap(user), keyHolder);
        LOGGER.info("New user with id: {} successfully inserted!", keyHolder.getKey().intValue());
        return keyHolder.getKey().intValue();

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

        LOGGER.info("Starting method changeUserLogin with id: {} to new login: {}", id, login);
        if (isThereAUser(id)) {
            namedParameterJdbcTemplate.update(userChangeLogin, getParametersMap(user));
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

        LOGGER.info("Starting method changeUserPassword with id: {} to new password: {}", id, password);
        if (isThereAUser(id)) {
            namedParameterJdbcTemplate.update(userChangePassword, getParametersMap(user));
            LOGGER.info("Password of user with id: {} was successfully changed to {}", id, password);
        }
        else
        {
            LOGGER.info("Couldn't change a user password with id: {}", id);
        }


    }

    private MapSqlParameterSource getParametersMap (User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID.getValue(), user.getUserId());
        parameterSource.addValue(LOGIN.getValue(), user.getLogin());
        parameterSource.addValue(PASSWORD.getValue(), user.getPassword());
        parameterSource.addValue(CREATED_DATE.getValue(), user.getCreatedDate());
        parameterSource.addValue(UPDATED_DATE.getValue(), user.getUpdatedDate());

        return parameterSource;
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow (ResultSet resultSet, int i) throws SQLException {
            User user = new User(
                    resultSet.getInt(USER_ID.getValue()),
                    resultSet.getString(LOGIN.getValue()),
                    resultSet.getString(PASSWORD.getValue()),
                    resultSet.getTimestamp(CREATED_DATE.getValue()),
                    resultSet.getTimestamp(UPDATED_DATE.getValue())
                    );
            return user;

        }
    }

}
