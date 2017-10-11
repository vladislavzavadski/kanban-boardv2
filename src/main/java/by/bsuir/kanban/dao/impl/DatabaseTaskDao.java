package by.bsuir.kanban.dao.impl;

import by.bsuir.kanban.dao.TaskDao;
import by.bsuir.kanban.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Set;

/**
 * Created by vladislav on 09.04.17.
 */
@Repository
public class DatabaseTaskDao implements TaskDao {

    private static final String GET_PROJECT_TASKS_QUERY = "select ta_id, ta_name, ta_summary, ta_priority, ta_status, ta_order, ts_order, ts_name from task inner join task_status on ts_id=ta_status where ta_project = :projectId and ta_status = :taskStatusId order by ts_order, ta_priority, ta_order limit :startFrom, :rowNumber;";

    private static final String SEARCH_TASKS = "select ta_id, ta_name, ta_summary, ta_priority, ta_status, ta_order, ts_order, ts_name from task where (ta_id in (select tt_task_id from tag_task inner join tag on tt_tag_id=lb_id where lb_name in (:tagName)) or ta_name = :taskName) and ta_project = :projectId;";

    private static final String GET_TASK_TAGS = "select lb_id, lb_name, lb_color, lb_project_id from tag inner join tag_task on tt_tag_id=lb_id where tt_task_id = ?;";

    private static final String INSERT_TASK_QUERY = "insert into task (ta_creator, ta_name, ta_summary, ta_priority, ta_status, ta_create_time, ta_project, ta_order) SELECT ?, ?, ?, ?, ?, NOW(), ?, count(*) + 1 from task where ta_project=? and ta_status = ?";

    private static final String UPDATE_TASK_STATUS_QUERY = "UPDATE task SET ta_status = :taskStatus.id, ta_order = :order where ta_id = :id";

    private static final String GET_TASK_ORDER = "select count(*) + 1 from task where ta_status=?";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Tag> tagRowMapper = (resultSet, i) -> {
      Tag tag = new Tag();

      tag.setColor(resultSet.getString("lb_color"));
      tag.setTagName(resultSet.getString("lb_name"));
      tag.setId(resultSet.getInt("lb_id"));

      Project project = new Project();

      project.setId(resultSet.getInt("lb_project_id"));
      tag.setProject(project);

      return tag;
    };

    private static final RowMapper<Task> taskRowMapper = ((resultSet, i) -> {
        Task task = new Task();

        task.setId(resultSet.getInt(TableColumn.TASK_ID));
        task.setName(resultSet.getString(TableColumn.TASK_NAME));
        task.setSummary(resultSet.getString(TableColumn.TASK_SUMMARY));
        task.setPriority(Priority.valueOf(resultSet.getString(TableColumn.TASK_PRIORITY)));
        task.setOrder(resultSet.getInt(TableColumn.TASK_ORDER));

        Status status = new Status();

        status.setName(resultSet.getString(TableColumn.TASK_STATUS_NAME));
        status.setOrder(resultSet.getInt(TableColumn.TASK_STATUS_ORDER));
        status.setId(resultSet.getInt(TableColumn.TASK_STATUS_ID));

        task.setTaskStatus(status);

        return task;
    });

    @Autowired
    public DatabaseTaskDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Task> getProjectTasks(int projectId, int taskStatusId, int startFrom, int limit) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource.addValue(ParameterName.PROJECT_ID, projectId);
        mapSqlParameterSource.addValue(ParameterName.TASK_STATUS_ID, taskStatusId);
        mapSqlParameterSource.addValue(ParameterName.START_FROM, startFrom);
        mapSqlParameterSource.addValue(ParameterName.ROW_NUMBER, limit);

        return namedParameterJdbcTemplate.query(GET_PROJECT_TASKS_QUERY, mapSqlParameterSource, taskRowMapper);
    }

    @Override
    public List<Task> searchTasks(Set<Tag> tags, String taskName, int projectId) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource.addValue(ParameterName.TAG_NAME, tags);
        mapSqlParameterSource.addValue(ParameterName.TASK_NAME, taskName);
        mapSqlParameterSource.addValue(ParameterName.PROJECT_ID, projectId);

        return namedParameterJdbcTemplate.query(SEARCH_TASKS, mapSqlParameterSource, taskRowMapper);
    }

    @Override
    public List<Tag> getTaskTags(int taskId) {

        return namedParameterJdbcTemplate.getJdbcOperations().query(GET_TASK_TAGS, new Object[]{taskId}, tagRowMapper);
    }

    @Override
    public void insertTask(Task task, int projectId){

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.getJdbcOperations().update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, task.getTaskCreator().getUsername());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setString(3, task.getSummary());
            preparedStatement.setString(4, task.getPriority().toString());
            preparedStatement.setInt(5, task.getTaskStatus().getId());
            preparedStatement.setInt(6, projectId);
            preparedStatement.setInt(7, projectId);
            preparedStatement.setInt(8, task.getTaskStatus().getId());

            return preparedStatement;
        }, keyHolder);

        task.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void updateTaskStatus(Task task){

        int order = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GET_TASK_ORDER, new Object[]{task.getTaskStatus().getId()}, Integer.class);

        task.setOrder(order);

        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(task);

        namedParameterJdbcTemplate.update(UPDATE_TASK_STATUS_QUERY, beanPropertySqlParameterSource);
    }
}
