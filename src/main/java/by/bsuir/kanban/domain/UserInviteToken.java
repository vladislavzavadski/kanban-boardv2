package by.bsuir.kanban.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_invite_token")
@Data
public class UserInviteToken {

    @Id
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_username")
    private User inviter;

    private Date expirationTime;
    private boolean inviteAccepted;
    private String email;

}
