package by.bsuir.kanban.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by ulza1116 on 4/7/2017.
 */
@Data
@NoArgsConstructor
public class User implements UserDetails, Serializable {

    @Length(min = 5, max = 80, message = "Username length must be between 5 and 80 symbols")
    private String username;

    @JsonIgnore
    @Length(min = 8, max = 20, message = "Password length must be between 5 and 20 symbols")
    private String password;

    @Length(max = 80, message = "First name length must be max 80 symbols")
    private String firstName;

    @Length(max = 80, message = "Last name length must be max 80 symbols")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;
    private String picture;
    private boolean canCreateProject;
    private Company company;

    @JsonIgnore
    private boolean accountNonExpired = true;

    @JsonIgnore
    private boolean accountNonLocked = true;

    @JsonIgnore
    private boolean credentialsNonExpired = true;

    @JsonIgnore
    private boolean enabled = true;
    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.picture = user.picture;
        this.canCreateProject = user.canCreateProject;
        this.company = user.company;
        this.accountNonExpired = user.accountNonExpired;
        this.accountNonLocked = user.accountNonLocked;
        this.credentialsNonExpired = user.credentialsNonExpired;
        this.enabled = user.enabled;
        this.authorities = user.authorities;
    }

}
