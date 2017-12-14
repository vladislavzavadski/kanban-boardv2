package by.bsuir.kanban.controller;

import by.bsuir.kanban.domain.Permission;
import by.bsuir.kanban.service.PermissionService;
import by.bsuir.kanban.service.exception.NotAssignedOnProjectException;
import by.bsuir.kanban.service.exception.PermissionExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/permission")
    public void createPermission(@RequestBody Permission permission) throws PermissionExistException, NotAssignedOnProjectException {
        permissionService.addPermission(permission);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/permission/{permissionId}")
    public void deletePermission(@PathVariable("permissionId") int permissionId){
        permissionService.deletePermission(permissionId);
    }

    @ExceptionHandler(PermissionExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String permissionExceptionHandler(PermissionExistException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(NotAssignedOnProjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notAssignedOnProjectExceptionHandler(NotAssignedOnProjectException ex){
        return ex.getMessage();
    }
}
