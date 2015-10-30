package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Value("${user.select}")
    private String userSelect;

    @Value("${user.countUsers}")
    private String countUser;

    @Value("${user.selectById}")
    private String userSelectById;

    @Value("${user.selectByLogin}")
    private String userSelectByLogin;

    @Value("${user.insertUser}")
    private String insertUser;

    @Value("${user.updateUser}")
    private String updateUser;

    @Value("${user.deleteUser}")
    private String deleteUser;

    @Value("${user.totalCountUsers}")
    private String totalUsersCountSql;

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("getAllUsers()");
        return jdbcTemplate.query(userSelect, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer userId) {
        LOGGER.debug("getUserById({})", userId);
        return jdbcTemplate.queryForObject(userSelectById, new Object[]{userId}, new UserRowMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({})", login);
        return jdbcTemplate.queryForObject(userSelectByLogin, new Object[]{login}, new UserRowMapper());
    }

    @Override
    public Integer addUser(User user) {
        LOGGER.debug("addUser(user): login = {}", user.getLogin());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertUser, getParametersMap(user), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("updateUser(user): {}", user.getLogin());
        jdbcTemplate.update(updateUser, new Object[]{user.getPassword(), user.getUserId()});
    }

    @Override
    public void deleteUser(Integer userId) {
        LOGGER.debug("deleteUser(): {}", userId);
        jdbcTemplate.update(deleteUser, new Object[]{userId});
    }

    private MapSqlParameterSource getParametersMap(User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_ID.getValue(), user.getUserId());
        parameterSource.addValue(LOGIN.getValue(), user.getLogin());
        parameterSource.addValue(PASSWORD.getValue(), user.getPassword());
        parameterSource.addValue(CREATED_DATE.getValue(), user.getCreatedDate());
        parameterSource.addValue(UPDATED_DATE.getValue(), user.getUpdatedDate());
        return parameterSource;
    }

    @Override
    public Integer getCountUsers(String login) {
        LOGGER.debug("getCountUsers(): login = {}", login);
        return jdbcTemplate.queryForObject(countUser, new String[]{login}, Integer.class);
    }

    @Override
    public Integer getTotalCountUsers() {
        LOGGER.debug("getTotalUsersCount()");
        return jdbcTemplate.queryForObject(totalUsersCountSql, Integer.class);
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(resultSet.getInt(USER_ID.getValue()),
                    resultSet.getString(LOGIN.getValue()),
                    resultSet.getString(PASSWORD.getValue()),
                    resultSet.getTimestamp(CREATED_DATE.getValue()),
                    resultSet.getTimestamp(UPDATED_DATE.getValue()));
            return user;
        }
    }

}
