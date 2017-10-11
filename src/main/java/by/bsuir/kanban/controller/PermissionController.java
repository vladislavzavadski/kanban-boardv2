package by.bsuir.kanban.controller;

import by.bsuir.kanban.domain.Permission;
import by.bsuir.kanban.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ulza1116 on 6/13/2017.
 */
@RestController
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public void createPermission(@RequestBody Permission permission){
        permissionService.createPermission(permission);
    }


}