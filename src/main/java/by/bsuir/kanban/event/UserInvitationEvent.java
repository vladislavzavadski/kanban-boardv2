package by.bsuir.kanban.event;

import by.bsuir.kanban.domain.to.UserInvitationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInvitationEvent {
    private UserInvitationDTO userInvitationDTO;
    private String token;
    private String applicationUrl;
}
