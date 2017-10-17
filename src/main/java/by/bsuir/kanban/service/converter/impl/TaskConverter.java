package by.bsuir.kanban.service.converter.impl;

import by.bsuir.kanban.domain.Status;
import by.bsuir.kanban.domain.Task;
import by.bsuir.kanban.domain.to.StatusDTO;
import by.bsuir.kanban.domain.to.TaskDTO;
import by.bsuir.kanban.service.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter implements Converter<TaskDTO, Task> {

    @Autowired
    private Converter<StatusDTO, Status> statusConverter;

    @Override
    public TaskDTO convert(Task task) {

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setSummary(task.getSummary());
        taskDTO.setPriority(task.getPriority());
        taskDTO.setTaskStatus(statusConverter.convert(task.getTaskStatus()));

        return taskDTO;

    }

}
