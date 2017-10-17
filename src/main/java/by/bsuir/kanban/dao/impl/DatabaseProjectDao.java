package by.bsuir.kanban.dao.impl;

import by.bsuir.kanban.dao.ProjectDao;
import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by vladislav on 08.04.17.
 */

//@Repository
public class DatabaseProjectDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String GET_USER_PROJECTS_QUERY = "SELECT pr_id, pr_name, pr_description, us_username, us_first_name, us_last_name, us_count, ta_count\n" +
            "FROM project INNER JOIN project_user ON pr_id=pu_project_id INNER JOIN user ON pr_lead=us_username \n" +
            "INNER JOIN (SELECT pr_id AS pp_iidd, count(*) us_count FROM project INNER JOIN project_user ON pr_id=pu_project_id WHERE pu_username=:username GROUP BY pr_id) AS uc \n" +
            "ON pp_iidd=pr_id\n" +
            "INNER JOIN (SELECT count(ta_project) ta_count, pu_project_id ppi FROM task\n" +
            "RIGHT JOIN project_user ON ta_project=pu_project_id WHERE pu_username=:username \n" +
            "GROUP BY pu_project_id) AS tsk\n" +
            "ON ppi=pr_id\n" +
            "WHERE pu_username=:username LIMIT :startFrom, :limit;";

    private static final String GET_PROJECT_QUERY = "select pr_id, pr_name, pr_description, pr_logo from project where pr_id = ?";

    private static final String INSERT_PROJECT_QUERY = "insert into project (pr_name, pr_lead, pr_company_id) values (?, ?, ?)";

    private static final String IS_PROJECT_OWNER_QUERY = "SELECT count(*) from project where pr_id = ? and pr_lead = ?;";

    private static final String IS_PROJECT_OWNER_BY_ROLE_QUERY = "select count(*) from project inner join role on ro_project_id=pr_id where ro_id = ? and pr_lead = ?";

    private final RowMapper<Project> projectRowMapper = (resultSet, i)->{
            Project project = new Project();

            project.setId(resultSet.getInt(TableColumn.PROJECT_ID));
            project.setName(resultSet.getString(TableColumn.PROJECT_NAME));
            project.setUsersNumber(resultSet.getInt(TableColumn.USER_COUNT));
            project.setDescription(resultSet.getString(TableColumn.PROJECT_DESCRIPTION));
            project.setTotalTaskCount(resultSet.getInt(TableColumn.TASK_COUNT));

            User lead = new User();

            lead.setUsername(resultSet.getString(TableColumn.USERNAME));
            lead.setFirstName(resultSet.getString(TableColumn.FIRST_NAME));
            lead.setLastName(resultSet.getString(TableColumn.LAST_NAME));

            project.setLead(lead);
            return project;
    };

    private static final RowMapper<Project> projectDetailsRowMapper = ((resultSet, i) -> {

            Project project = new Project();

            project.setId(resultSet.getInt(TableColumn.PROJECT_ID));
            project.setName(resultSet.getString(TableColumn.PROJECT_NAME));
            project.setDescription(resultSet.getString(TableColumn.PROJECT_DESCRIPTION));
            project.setLogo(resultSet.getString(TableColumn.PROJECT_LOGO));

            return project;
    });

    @Autowired
    public DatabaseProjectDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Project> getUsersProjects(String username, int limit, int startFrom){
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

        sqlParameterSource.addValue("username", username);
        sqlParameterSource.addValue("startFrom", startFrom);
        sqlParameterSource.addValue("limit", limit);

        return namedParameterJdbcTemplate.query(GET_USER_PROJECTS_QUERY, sqlParameterSource, projectRowMapper);
    }

   // @Override
    public Project getProject(int projectId){
        return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GET_PROJECT_QUERY, new Object[]{projectId}, projectDetailsRowMapper);
    }

    //@Override
    public int createProject(Project project){

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.getJdbcOperations().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT_QUERY, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, project.getName());
                preparedStatement.setString(2, project.getLead().getUsername());
                preparedStatement.setInt(3, project.getCompany().getId());

                return preparedStatement;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

   // @Override
    public boolean isProjectOwner(String username, int projectId) {
        int count = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(IS_PROJECT_OWNER_QUERY, new Object[]{projectId, username}, Integer.class);

        return 0 < count;
    }

   // @Override
    public boolean isProjectOwnerByRole(String username, int roleId){
        int count = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(IS_PROJECT_OWNER_BY_ROLE_QUERY, new Object[]{roleId, username}, Integer.class);

        return 0 < count;
    }



}

