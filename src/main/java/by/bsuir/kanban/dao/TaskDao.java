package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.Status;
import by.bsuir.kanban.domain.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by vladislav on 09.04.17.
 */
public interface TaskDao extends JpaRepository<Task, Integer> {

    @Query("select (count (ta) > 0) from Task ta join ta.taskCreator tc where ta.id = :taskId and tc.username = :username")
    boolean isTaskCreator(@Param("username") String username, @Param("taskId") int taskId);

    List<Task> getTaskByTaskStatusAndProject(Status status, Project project, Pageable pageable);
}
