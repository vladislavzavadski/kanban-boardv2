package by.bsuir.kanban.domain;


//$2a$10$u4gDAYyMDScY3IA1wEEqiu2Q0eB8mVJXJ5XNvN41beh/VhLOfab/S
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by vladislav on 08.04.17.
 */
@Getter
@Setter
@Entity
@Table(name = "project")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_lead_id")
    private User lead;
    private String description;
    private String logo;

    @Formula("(select count(*) from user_project usp where usp.project_id=id)")
    private int usersNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Task> tasks;

    @Formula("(select count(*) from task ta where ta.project_id = id)")
    private int totalTaskCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tag> tags;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Status> statuses;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserProject> userProjects;

    public Project(int id){
        this.id = id;
    }

    public Project(){}

}
