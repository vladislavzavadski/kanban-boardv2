package by.bsuir.kanban.controller;

import by.bsuir.kanban.controller.exception.InvalidObjectException;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.domain.to.UserDTO;
import by.bsuir.kanban.domain.to.UserInvitationDTO;
import by.bsuir.kanban.service.UserService;
import by.bsuir.kanban.service.converter.Converter;
import by.bsuir.kanban.service.exception.EmailAlreadyUsedException;
import by.bsuir.kanban.service.exception.InvalidTokenException;
import by.bsuir.kanban.service.exception.LoginAlreadyUsedException;
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

    private final UserService userService;
    private final Converter<UserDTO, User> userConverter;

    @Autowired
    public AuthenticationController(UserService userService, Converter<UserDTO, User> userConverter){
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid UserDTO user, Errors errors) throws EmailAlreadyUsedException, LoginAlreadyUsedException {

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

    @RequestMapping(value = "/user/invitation", method = RequestMethod.POST)
    public void inviteUser(@RequestBody UserInvitationDTO userInvintation){

    }

    @RequestMapping(value = "/currentuser", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO currentUser(){
        return userConverter.convert((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Boolean> isAuthenticated(){
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication() != null;

        return new ResponseEntity<>(isAuthenticated, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{token}", method = RequestMethod.GET)
    public void confirmUserAccount(@PathVariable("token") String token) throws InvalidTokenException {
        userService.activateUserAccount(token);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public String invalidUserTokenExceptionHandler(InvalidTokenException ex){
        return "Invalid token";
    }

    @ExceptionHandler(InvalidObjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidObjectExceptionHandler(InvalidObjectException ex){
        return ex.getErrors().getFieldError().getDefaultMessage();
    }

    @ExceptionHandler(LoginAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginAlreadyExistExceptionHandler(LoginAlreadyUsedException ex){
        return "Login already used";
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String emailAlreadyExistExceptionHandler(EmailAlreadyUsedException ex){
        return "Email already used";
    }
}
