package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.dao.*;
import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.Status;
import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vladislav on 08.04.17.
 */
@Service
public class DefaultProjectService implements ProjectService {

    private final ProjectDao projectDao;

    private final TaskDao taskDao;

    private final TaskStatusDao taskStatusDao;

    private final UserDao userDao;

    private final UserProjectDao userProjectDao;

    @Autowired
    public DefaultProjectService(ProjectDao projectDao, TaskDao taskDao, TaskStatusDao taskStatusDao,
                                 UserDao userDao, UserProjectDao userProjectDao) {
        this.projectDao = projectDao;
        this.taskDao = taskDao;
        this.taskStatusDao = taskStatusDao;
        this.userDao = userDao;
        this.userProjectDao = userProjectDao;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Project> getUserProjects(int limit, int startFrom) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return projectDao.getUsersProjects(user.getUsername(), limit, startFrom);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and principal.canCreateProject")
    public void createProject(Project project){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        project.setCompany(user.getCompany());
        int projectId = projectDao.createProject(project);

        userProjectDao.addUserOnProject(project.getLead().getUsername(), projectId);
    }

    @Override
    @PreAuthorize("@databaseUserProjectDao.isUserAssignOnProject(principal.username, #projectId)")
    public Project getProject(int projectId){
        Project project = projectDao.getProject(projectId);

        List<Status> statuses = taskStatusDao.getAvailableProjectStatus(projectId);

        List<Task> tasks = new ArrayList<>();

        for(Status status : statuses){
            List<Task> tasksInStatus =  taskDao.getProjectTasks(projectId, status.getId(), TASK_START_FROM, TASK_LIMIT);
            tasks.addAll(tasksInStatus);
        }

        project.setTasks(tasks);
        project.setUsers(userDao.getUsersOnProject(projectId, TASK_START_FROM, TASK_LIMIT));

        return project;
    }
}
