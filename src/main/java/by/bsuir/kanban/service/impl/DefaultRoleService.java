package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.dao.RoleDao;
import by.bsuir.kanban.domain.Permission;
import by.bsuir.kanban.domain.Role;
import by.bsuir.kanban.domain.RoleUser;
import by.bsuir.kanban.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ulza1116 on 6/12/2017.
 */
@Service
public class DefaultRoleService implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public DefaultRoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
  // @PreAuthorize("isAuthenticated() and @databaseProjectDao.isProjectOwner(principal.username, #role.project.id)")
    public void createRole(Role role){
        roleDao.createRole(role);
    }

    @Override
    //@PreAuthorize("isAuthenticated() and @databaseUserProjectDao.isUserAssignOnProject(principal.username, #projectId)")
    public List<Role> getProjectRoles(int projectId){
        return roleDao.getProjectGroups(projectId);
    }

    @Override
    @PreAuthorize("isAuthenticated() and @databaseProjectDao.isProjectOwnerByRole(principal.username, #roleUser.role.id)")
    public void assignToRole(RoleUser roleUser){
        roleDao.assignToRole(roleUser);
    }

    @Override
    @PreAuthorize("isAuthenticated() and @databaseProjectDao.isProjectOwnerByRole(principal.username, #roleId)")
    public void setPermissionToRole(Permission permission, int roleId){
        roleDao.setPermission(permission, roleId);
    }

}
