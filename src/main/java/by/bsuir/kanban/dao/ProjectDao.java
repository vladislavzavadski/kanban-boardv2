package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vladislav on 08.04.17.
 */
@Repository
public interface ProjectDao extends JpaRepository<Project, Integer>{

    @Query("select project from Project project where :user member project.users")
    List<Project> getUsersProjects(@Param("user") User user, Pageable pageable);

//    Project getProject(int projectId);
//
//    int createProject(Project project);
//
//    boolean isProjectOwner(String username, int projectId);
//
//    boolean isProjectOwnerByRole(String username, int roleId);
}
