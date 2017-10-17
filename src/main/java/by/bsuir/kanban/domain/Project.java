package by.bsuir.kanban.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users ;

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

}
