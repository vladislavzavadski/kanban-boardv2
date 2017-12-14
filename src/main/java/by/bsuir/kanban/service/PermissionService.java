package by.bsuir.kanban.service;

import by.bsuir.kanban.domain.Permission;
import by.bsuir.kanban.domain.PermissionType;
import by.bsuir.kanban.domain.Project;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.service.exception.NotAssignedOnProjectException;
import by.bsuir.kanban.service.exception.PermissionExistException;

public interface PermissionService {
    void addPermission(Permission permission) throws PermissionExistException, NotAssignedOnProjectException;

    void deletePermission(int permissionId);

    boolean doesHavePermission(PermissionType permissionType, User user, Project project);
}
