package by.bsuir.kanban.service.converter.impl;

import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.to.ComplexProjectDTO;
import by.bsuir.kanban.domain.to.TaskDTO;
import by.bsuir.kanban.service.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ComplexProjectConverter implements Converter<ComplexProjectDTO, Project> {

    @Autowired
    private Converter<TaskDTO, Task> taskConverter;

    @Override
    public ComplexProjectDTO convert(Project project) {

        ComplexProjectDTO complexProjectDTO = new ComplexProjectDTO();

        complexProjectDTO.setId(project.getId());
        complexProjectDTO.setName(project.getName());
        complexProjectDTO.setTasks(project.getTasks().stream().map(taskConverter :: convert).collect(Collectors.toList()));

        return complexProjectDTO;

    }
}
