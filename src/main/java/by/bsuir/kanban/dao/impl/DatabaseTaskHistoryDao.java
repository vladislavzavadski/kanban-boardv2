package by.bsuir.kanban.dao.impl;

import by.bsuir.kanban.dao.TaskHistoryDao;
import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by vladislav on 10.06.17.
 */
@Repository
public class DatabaseTaskHistoryDao implements TaskHistoryDao {

    private static final String INSERT_HISTORY_QUERY = "INSERT INTO task_history (th_time, th_task_status, th_task_id, th_username) VALUES (NOW(), :taskStatusId, :taskId, :username)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DatabaseTaskHistoryDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void logTaskChange(Task task, User changer){

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource.addValue(ParameterName.TASK_STATUS_ID, task.getTaskStatus().getId());
        mapSqlParameterSource.addValue(ParameterName.TASK_ID, task.getId());
        mapSqlParameterSource.addValue(ParameterName.USERNAME, changer.getUsername());

        namedParameterJdbcTemplate.update(INSERT_HISTORY_QUERY, mapSqlParameterSource);
    }

}
