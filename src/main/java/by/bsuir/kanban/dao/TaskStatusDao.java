package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by vladislav on 09.04.17.
 */
public interface TaskStatusDao extends JpaRepository<Status, Integer>{

}
