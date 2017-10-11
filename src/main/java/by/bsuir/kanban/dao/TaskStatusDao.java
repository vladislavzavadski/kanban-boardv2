package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Status;

import java.util.List;

/**
 * Created by vladislav on 09.04.17.
 */
public interface TaskStatusDao {
    List<Status> getAvailableProjectStatus(int projectId);

    void createStatus(Status status);
}
