package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexander on 7.10.15.
 */
public class UserDaoImpl implements UserDao {


    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${user.select}")
    private String userSelect;
    @Value("${user.selectById}")
    private String userSelectById;

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
        return jdbcTemplate.query(userSelect, new UserMapper());
    }

    @Override
    public User getUserById(Integer id) {
        LOGGER.info("id: {}", id);
        return jdbcTemplate.queryForObject(userSelectById, new  Object[]{id}, new UserMapper());
    }

    @Override
    public void insertUser(User user) {
        jdbcTemplate.update("insert into user (userId, login, password) values (?, ?, ?)", user.getUserId(), user.getLogin(), user.getPassword());
    }

    @Override
    public void deleteUser(Integer id) {
        jdbcTemplate.update("delete from user where userId=?", id);
    }

    @Override
    public void changeUserLogin (Integer id, String login) {
        jdbcTemplate.update("update user set login = ? where userId = ?", login, id);
    }

    @Override
    public void changeUserPassword (Integer id, String password) {}


}
