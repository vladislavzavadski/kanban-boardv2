package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Tag;
import by.bsuir.kanban.domain.Task;

import java.util.List;
import java.util.Set;

/**
 * Created by vladislav on 09.04.17.
 */
public interface TaskDao {
    List<Task> getProjectTasks(int projectId, int taskStatusId, int startFrom, int limit);
    List<Task> searchTasks(Set<Tag> tags, String taskName, int projectId);
    List<Tag> getTaskTags(int taskId);

    void insertTask(Task task, int projectId);

    void updateTaskStatus(Task task);
}
