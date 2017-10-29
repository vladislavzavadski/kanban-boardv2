package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.dao.*;
import by.bsuir.kanban.domain.*;
import by.bsuir.kanban.domain.to.StatusDTO;
import by.bsuir.kanban.domain.to.TaskDTO;
import by.bsuir.kanban.service.TaskService;
import by.bsuir.kanban.service.converter.Converter;
import by.bsuir.kanban.service.exception.StatusNotFoundException;
import by.bsuir.kanban.service.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by vladislav on 22.05.17.
 */
@Service
public class DefaultTaskService implements TaskService{

    private final TaskDao taskDao;
    private final TaskStatusDao taskStatusDao;
    private final TaskHistoryDao taskHistoryDao;
    private final ProjectDao projectDao;
    private final Converter<StatusDTO, Status> statusConverter;
    private final Converter<TaskDTO, Task> taskConverter;

    @Autowired
    private UserDao userDao;

    @Autowired
    public DefaultTaskService(TaskDao taskDao, TaskStatusDao taskStatusDao, TaskHistoryDao taskHistoryDao,
                              ProjectDao projectDao, Converter<StatusDTO, Status> statusConverter, Converter<TaskDTO, Task> taskConverter) {
        this.taskDao = taskDao;
        this.taskStatusDao = taskStatusDao;
        this.taskHistoryDao = taskHistoryDao;
        this.projectDao = projectDao;
        this.statusConverter = statusConverter;
        this.taskConverter = taskConverter;
    }

    @Override
    public List<Task> searchTasks(Set<Tag> tags, String taskName, int projectId) {
//        List<Task> tasks = taskDao.searchTasks(tags, taskName, projectId);
//
//        for(Task task: tasks){
//            task.setTags(taskDao.getTaskTags(task.getId()));
//        }
//
//        Collections.sort(tasks, new RelevantTaskComparator(tags));
//
//        return tasks;
        return null;
    }

    @Override
    @PreAuthorize("@userDao.isAssignedOnProject(principal.username, #projectId)")
    public List<StatusDTO> getProjectTaskStatuses(int projectId){
        return projectDao.findOne(projectId).getStatuses().stream().sorted(Comparator.comparingInt(Status::getOrder)).
                map(statusConverter::convert).collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("isAuthenticated() and @userDao.isProjectOwner(principal.username, #status.project.id)")
    public void createTaskStatus(Status status){
        Integer maxOrder = taskStatusDao.getMaxOrderStatusNumber(status.getProject());
        int currentOrder = maxOrder == null ? 0: maxOrder + 1;
        status.setOrder(currentOrder);
        taskStatusDao.save(status);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() " +
            "and (@defaultPermissionService.doesHavePermission(T(by.bsuir.kanban.domain.PermissionType).CREATE_TASK, principal, #task.project)" +
            " or @userDao.isProjectOwner(principal.username, #task.project.id))")
    public void createTask(Task task){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        task.setTaskCreator(user);
        task.setCreateDate(new Date());
        task = taskDao.save(task);
        logTaskChanges(task, user);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and @userDao.isAssignedOnProjectByTaskId(principal.username, #task.id)")
    public void changeTaskStatus(Task task){

        Task persistentTask = taskDao.findOne(task.getId());
        persistentTask.setTaskStatus(task.getTaskStatus());
        taskDao.save(persistentTask);

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logTaskChanges(task, user);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and @userDao.isProjectOwnerByStatusId(principal.username, #statusId)")
    public void deleteTaskStatus(int statusId) throws StatusNotFoundException {
        Status status = taskStatusDao.findOne(statusId);

        if(status == null){
            throw new StatusNotFoundException("Status with id = " + statusId + " was not found");
        }

        taskStatusDao.updateStatusOrderAfterDelete(status.getProject().getId(), status.getOrder());
        taskStatusDao.delete(statusId);
    }

    @Override
    @PreAuthorize("isAuthenticated() and (@userDao.isProjectOwnerByTaskId(principal.username, #taskId) " +
            "or @taskDao.isTaskCreator(principal.username, #taskId))")
    public void deleteTask(int taskId) throws TaskNotFoundException {

        try {
            taskDao.delete(taskId);
        }
        catch (EmptyResultDataAccessException ex){
            throw new TaskNotFoundException(ex);
        }
    }

    @Override//TODO: security
    public void assignTask(int taskId) throws TaskNotFoundException {
        Task task = taskDao.findOne(taskId);

        if(task == null){
            throw new TaskNotFoundException("Task with id = "+taskId+" was not found");
        }

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        task.setTaskExecutor(user);
        taskDao.save(task);
    }

    @Override//TODO security
    public void assignTask(User user, int taskId) throws TaskNotFoundException {
        Task task = taskDao.findOne(taskId);

        if(task == null){
            throw new TaskNotFoundException("Task with id = "+taskId+" was not found");
        }

        task.setTaskExecutor(user);
        taskDao.save(task);
    }

    @Override
    public List<TaskDTO> getProjectTasks(int projectId, int statusId, int page, int limit){
        List<Task> tasks = taskDao.getTaskByTaskStatusAndProject(new Status(statusId), new Project(projectId),
                new PageRequest(page, limit));

        return tasks.stream().map(taskConverter::convert).collect(Collectors.toList());
    }

    private void logTaskChanges(Task task, User user){
        TaskHistory taskHistory = new TaskHistory();

        taskHistory.setEditor(user);
        taskHistory.setTime(new Date());
        taskHistory.setStatus(task.getTaskStatus());
        taskHistory.setTask(task);

        taskHistoryDao.save(taskHistory);
    }
}
