package by.bsuir.kanban.service.converter.impl;

import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.domain.to.SimpleProjectDTO;
import by.bsuir.kanban.domain.to.TaskDTO;
import by.bsuir.kanban.domain.to.UserDTO;
import by.bsuir.kanban.service.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleProjectConverter implements Converter<SimpleProjectDTO, Project> {

    @Autowired
    private Converter<UserDTO, User> userConverter;

    @Override
    public SimpleProjectDTO convert(Project project) {

        SimpleProjectDTO simpleProjectDTO = new SimpleProjectDTO();

        simpleProjectDTO.setId(project.getId());
        simpleProjectDTO.setLead(userConverter.convert(project.getLead()));
        simpleProjectDTO.setName(project.getName());
        simpleProjectDTO.setTotalTaskNumber(project.getTotalTaskCount());
        simpleProjectDTO.setUsersNumber(project.getUsersNumber());

        return simpleProjectDTO;

    }
}
