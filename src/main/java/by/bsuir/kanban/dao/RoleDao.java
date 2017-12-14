package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Permission;
import by.bsuir.kanban.domain.Role;
import by.bsuir.kanban.domain.RoleUser;
import by.bsuir.kanban.domain.User;

import java.util.List;

/**
 * Created by ulza1116 on 6/12/2017.
 */
public interface RoleDao {
    List<Role> getProjectGroups(int projectId);

    void createRole(Role role);

    List<User> selectRoleParticipants(int roleId);

    void assignToRole(RoleUser roleUser);

    void setPermission(Permission permission, int roleId);
}
