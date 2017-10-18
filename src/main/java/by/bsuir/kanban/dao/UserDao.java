package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by ulza1116 on 4/7/2017.
 */
@Repository
public interface UserDao extends JpaRepository<User, String>{

    User getUserByUsername(String username);

    @Query("select count (us) from User us join us.userProjects proj where us.username = :username and proj.project.id = :id")
    int isAssignedOnProject(@Param("username") String username, @Param("id")int projectId);

    @Query("select count (us) from User us join us.ownProjects proj where us.username = :username and proj.id = :id")
    int isProjectOwner(@Param("username") String username, @Param("id") int projectId);

    @Query("select count (us) from User us join us.userProjects up join up.project pr where pr.id = (select proj.id from Project proj join proj.tasks tasks where tasks.id = :taskId) and us.username = :username")
    int isAssignedOnProjectByTaskId(@Param("username") String username, @Param("taskId") int taskId);
}
