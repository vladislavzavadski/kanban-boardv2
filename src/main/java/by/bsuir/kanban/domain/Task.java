package by.bsuir.kanban.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by vladislav on 09.04.17.
 */
@Data
public class Task implements Serializable {
    private int id;
    private String name;
    private String summary;
    private String description;
    private Priority priority;
    private Status taskStatus;
    private User taskCreator;
    private User taskExecutor;
    private Date createDate;
    private int order;
    private List<Tag> tags;
    private Project project;
}
