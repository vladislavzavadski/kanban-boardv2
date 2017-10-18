package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.dao.*;
import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.domain.UserProject;
import by.bsuir.kanban.domain.to.ComplexProjectDTO;
import by.bsuir.kanban.domain.to.SimpleProjectDTO;
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
    private final Converter<SimpleProjectDTO, Project> simpleProjectConverter;
    private final Converter<ComplexProjectDTO, Project> complexProjectConverter;
    private final ProjectUserDao projectUserDao;

    @Autowired
    public DefaultProjectService(ProjectDao projectDao, TaskDao taskDao, TaskStatusDao taskStatusDao,
                                 UserDao userDao, UserProjectDao userProjectDao, Converter<SimpleProjectDTO, Project> simpleProjectConverter, Converter<ComplexProjectDTO, Project> complexProjectConverter, ProjectUserDao projectUserDao) {
        this.projectDao = projectDao;
        this.taskDao = taskDao;
        this.taskStatusDao = taskStatusDao;
        this.userDao = userDao;
        this.userProjectDao = userProjectDao;
        this.simpleProjectConverter = simpleProjectConverter;
        this.complexProjectConverter = complexProjectConverter;
        this.projectUserDao = projectUserDao;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<SimpleProjectDTO> getUserProjects(int limit, int startFrom) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return projectDao.getUsersProjects(user, new PageRequest(startFrom, limit)).stream().
                map(simpleProjectConverter::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and principal.canCreateProject")
    public void createProject(Project project){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//Дастаём пользователя и секьюрного контекста
        user = userDao.findOne(user.getUsername());//Достаю пользователя из БД, чтобы привязать его к сессии хибернейта
        project.setLead(user);
        project = projectDao.save(project);
        UserProject userProject = new UserProject();
        userProject.setProject(project);
        userProject.setUser(user);
        projectUserDao.save(userProject);
    }

    @Override
    @PreAuthorize("@userDao.isAssignedOnProject(principal.username, #projectId) ne 0")
    public ComplexProjectDTO getProject(int projectId){

        Project project = projectDao.findOne(projectId);

        return complexProjectConverter.convert(project);
    }
}
