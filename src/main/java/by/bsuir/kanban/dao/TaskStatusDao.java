package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by vladislav on 09.04.17.
 */
public interface TaskStatusDao extends JpaRepository<Status, Integer>{

    @Query("select max (st.order) from Status st where st.project = :project")
    Integer getMaxOrderStatusNumber(@Param("project")Project project);

    @Modifying
    @Query(value = "update task_status st set st.status_order = st.status_order - 1 where st.project_id = :projectId and st.status_order > :deletedOrder",nativeQuery = true)
    void updateStatusOrderAfterDelete(@Param("projectId") int projectId, @Param("deletedOrder") int order);

}
