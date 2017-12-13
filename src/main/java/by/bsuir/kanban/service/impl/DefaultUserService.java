package by.bsuir.kanban.service.impl;

import by.bsuir.kanban.dao.RegistrationTokenDao;
import by.bsuir.kanban.dao.UserDao;
import by.bsuir.kanban.domain.Company;
import by.bsuir.kanban.domain.RegistrationToken;
import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.domain.to.UserDTO;
import by.bsuir.kanban.event.UserRegistrationEvent;
import by.bsuir.kanban.service.NotificationSender;
import by.bsuir.kanban.service.UserService;
import by.bsuir.kanban.service.converter.Converter;
import by.bsuir.kanban.service.converter.impl.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by ulza1116 on 4/7/2017.
 */
@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserDetailsService, UserService {

    private final UserDao userDao;
    private final RegistrationTokenDao registrationTokenDao;
    private final PasswordEncoder passwordEncoder;
    private final NotificationSender notificationSender;
    private final Converter<UserDTO, User> userConverter;
    private final ApplicationEventPublisher applicationEventPublisher;

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
    public void createUser(UserDTO userDTO){
        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setUser(toUser(userDTO));
        String token = UUID.randomUUID().toString();
        registrationToken.setToken(token);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
        registrationToken.setExpirationDate(calendar.getTime());

        registrationTokenDao.save(registrationToken);

        applicationEventPublisher.publishEvent(new UserRegistrationEvent(userDTO, token));
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

    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public File getUserPicture(String username, String imageDirectoryFolder){
        return null;

    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated() and (principal.company.id eq #companyId)")
    public List<UserDTO> getUsersInCompany(int companyId, int page, int limit){
        Company company = new Company();
        company.setId(companyId);
        return userDao.getUsersByCompany(company, new PageRequest(page, limit)).stream().map(userConverter :: convert).collect(Collectors.toList());
    }

    private User toUser(UserDTO userDTO){
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setEmail(userDTO.getEmail());

        return user;
    }
}
