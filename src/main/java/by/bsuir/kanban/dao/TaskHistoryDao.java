package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.User;

/**
 * Created by vladislav on 10.06.17.
 */
public interface TaskHistoryDao {
    void logTaskChange(Task task, User changer);
}
