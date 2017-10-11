package by.bsuir.kanban.controller;

import by.bsuir.kanban.controller.exception.InvalidObjectException;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by ulza1116 on 5/2/2017.
 */
@Controller
public class AuthenticationController {

    private UserService userService;

    @Autowired
    public AuthenticationController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid User user, Errors errors) {

        if(errors.hasErrors()){
            throw new InvalidObjectException(errors);
        }

        userService.createUser(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public void updateUser(@RequestBody User user, Errors errors){

        if(errors.hasErrors()){
            throw new InvalidObjectException(errors);
        }

        userService.updateUser(user);
    }

    @RequestMapping(value = "/currentuser", method = RequestMethod.GET)
    @ResponseBody
    public User currentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Boolean> isAuthenticated(){
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication() != null;

        return new ResponseEntity<Boolean>(isAuthenticated, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidObjectException.class)
    public ResponseEntity<Object> invalidObjectExceptionHandler(InvalidObjectException ex){
        String message = ex.getErrors().getFieldError().getDefaultMessage();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
