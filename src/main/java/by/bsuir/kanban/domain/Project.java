package by.bsuir.kanban.domain;


import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by vladislav on 08.04.17.
 */
@Data
@Entity
@Table(name = "project")
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_lead_id")
    private User lead;
    private String description;
    private String logo;

    @Transient
    private int usersNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Task> tasks;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private List<User> users;
    @Transient
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
