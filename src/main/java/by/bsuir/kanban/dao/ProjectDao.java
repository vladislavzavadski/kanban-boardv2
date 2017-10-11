package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Project;

import java.util.List;

/**
 * Created by vladislav on 08.04.17.
 */
public interface ProjectDao {
    List<Project> getUsersProjects(String username, int limit, int startFrom);

    Project getProject(int projectId);

    int createProject(Project project);

    boolean isProjectOwner(String username, int projectId);

    boolean isProjectOwnerByRole(String username, int roleId);
}
