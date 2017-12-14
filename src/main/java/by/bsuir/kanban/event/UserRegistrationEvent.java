package by.bsuir.kanban.event;

import by.bsuir.kanban.domain.to.UserDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {

    private UserDTO userDTO;
    private String token;
    private String applicationUrl;

    public UserRegistrationEvent(UserDTO userDTO, String token, String applicationUrl) {
        super(userDTO);
        this.userDTO = userDTO;
        this.token = token;
        this.applicationUrl = applicationUrl;
    }

}
