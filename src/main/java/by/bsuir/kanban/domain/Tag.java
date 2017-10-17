package by.bsuir.kanban.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by vladislav on 22.05.17.
 */
@Getter
@Setter
@Entity
@Table(name = "tag")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id")
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
