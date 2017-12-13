package by.bsuir.kanban.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registration_token")
@Getter
@Setter
public class RegistrationToken {

    @Id
    private String token;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;

    @Column(nullable = false)
    private Date expirationDate;
}
