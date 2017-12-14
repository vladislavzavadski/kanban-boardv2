package by.bsuir.kanban.service.converter.impl;

import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.domain.to.UserDTO;
import by.bsuir.kanban.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<UserDTO, User> {

    @Override
    public UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setCanCreateProject(user.isCanCreateProject());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPicture(user.getPicture());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }

}
