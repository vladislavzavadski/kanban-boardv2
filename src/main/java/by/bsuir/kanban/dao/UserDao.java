package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.User;
/**
 * Created by ulza1116 on 4/7/2017.
 */
public interface UserDao {

    User getUserByUsername(String username);

    String getImagePath(String username);
}
