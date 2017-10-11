package by.bsuir.kanban.dao.impl;

import by.bsuir.kanban.dao.RoleDao;
import by.bsuir.kanban.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ulza1116 on 6/12/2017.
 */
@Repository
public class DatabaseRoleDao implements RoleDao {

    private static final String GET_PROJECT_ROLES = "SELECT ro_id, ro_name, ro_project_id AS pr_id FROM role WHERE ro_project_id = ?;";
    private static final String INSERT_ROLE_QUERY = "INSERT INTO role (ro_name, ro_project_id) VALUES (:name, :project.id)";
    private static final String SELECT_ROLE_DETAILS = "SELECT us_username, us_first_name, us_last_name, us_picture FROM user INNER JOIN role_user ON user.us_username = role_user.ru_username WHERE ru_role_id=?";
    private static final String ASSIGN_TO_ROLE_QUERY = "INSERT INTO role_user (ru_username, ru_role_id) VALUES (:user.username, role.id);";
    private static final String SET_PERMISSION_QUERY = "INSERT INTO role_permission (rp_role_id, rp_permission_id) VALUES (?, ?)";

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private static final RowMapper<Role> roleRowMapper = (resultSet, i) -> {

        Role role = new Role();

        role.setId(resultSet.getInt(TableColumn.ROLE_ID));
        role.setName(resultSet.getString(TableColumn.ROLE_NAME));

        Project project = new Project();

        project.setId(resultSet.getInt(TableColumn.PROJECT_ID));

        role.setProject(project);

        return role;
    };

    private final RowMapper<User> userRowMapper;

    @Autowired
    public DatabaseRoleDao(NamedParameterJdbcOperations namedParameterJdbcOperations, RowMapper<User> userRowMapper) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<Role> getProjectGroups(int projectId) {
        return namedParameterJdbcOperations.getJdbcOperations().query(GET_PROJECT_ROLES, new Object[]{projectId}, roleRowMapper);
    }

    @Override
    public void createRole(Role role) {
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(role);

        namedParameterJdbcOperations.update(INSERT_ROLE_QUERY, beanPropertySqlParameterSource);
    }

    @Override
    public List<User> selectRoleParticipants(int roleId) {
        return namedParameterJdbcOperations.getJdbcOperations().query(SELECT_ROLE_DETAILS, new Object[]{roleId}, userRowMapper);
    }

    @Override
    public void assignToRole(RoleUser roleUser) {
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(roleUser);

        namedParameterJdbcOperations.update(ASSIGN_TO_ROLE_QUERY, beanPropertySqlParameterSource);
    }

    @Override
    public void setPermission(Permission permission, int roleId) {
        namedParameterJdbcOperations.getJdbcOperations().update(SET_PERMISSION_QUERY, roleId, permission.getId());
    }

}
