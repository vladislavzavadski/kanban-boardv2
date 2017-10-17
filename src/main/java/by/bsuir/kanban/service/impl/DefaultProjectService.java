package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.dao.*;
import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.Status;
import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.domain.to.ProjectDTO;
import by.bsuir.kanban.service.ProjectService;
import by.bsuir.kanban.service.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    private final Converter<ProjectDTO, Project> projectConverter;

    @Autowired
    public DefaultProjectService(ProjectDao projectDao, TaskDao taskDao, TaskStatusDao taskStatusDao,
                                 UserDao userDao, UserProjectDao userProjectDao, Converter<ProjectDTO, Project> projectConverter) {
        this.projectDao = projectDao;
        this.taskDao = taskDao;
        this.taskStatusDao = taskStatusDao;
        this.userDao = userDao;
        this.userProjectDao = userProjectDao;
        this.projectConverter = projectConverter;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<ProjectDTO> getUserProjects(int limit, int startFrom) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return projectDao.getUsersProjects(user, new PageRequest(startFrom, limit)).stream().
                map(projectConverter::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and principal.canCreateProject")
    public void createProject(Project project){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userDao.findOne(user.getUsername());
        project.setLead(user);
        user.getProjects().add(project);
        userDao.save(user);
    }

    @Override
    @PreAuthorize("@databaseUserProjectDao.isUserAssignOnProject(principal.username, #projectId)")
    public Project getProject(int projectId){
       // Project project = projectDao.getProject(projectId);

        List<Status> statuses = taskStatusDao.getAvailableProjectStatus(projectId);

        List<Task> tasks = new ArrayList<>();

        for(Status status : statuses){
            List<Task> tasksInStatus =  taskDao.getProjectTasks(projectId, status.getId(), TASK_START_FROM, TASK_LIMIT);
            tasks.addAll(tasksInStatus);
        }

//        project.setTasks(tasks);
//        project.setUsers(userDao.getUsersOnProject(projectId, TASK_START_FROM, TASK_LIMIT));

        return null;
    }
}
