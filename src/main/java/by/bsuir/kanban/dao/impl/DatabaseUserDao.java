package by.bsuir.kanban.dao.impl;

import by.bsuir.kanban.dao.UserDao;
import by.bsuir.kanban.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Collections;

/**
 * Created by ulza1116 on 4/7/2017.
 */
@Repository
public class DatabaseUserDao implements UserDao {
    private static final String USERNAME = "username";
    private static final String GET_USER_QUERY = "select us_username, us_password, us_last_name, us_first_name, us_email, us_can_create_project from user where us_username = :username";

    private static final String GET_IMAGE_QUERY = "SELECT us_picture from user where us_username = :username";

    private static final RowMapper<User> userRowMapper = (resultSet, i) -> {
        User user = new User();

        user.setUsername(resultSet.getString(TableColumn.USERNAME));
        user.setPassword(resultSet.getString(TableColumn.PASSWORD));
        user.setFirstName(resultSet.getString(TableColumn.FIRST_NAME));
        user.setLastName(resultSet.getString(TableColumn.LAST_NAME));
        user.setEmail(resultSet.getString(TableColumn.EMAIL));
        user.setCanCreateProject(resultSet.getBoolean(TableColumn.CAN_CREATE_PROJECT));
        user.setAuthorities(Collections.emptyList());

        return user;
    };

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserByUsername(String username) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue(USERNAME, username);

        return namedParameterJdbcTemplate.queryForObject(GET_USER_QUERY, sqlParameterSource, userRowMapper);
    }

    @Override
    public String getImagePath(String username){
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue(USERNAME, username);

        return namedParameterJdbcTemplate.queryForObject(GET_IMAGE_QUERY, sqlParameterSource, String.class);
    }
}
