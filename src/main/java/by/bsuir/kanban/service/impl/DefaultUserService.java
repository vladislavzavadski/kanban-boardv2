package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.dao.UserDao;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.service.UserService;
import by.bsuir.kanban.service.exception.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by ulza1116 on 4/7/2017.
 */
@Service
public class DefaultUserService implements UserDetailsService, UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User with username " + username + " was not found");
        }

        return user;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public File getUserPicture(String username) throws ImageNotFoundException {
        String pathToImage = userDao.getImagePath(username);

        File file = new File(pathToImage);

        if(file.exists()){
            return file;
        }

        else {
           throw new ImageNotFoundException("Image for user was not found");
        }

    }
}
