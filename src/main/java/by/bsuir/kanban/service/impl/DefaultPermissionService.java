package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.dao.PermissionDao;
import by.bsuir.kanban.domain.Permission;
import by.bsuir.kanban.domain.PermissionType;
import by.bsuir.kanban.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ulza1116 on 6/13/2017.
 */
@Service
public class DefaultPermissionService implements PermissionService {

    private final PermissionDao permissionDao;

    @Autowired
    public DefaultPermissionService(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and @databaseProjectDao.isProjectOwner(principal.username, #permission.project.id)")
    public void createPermission(Permission permission){

        permissionDao.insertPermission(permission);

        if(permission.getPermissionType() == PermissionType.CHANGE_STATUS){
            permissionDao.insertChangeStatusPermission(permission);
        }
    }

}
