package by.bsuir.kanban.domain.to;

import lombok.Data;

@Data
public class UserInvitationDTO {
    private String email;
    private SimpleProjectDTO projectDTO;
}
