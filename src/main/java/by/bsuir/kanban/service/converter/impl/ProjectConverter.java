package by.bsuir.kanban.service.converter.impl;

import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.domain.to.ProjectDTO;
import by.bsuir.kanban.domain.to.UserDTO;
import by.bsuir.kanban.service.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter implements Converter<ProjectDTO, Project> {

    @Autowired
    private Converter<UserDTO, User> userConverter;

    @Override
    public ProjectDTO convert(Project project) {

        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setId(project.getId());
        projectDTO.setLead(userConverter.convert(project.getLead()));
        projectDTO.setName(project.getName());
        projectDTO.setTotalTaskNumber(project.getTotalTaskCount());
        projectDTO.setUsersNumber(project.getUsersNumber());

        return projectDTO;

    }
}
