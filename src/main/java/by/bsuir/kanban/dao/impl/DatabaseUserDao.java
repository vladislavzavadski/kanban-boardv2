package by.bsuir.kanban.dao.impl;

import by.bsuir.kanban.dao.UserDao;
import by.bsuir.kanban.domain.Company;
import by.bsuir.kanban.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by ulza1116 on 4/7/2017.
 */
@Repository
public class DatabaseUserDao implements UserDao {

    private static final String GET_USER_QUERY = "select us_username, us_password, us_last_name, us_first_name, us_email, us_can_create_project, co_id, co_name from user inner JOIN company on co_id=us_company_id where us_username = ?";

    private static final String GET_IMAGE_QUERY = "SELECT us_picture from user where us_username = ?";

    private static final String GET_USERS_ON_PROJECT_QUERY = "select us_first_name, us_last_name, us_username, us_picture from user inner join project_user on pu_username=us_username where pu_project_id = :projectId order by us_last_name limit :startFrom, :rowNumber";

    private static final String INSERT_USER_QUERY = "insert into user (us_username, us_email, us_first_name, us_last_name, us_password, us_picture, us_can_create_project, us_company_id) VALUES (:username, :email, :firstName, :lastName, :password, :picture, :canCreateProject, :company.id);";

    private static final String UPDATE_USER_QUERY = "UPDATE user SET us_first_name = :firstName, us_last_name = :lastName, us_password = :password, us_email = :email WHERE us_username = :username";

    private static final String SELECT_USERS_FROM_COMPANY = "select us_username, us_first_name, us_last_name, us_picture from user where us_company_id=?;";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<User> userRowMapper = ((resultSet, i) -> {
        User user = new User();

        user.setUsername(resultSet.getString(TableColumn.USERNAME));
        user.setPassword(resultSet.getString(TableColumn.PASSWORD));
        user.setFirstName(resultSet.getString(TableColumn.FIRST_NAME));
        user.setLastName(resultSet.getString(TableColumn.LAST_NAME));
        user.setEmail(resultSet.getString(TableColumn.EMAIL));
        user.setCanCreateProject(resultSet.getInt(TableColumn.CREATE_PROJECT) != 0);
        user.setAuthorities(Collections.emptyList());

        Company company = new Company();

        company.setId(resultSet.getInt(TableColumn.COMPANY_ID));
        company.setName(resultSet.getString(TableColumn.COMPANY_NAME));

        user.setCompany(company);

        return user;
    });

    private final RowMapper<User> userOnProjectRowMapper;

    @Autowired
    public DatabaseUserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, RowMapper<User> userRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userOnProjectRowMapper = userRowMapper;
    }

    public User getUserByUsername(String username) {
        try {
            return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GET_USER_QUERY, new Object[]{username}, userRowMapper);
        }
        catch (EmptyResultDataAccessException ex){
            return null;
        }

    }

    public String getImagePath(String username){

        try {
            return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GET_IMAGE_QUERY, new Object[]{username}, String.class);
        }
        catch (EmptyResultDataAccessException ex){
            return null;
        }

    }

    @Override
    public List<User> getUsersOnProject(int projectId, int startFrom, int limit){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource.addValue(ParameterName.PROJECT_ID, projectId);
        mapSqlParameterSource.addValue(ParameterName.START_FROM, startFrom);
        mapSqlParameterSource.addValue(ParameterName.ROW_NUMBER, limit);

        return namedParameterJdbcTemplate.query(GET_USERS_ON_PROJECT_QUERY, mapSqlParameterSource, userOnProjectRowMapper);
    }

    @Override
    public void createUser(User user){
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(user);

        namedParameterJdbcTemplate.update(INSERT_USER_QUERY, sqlParameterSource);
    }

    @Override
    public void updateUser(User user){
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(user);

        namedParameterJdbcTemplate.update(UPDATE_USER_QUERY, sqlParameterSource);
    }

    @Override
    public List<User> selectUsersByCompany(int companyId){
        return namedParameterJdbcTemplate.getJdbcOperations().query(SELECT_USERS_FROM_COMPANY, new Object[]{companyId}, userOnProjectRowMapper);
    }

}
