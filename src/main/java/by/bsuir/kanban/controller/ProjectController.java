package by.bsuir.kanban.controller;

import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vladislav on 09.04.17.
 */
@RestController
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/project/{id}")
    public Project getProjectDetails(@PathVariable("id") int projectId){
        return projectService.getProject(projectId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/projects")
    public List<Project> getUsersProjects(@RequestParam(value = "limit", defaultValue = "30") int limit,
                                          @RequestParam(value = "start_from", defaultValue = "0") int startFrom){
        return projectService.getUserProjects(limit, startFrom);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/project")
    public void createProject(@RequestBody Project project){
        projectService.createProject(project);
    }

}
