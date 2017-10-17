package by.bsuir.kanban.service.converter.impl;

import by.bsuir.kanban.domain.Status;
import by.bsuir.kanban.domain.to.StatusDTO;
import by.bsuir.kanban.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatusConverter implements Converter<StatusDTO, Status> {

    @Override
    public StatusDTO convert(Status status) {

        StatusDTO statusDTO = new StatusDTO();

        statusDTO.setId(status.getId());
        statusDTO.setName(status.getName());
        statusDTO.setOrder(status.getOrder());

        return statusDTO;

    }

}
