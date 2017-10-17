package by.bsuir.kanban.controller;

import by.bsuir.kanban.domain.Status;
import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.to.StatusDTO;
import by.bsuir.kanban.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/task/{projectId}", method = RequestMethod.POST)
    public void createTask(@RequestBody Task task, @PathVariable("projectId") int projectId){
        taskService.createTask(task, projectId);
    }

    @RequestMapping(value = "/task", method = RequestMethod.PUT)
    public void changeTaskStatus(@RequestBody Task task){
        taskService.changeTaskStatus(task);
    }
}
