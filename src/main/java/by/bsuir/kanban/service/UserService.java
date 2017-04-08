package by.bsuir.kanban.service;

import by.bsuir.kanban.service.exception.ImageNotFoundException;

import java.io.File;

/**
 * Created by ulza1116 on 4/7/2017.
 */
public interface UserService {
    File getUserPicture(String username) throws ImageNotFoundException;
}
