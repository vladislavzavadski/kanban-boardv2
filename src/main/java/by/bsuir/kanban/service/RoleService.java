package by.bsuir.kanban.service;

import by.bsuir.kanban.domain.Permission;
import by.bsuir.kanban.domain.Role;
import by.bsuir.kanban.domain.RoleUser;

import java.util.List;

/**
 * Created by ulza1116 on 6/12/2017.
 */
public interface RoleService {
    void createRole(Role role);

    List<Role> getProjectRoles(int projectId);

    void assignToRole(RoleUser roleUser);

    void setPermissionToRole(Permission permission, int roleId);
}
