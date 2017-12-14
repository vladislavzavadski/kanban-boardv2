package by.bsuir.kanban.service;

import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.to.ComplexProjectDTO;
import by.bsuir.kanban.domain.to.SimpleProjectDTO;

import java.util.List;

/**
 * Created by vladislav on 08.04.17.
 */
public interface ProjectService {
    int TASK_START_FROM = 0;
    int TASK_LIMIT = 8;

    List<SimpleProjectDTO> getUserProjects(int limit, int startFrom);

    void createProject(Project project);

    boolean isProjectOwner(int projectId);

    ComplexProjectDTO getProject(int projectId);
}
