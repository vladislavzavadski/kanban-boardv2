package by.bsuir.kanban.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by vladislav on 22.05.17.
 */
@Data
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;
    private String tagName;
    private String color;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tag_task", joinColumns = {@JoinColumn(name = "tag_id")}, inverseJoinColumns = {@JoinColumn(name = "task_id")})
    private List<Task> tasks;
}
