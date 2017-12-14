package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.dao.PermissionDao;
import by.bsuir.kanban.dao.ProjectUserDao;
import by.bsuir.kanban.domain.*;
import by.bsuir.kanban.service.PermissionService;
import by.bsuir.kanban.service.exception.NotAssignedOnProjectException;
import by.bsuir.kanban.service.exception.PermissionExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DefaultPermissionService implements PermissionService {

    private final PermissionDao permissionDao;
    private final ProjectUserDao projectUserDao;

    @Autowired
    public DefaultPermissionService(PermissionDao permissionDao, ProjectUserDao projectUserDao) {
        this.permissionDao = permissionDao;
        this.projectUserDao = projectUserDao;
    }

    @Override
    @PreAuthorize("isAuthenticated() and @userDao.isProjectOwner(principal.username, #permission.userProject.project.id)")
    public void addPermission(Permission permission) throws PermissionExistException, NotAssignedOnProjectException {

        if(!projectUserDao.exists(permission.getUserProject().getId())){
            throw new NotAssignedOnProjectException("User not assigned on project");
        }

        checkIsPermissionExist(permission);
        permissionDao.save(permission);
    }

    @Override
    @PreAuthorize("isAuthenticated() and @userDao.isProjectOwnerByPermissionId(principal.username, #permissionId)")
    public void deletePermission(int permissionId){
        permissionDao.delete(permissionId);
    }

    @Override
    public boolean doesHavePermission(PermissionType permissionType, User user, Project project){
        UserProject userProject = new UserProject();

        userProject.setUser(user);
        userProject.setProject(project);

        return permissionDao.doesHavePermission(permissionType, userProject);
    }

    private void checkIsPermissionExist(Permission permission) throws PermissionExistException {

        if(permissionDao.isPermissionExist(permission.getUserProject(), permission.getPermissionType())){

            if(permission.getClass() == ChangeStatusPermission.class){
                ChangeStatusPermission changeStatusPermission = (ChangeStatusPermission)permission;

                if(permissionDao.isPermissionExist(changeStatusPermission.getFrom(), changeStatusPermission.getTo(),
                        changeStatusPermission.getUserProject())){
                    throw new PermissionExistException("Permission for user and project already exist");
                }
            }
        }
    }

}
