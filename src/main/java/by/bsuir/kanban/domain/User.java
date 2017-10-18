package by.bsuir.kanban.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by ulza1116 on 4/7/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "username")
public class User implements UserDetails, Serializable {

    @Id
    @Column(length = 80)
    private String username;

    @JsonIgnore
    private String password;

    private String firstName;

    private String lastName;

    private String email;
    private String picture;
    private boolean canCreateProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "lead", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Project> ownProjects;

    @JsonIgnore
    @Transient
    private boolean accountNonExpired = true;

    @JsonIgnore
    @Transient
    private boolean accountNonLocked = true;

    @JsonIgnore
    @Transient
    private boolean credentialsNonExpired = true;

    @JsonIgnore
    @Transient
    private boolean enabled = true;
    @JsonIgnore
    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @OneToMany(mappedBy = "taskCreator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "taskExecutor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> executedTasks;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserProject> userProjects;

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
