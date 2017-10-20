package by.bsuir.kanban.controller;

import by.bsuir.kanban.domain.to.UserDTO;
import by.bsuir.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<UserDTO> getUsersInCompany(@PathVariable("companyId") int companyId, @RequestParam(value = "page") int page,
                                           @RequestParam(value = "limit", defaultValue = "10", required = false) int limit){
        return userService.getUsersInCompany(companyId, page, limit);
    }

}
