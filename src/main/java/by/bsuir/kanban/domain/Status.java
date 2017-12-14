package by.bsuir.kanban.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by vladislav on 09.04.17.
 */
@Getter
@Setter
@Entity
@Table(name = "task_status")
public class Status implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    private String name;

    @Column(name = "status_order")
    private int order;

    @OneToMany(mappedBy = "taskStatus", fetch = FetchType.LAZY)
    private List<Task> tasks;

    public Status(int id){
        this.id = id;
    }

    public Status(){}
}
