package by.bsuir.kanban.service;

import by.bsuir.kanban.domain.Status;
import by.bsuir.kanban.domain.Tag;
import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.to.StatusDTO;

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
}
