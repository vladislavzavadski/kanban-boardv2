package by.bsuir.kanban.event;

import by.bsuir.kanban.domain.to.UserDTO;
import by.bsuir.kanban.domain.to.UserInvitationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserInvitationEvent extends ApplicationEvent{
    private UserInvitationDTO userInvitationDTO;
    private String token;
    private String applicationUrl;
    private UserDTO inviter;

    public UserInvitationEvent(UserInvitationDTO userInvitationDTO, String token, String applicationUrl, UserDTO inviter) {
        super(userInvitationDTO);
        this.userInvitationDTO = userInvitationDTO;
        this.token = token;
        this.applicationUrl = applicationUrl;
        this.inviter = inviter;
    }
}
