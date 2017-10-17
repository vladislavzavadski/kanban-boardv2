package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ulza1116 on 4/7/2017.
 */
@Repository
public interface UserDao extends JpaRepository<User, String>{

    User getUserByUsername(String username);

//    String getImagePath(String username);
//
//    List<User> getUsersOnProject(int projectId, int startFrom, int limit);
//
//    void createUser(User user);
//
//    void updateUser(User user);
//
//    List<User> selectUsersByCompany(int companyId);
}
