package by.bsuir.kanban.dao.impl;

import by.bsuir.kanban.dao.TaskStatusDao;
import by.bsuir.kanban.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vladislav on 09.04.17.
 */
@Repository
public class DatabaseTaskStatusDao implements TaskStatusDao {

    private static final String GET_AVAILABLE_STATUS_QUERY = "SELECT ts_id as ta_status, ts_name, ts_order from task_status WHERE ts_project = ? ORDER BY ts_order;";

    private static final String CREATE_STATUS_QUERY = "insert into task_status (ts_name, ts_project, ts_order) (select :name, :project.id,  count(ts_order)+1 from task_status where ts_project = :project.id)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final RowMapper<Status> taskStatusRowMapper = ((resultSet, i) -> {
        Status status = new Status();

        status.setId(resultSet.getInt(TableColumn.TASK_STATUS_ID));
        status.setName(resultSet.getString(TableColumn.TASK_STATUS_NAME));
        status.setOrder(resultSet.getInt(TableColumn.TASK_STATUS_ORDER));

        return status;
    });

    @Autowired
    public DatabaseTaskStatusDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Status> getAvailableProjectStatus(int projectId) {

        return namedParameterJdbcTemplate.getJdbcOperations().query(GET_AVAILABLE_STATUS_QUERY, new Object[]{projectId}, taskStatusRowMapper);
    }

    @Override
    public void createStatus(Status status){

        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(status);

        namedParameterJdbcTemplate.update(CREATE_STATUS_QUERY, beanPropertySqlParameterSource);
    }

}
