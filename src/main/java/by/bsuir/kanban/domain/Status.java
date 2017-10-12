package by.bsuir.kanban.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by vladislav on 09.04.17.
 */
@Data
@Entity
@Table(name = "task_status")
public class Status implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    private String name;
    @Column(name = "status_order")
    private int order;
}
