package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vladislav on 10.06.17.
 */
public interface TaskHistoryDao extends JpaRepository<TaskHistory, Integer>{

}
