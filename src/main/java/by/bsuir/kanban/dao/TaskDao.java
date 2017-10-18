package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vladislav on 09.04.17.
 */
public interface TaskDao extends JpaRepository<Task, Integer> {

}
