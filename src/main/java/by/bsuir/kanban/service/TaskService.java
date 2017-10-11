package by.bsuir.kanban.service;

import by.bsuir.kanban.domain.Status;
import by.bsuir.kanban.domain.Tag;
import by.bsuir.kanban.domain.Task;

import java.util.List;
import java.util.Set;

/**
 * Created by vladislav on 22.05.17.
 */
public interface TaskService {
    List<Task> searchTasks(Set<Tag> tags, String taskName, int projectId);

    List<Status> getProjectTaskStatuses(int projectId);

    void createTaskStatus(Status status);

    void createTask(Task task, int projectId);

    void changeTaskStatus(Task task);
}
