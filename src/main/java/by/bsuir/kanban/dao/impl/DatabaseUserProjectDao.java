package by.bsuir.kanban.dao.impl;

import by.bsuir.kanban.dao.UserProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by vladislav on 09.04.17.
 */
@Repository
public class DatabaseUserProjectDao implements UserProjectDao {

    private static final String IS_ASSIGN_QUERY = "SELECT count(*) from project_user WHERE pu_project_id = :projectId and pu_username = :username;";

    private static final String ASSIGN_ON_PROJECT = "insert into project_user (pu_username, pu_project_id) VALUES (?, ?);";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DatabaseUserProjectDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean isUserAssignOnProject(String username, int projectId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(ParameterName.PROJECT_ID, projectId);
        mapSqlParameterSource.addValue(ParameterName.USERNAME, username);

        return namedParameterJdbcTemplate.queryForObject(IS_ASSIGN_QUERY, mapSqlParameterSource, Integer.class) != 0;
    }

    @Override
    public void addUserOnProject(String username, int projectId){
        namedParameterJdbcTemplate.getJdbcOperations().update(ASSIGN_ON_PROJECT, username, projectId);
    }
}
