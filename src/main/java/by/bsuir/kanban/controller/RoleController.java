package by.bsuir.kanban.controller;

import by.bsuir.kanban.domain.Permission;
import by.bsuir.kanban.domain.Role;
import by.bsuir.kanban.domain.RoleUser;
import by.bsuir.kanban.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ulza1116 on 6/12/2017.
 */
@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public void createRole(@RequestBody Role role){
        roleService.createRole(role);
    }

    @RequestMapping(value = "/roles/{projectId}", method = RequestMethod.GET)
    public List<Role> getProjectRoles(@PathVariable("projectId") int projectId){
        return roleService.getProjectRoles(projectId);
    }

    @RequestMapping(value = "/role/assign", method = RequestMethod.POST)
    public void assignToRole(@RequestBody RoleUser roleUser){
        roleService.assignToRole(roleUser);
    }

    @RequestMapping(value = "/role/permission/{roleId}", method = RequestMethod.POST)
    public void addPermissionToRole(@RequestBody Permission permission, @PathVariable("roleId") int roleId){
        roleService.setPermissionToRole(permission, roleId);
    }
}
