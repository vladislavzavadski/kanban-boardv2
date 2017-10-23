package by.bsuir.kanban.service;

import by.bsuir.kanban.domain.Status;
import by.bsuir.kanban.domain.Tag;
import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.to.StatusDTO;
import by.bsuir.kanban.service.exception.StatusNotFoundException;
import by.bsuir.kanban.service.exception.TaskNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

/**
 * Created by vladislav on 22.05.17.
 */
public interface TaskService {
    List<Task> searchTasks(Set<Tag> tags, String taskName, int projectId);

    List<StatusDTO> getProjectTaskStatuses(int projectId);

    void createTaskStatus(Status status);

    void createTask(Task task);

    void changeTaskStatus(Task task);

    void deleteTaskStatus(int statusId) throws StatusNotFoundException;

    @PreAuthorize("isAuthenticated()")
    void deleteTask(int taskId) throws TaskNotFoundException;
}
