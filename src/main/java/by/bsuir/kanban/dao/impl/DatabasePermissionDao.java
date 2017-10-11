package by.bsuir.kanban.dao.impl;

import by.bsuir.kanban.dao.PermissionDao;
import by.bsuir.kanban.domain.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by ulza1116 on 6/12/2017.
 */

@Repository
public class DatabasePermissionDao  implements PermissionDao {

    private static final String INSERT_PERMISSION_QUERY = "INSERT INTO permission (pe_project_id, pe_type) VALUES (:project.id, :permissionType)";

    private static final String INSERT_CHANGE_STATUS_PERMISSION = "INSERT INTO change_status_permission (csp_perm_id, csp_status_from, csp_status_to) VALUES (:id, :changeStatusPermission.from.id, :changeStatusPermission.to.id)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DatabasePermissionDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void insertPermission(Permission permission){

        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(permission);

        namedParameterJdbcTemplate.update(INSERT_PERMISSION_QUERY, beanPropertySqlParameterSource);
    }

    @Override
    public void insertChangeStatusPermission(Permission permission){
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(permission);

        namedParameterJdbcTemplate.update(INSERT_CHANGE_STATUS_PERMISSION, beanPropertySqlParameterSource);
    }


}
