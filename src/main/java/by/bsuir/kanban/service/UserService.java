package by.bsuir.kanban.service;

import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.domain.to.UserDTO;

import java.io.File;
import java.util.List;

/**
 * Created by ulza1116 on 4/7/2017.
 */
public interface UserService {
    void createUser(UserDTO user);

    void updateUser(User user);

    File getUserPicture(String username, String imageDirectoryFolder);
    List<UserDTO> getUsersInCompany(int companyId, int page, int limit);
}
