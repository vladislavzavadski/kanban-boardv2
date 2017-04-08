package by.bsuir.kanban.controller;

import by.bsuir.kanban.ImageConstant;
import by.bsuir.kanban.service.UserService;
import by.bsuir.kanban.service.exception.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;


/**
 * Created by ulza1116 on 4/7/2017.
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping("/")
    public String index(HttpServletRequest httpServletRequest){
        String path = servletContext.getRealPath("/images/user/");
        return "index";
    }

    @RequestMapping("/main")
    @ResponseBody
    public UserDetails success(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping("/user/picture")
    public FileSystemResource getUserPicture(@RequestParam(name = "username") String username) throws ImageNotFoundException {
        File file = userService.getUserPicture(username);
        return new FileSystemResource(file);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public FileSystemResource imageNotFoundException(HttpServletRequest httpServletRequest){
        String defaultUserImagePath = httpServletRequest.getServletContext().getRealPath("pictures/") + ImageConstant.DEFAULT_USER_IMAGE;
        return new FileSystemResource(defaultUserImagePath);
    }
}
