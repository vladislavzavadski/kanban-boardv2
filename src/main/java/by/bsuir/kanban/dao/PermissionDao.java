package by.bsuir.kanban.dao;

import by.bsuir.kanban.domain.Permission;

/**
 * Created by ulza1116 on 6/12/2017.
 */
public interface PermissionDao {
    void insertPermission(Permission permission);

    void insertChangeStatusPermission(Permission permission);
}
