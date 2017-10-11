package by.bsuir.kanban.domain;

import lombok.Data;

/**
 * Created by ulza1116 on 6/12/2017.
 */
@Data
public class Permission {
    private int id;
    private Project project;
    private PermissionType permissionType;
    private ChangeStatusPermission changeStatusPermission;
}
