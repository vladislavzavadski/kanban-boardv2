package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.ImageConstant;
import by.bsuir.kanban.dao.UserDao;
import by.bsuir.kanban.domain.Notification;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.service.NotificationSender;
import by.bsuir.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by ulza1116 on 4/7/2017.
 */
@Service
public class DefaultUserService implements UserDetailsService, UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final NotificationSender notificationSender;

    @Autowired
    public DefaultUserService(UserDao userDao, PasswordEncoder passwordEncoder, NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User with username " + username + " was not found");
        }

        return user;
    }

    @Override
    @Transactional
    public void createUser(User user){
        String password = getRandomPassword();
        user.setPassword(passwordEncoder.encode(password));
        //userDao.createUser(user);

        Notification notification = new Notification();
        notification.setBody(user.getUsername() + " " + password);
        notification.setFrom("vladislav.zavadski@gmail.com");
        notification.setSubject("Registration");
        notification.setTo(user.getEmail());
        notificationSender.sendNotification(notification);

    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void updateUser(User user){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User tempUser = new User(user);

        tempUser.setUsername(currentUser.getUsername());

        if(tempUser.getPassword() == null){
            tempUser.setPassword(currentUser.getPassword());
        }
        else {
            tempUser.setPassword(passwordEncoder.encode(tempUser.getPassword()));
        }

     //   userDao.updateUser(tempUser);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public File getUserPicture(String username, String imageDirectoryFolder){
        return null;

    }

    @Override
    @PreAuthorize("isAuthenticated() and (principal.company.id eq #companyId)")
    public List<User> getUsersInCompany(int companyId){

        //return userDao.selectUsersByCompany(companyId);
        return null;
    }

    private String getRandomPassword(){
        SecureRandom secureRandom = new SecureRandom();

        return new BigInteger(64, secureRandom).toString(32);
    }
}
