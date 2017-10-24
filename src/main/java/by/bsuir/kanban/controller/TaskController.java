package by.bsuir.kanban.controller;

import by.bsuir.kanban.domain.Status;
import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.domain.to.StatusDTO;
import by.bsuir.kanban.domain.to.TaskDTO;
import by.bsuir.kanban.service.TaskService;
import by.bsuir.kanban.service.exception.StatusNotFoundException;
import by.bsuir.kanban.service.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ulza1116 on 6/7/2017.
 */

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/status/{projectId}", method = RequestMethod.GET)
    public List<StatusDTO> getTaskStatusesByProject(@PathVariable("projectId") int projectId){
        return taskService.getProjectTaskStatuses(projectId);
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public void createStatus(@RequestBody Status status){
        taskService.createTaskStatus(status);
    }

    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public void createTask(@RequestBody Task task){
        taskService.createTask(task);
    }

    @RequestMapping(value = "/task", method = RequestMethod.PUT)
    public void changeTaskStatus(@RequestBody Task task){
        taskService.changeTaskStatus(task);
    }

    @RequestMapping(value = "/status/{statusId}", method = RequestMethod.DELETE)
    public void deleteStatus(@PathVariable("statusId") int statusId) throws StatusNotFoundException {
        taskService.deleteTaskStatus(statusId);
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable("taskId") int taskId) throws TaskNotFoundException {
        taskService.deleteTask(taskId);
    }

    @RequestMapping(value = "/task/assign/to/{taskId}", method = RequestMethod.PUT)
    public void assignTask(@RequestBody User user, @PathVariable("taskId") int taskId) throws TaskNotFoundException {
        taskService.assignTask(user, taskId);
    }

    @RequestMapping(value = "/task/assign/{taskId}", method = RequestMethod.PUT)
    public void assignTask(@PathVariable("taskId") int taskId) throws TaskNotFoundException {
        taskService.assignTask(taskId);
    }

    @RequestMapping(value = "/task/{projectId}", method = RequestMethod.GET)
    public List<TaskDTO> getTasks(@PathVariable("projectId") int projectId, @RequestParam("statusId") int statusId,
                                  @RequestParam("page") int page,
                                  @RequestParam(value = "limit", required = false, defaultValue = "20") int limit){
        return taskService.getProjectTasks(projectId, statusId, page, limit);
    }

    @ExceptionHandler(StatusNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String statusNotFoundExceptionHandler(StatusNotFoundException ex){
        return "Task status was not found";
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String taskNotFoundExceptionHandler(TaskNotFoundException ex){
        return "Task not found";
    }

}
