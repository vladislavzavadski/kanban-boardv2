package by.bsuir.kanban.dao;

/**
 * Created by vladislav on 09.04.17.
 */
public interface UserProjectDao {
    boolean isUserAssignOnProject(String username, int projectId);

    void addUserOnProject(String username, int projectId);
}
