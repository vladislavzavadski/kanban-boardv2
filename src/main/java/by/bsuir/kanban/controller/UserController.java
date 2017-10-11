package by.bsuir.kanban.controller;

import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by vladislav on 12.05.17.
 */
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "user/company/{companyId}", method = RequestMethod.GET)
    public List<User> getUsersInCompany(@PathVariable("companyId") int companyId){
        return userService.getUsersInCompany(companyId);
    }

}
