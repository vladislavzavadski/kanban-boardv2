package by.bsuir.kanban.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by vladislav on 09.04.17.
 */
@Data
@Entity
@Table(name = "task")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String summary;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Transient
    private Status taskStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User taskCreator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id")
    private User taskExecutor;
    private Date createDate;
    @Column(name = "task_order")
    private int order;

    @ManyToMany(mappedBy = "tasks", fetch = FetchType.LAZY)
    private List<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
