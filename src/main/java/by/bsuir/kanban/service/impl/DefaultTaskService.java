package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.dao.*;
import by.bsuir.kanban.domain.*;
import by.bsuir.kanban.domain.to.StatusDTO;
import by.bsuir.kanban.service.TaskService;
import by.bsuir.kanban.service.converter.Converter;
import by.bsuir.kanban.service.util.RelevantTaskComparator;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    public DefaultTaskService(TaskDao taskDao, TaskStatusDao taskStatusDao, TaskHistoryDao taskHistoryDao, ProjectDao projectDao, Converter<StatusDTO, Status> statusConverter) {
        this.taskDao = taskDao;
        this.taskStatusDao = taskStatusDao;
        this.taskHistoryDao = taskHistoryDao;
        this.projectDao = projectDao;
        this.statusConverter = statusConverter;
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
    @PreAuthorize("@userDao.isAssignedOnProject(principal.username, #projectId) ne 0")
    public List<StatusDTO> getProjectTaskStatuses(int projectId){
        return projectDao.findOne(projectId).getStatuses().stream().sorted(Comparator.comparingInt(Status::getOrder)).
                map(statusConverter::convert).collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("isAuthenticated() and @userDao.isProjectOwner(principal.username, #status.project.id) ne 0")
    public void createTaskStatus(Status status){
        Integer maxOrder = taskStatusDao.getMaxOrderStatusNumber(status.getProject());
        int currentOrder = maxOrder == null ? 0: maxOrder + 1;
        status.setOrder(currentOrder);
        taskStatusDao.save(status);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and @userDao.isProjectOwner(principal.username, #task.project.id) ne 0")
    public void createTask(Task task){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        task.setTaskCreator(user);
        task = taskDao.save(task);
        logTaskChanges(task, user);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and @userDao.isAssignedOnProjectByTaskId(principal.username, #task.id) ne 0")
    public void changeTaskStatus(Task task){

        Task persistentTask = taskDao.findOne(task.getId());
        persistentTask.setTaskStatus(task.getTaskStatus());
        taskDao.save(persistentTask);

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logTaskChanges(task, user);
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
