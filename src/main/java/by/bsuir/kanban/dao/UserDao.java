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

    @Query("select count (us) from User us join us.projects proj where us.username = :username and proj.id = :id")
    int isAssignedOnProject(@Param("username") String username, @Param("id")int projectId);

    @Query("select count (us) from User us join us.ownProjects proj where us.username = :username and proj.id = :id")
    int isProjectOwner(@Param("username") String username, @Param("id") int projectId);
}
